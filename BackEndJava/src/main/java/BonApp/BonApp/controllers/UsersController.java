package BonApp.BonApp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import BonApp.BonApp.Enum.StatusOrdine;
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.Review;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.ForbiddenException;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewPreferiti;
import BonApp.BonApp.payload.NewReviewPayload;
import BonApp.BonApp.payload.NewUserPayload;
import BonApp.BonApp.payload.RegistrationPayload;
import BonApp.BonApp.payload.TopFavoritePayload;
import BonApp.BonApp.repositories.ReviewRepository;
import BonApp.BonApp.service.OrdineSingoloService;
import BonApp.BonApp.service.ReviewService;
import BonApp.BonApp.service.UsersService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UsersService userService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	OrdineSingoloService ordineSingoloService;

    //SAVE USER
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Valid RegistrationPayload registrationPayload) {
		User createdUser = userService.save(registrationPayload);
		return createdUser;
	}
	
    //GET USER
	@GetMapping("")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return userService.find(page, size, sortBy);

	}

	//FIND USER BY ID
	@GetMapping("/{userId}")
	public User findById(@PathVariable UUID userId) {
		return userService.findById(userId);
	}
    
	//GET CURRENT USER
	@GetMapping("/current")
	public ResponseEntity<User> getCurrentUser() {
		try {
			User currentUser = userService.getCurrentUser();
			return new ResponseEntity<>(currentUser, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// UPDATE USER
	@PutMapping("/{userId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public User updateUser(@PathVariable UUID userId, @RequestBody NewUserPayload body) {
		return userService.findByIdAndUpdate(userId, body);
	}

	//DELTE USER
	@DeleteMapping("/{userId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		userService.findByIdAndDelete(userId);
	}
    
	//ADD OWN FAVORITE PORDUCT
	@PostMapping("/{userId}/add-favorite/{productId}")
	public NewPreferiti addProductToFavorites(@PathVariable UUID userId, @PathVariable UUID productId)
			throws NotFoundException, ForbiddenException {
		return userService.addProductToFavorites(userId, productId);
	}
	
    //REMOVE OWN FAVORITE PRODUCT
	@DeleteMapping("/{userId}/remove-favorite/{productId}")
	public ResponseEntity<Void> removeProductFromFavorites(@PathVariable UUID userId, @PathVariable UUID productId) {
		userService.removeProductFromFavorites(productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
    
	//GET OWN ALL FAVORITE PRODUCTS
	@GetMapping("/{userId}/favorites")
	public ResponseEntity<Page<TopFavoritePayload>> getFavoriteProducts(@PathVariable UUID userId, Pageable pageable)
			throws NotFoundException {

		Page<TopFavoritePayload> favorites = userService.getFavoriteProducts(userId, pageable);
		return ResponseEntity.ok(favorites);
	}

	//GET TOP 10 FAVORITE PRODUCTS
	@GetMapping("/top-favorites")
	public ResponseEntity<Page<TopFavoritePayload>> getTopFavoriteProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<TopFavoritePayload> topFavoriteProducts = userService.getTopFavoriteProducts(page, size);
		if (topFavoriteProducts.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(topFavoriteProducts);
		}
	}

	//INITIALIZE CART
	@PostMapping("/{userId}/initialize-cart")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void initializeCart(@PathVariable UUID userId) {
		userService.initializeUserCart(userId);
	}

	
   //CREATE OWN REVIEW
	@PostMapping("/{userId}/new-review")
	public ResponseEntity<Review> createReview(@PathVariable UUID userId,
			@Valid @RequestBody NewReviewPayload newReviewPayload) {

		Review review = reviewService.createReview(userId, newReviewPayload);
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}

	//GET OWN REVIEWS
	@GetMapping("/{userId}/getOwnReviews")
	public ResponseEntity<Page<Review>> getAllReviewsByUserId(@PathVariable UUID userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Review> reviews = reviewService.getAllReviewsByUserId(userId, pageable);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	//DELETE OWN REVIEWS
	@DeleteMapping("/{userId}/delete-own-review/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable UUID userId, @PathVariable UUID reviewId) {
		try {
			reviewService.deleteReview(reviewId, userId);
			return ResponseEntity.ok("Review deleted successfully");
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
		} catch (ForbiddenException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authorized to delete this review");
		}

	}

	//ADD PRODUCT TO OWN CART
	@PostMapping("/{userId}/add-to-cart/{productId}")
	public ResponseEntity<Map<String, String>> addProductToCart(@PathVariable UUID userId, @PathVariable UUID productId, @RequestParam int quantity) {
	    userService.addProductToCart(userId, productId, quantity);
	    
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Product added to cart successfully");
	    
	    return ResponseEntity.ok(response);
	}


	//REMOVE PRODUCT FROM OWN CART
    @DeleteMapping("/{userId}/remove-from-cart/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable UUID userId, @PathVariable UUID productId, @RequestParam int quantity) {
        userService.removeProductFromCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }
    
    //GET PRODUCTS FROM CART
    @GetMapping("/{userId}/cart/products")
    public ResponseEntity<List<Prodotto>> getProductsInOrder(@PathVariable UUID userId) {
        
        List<Prodotto> prodottoList = userService.getProductsInOrder(userId);

        return ResponseEntity.ok(prodottoList);

}
    
    //FIND ORDINE SINGOLO BY USER ID AND STATUS
    @GetMapping("/{userId}/ordine-singolo-incart")
    public List<OrdineSingolo> getCartByUserId(@PathVariable UUID userId) {
        return userService.findCartByUserId(userId);
    }
    
    
    //GET THE QUANTITY OF A ITEM IN A CART
//    @GetMapping("/product-quantity")
//    public int getProductQuantityInCart(@RequestParam UUID userId, @RequestParam UUID productId) {
//        return ordineSingoloService.getProductQuantityInCart(userId, productId);
//    }
    
//    @GetMapping("/{userId}/ordine-singolo-incart")
//    public List<OrdineSingolo> getCartInStatus(@PathVariable UUID userId) {
//        return ordineSingoloService.getCartInStatus(userId);
//    }
    
   
    
//    @GetMapping("/search")
//	public ResponseEntity<Page<User>> searchUsers(@RequestParam(required = false) String cap,
//			@RequestParam(required = false) String localita, @RequestParam(required = false) String comune,
//			Pageable pageable) {
//
//		Page<User> users = userService.searchUsers(cap, localita, comune, pageable);
//		return ResponseEntity.ok(users);
//	}
}




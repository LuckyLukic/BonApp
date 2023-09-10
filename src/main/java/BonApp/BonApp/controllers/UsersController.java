package BonApp.BonApp.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewPreferiti;
import BonApp.BonApp.payload.NewUserPayload;
import BonApp.BonApp.payload.TopFavoritePayload;
import BonApp.BonApp.service.UsersService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UsersService userService;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@Valid @RequestBody NewUserPayload body) {
		User createdUser = userService.save(body);
		return createdUser;
	}

	@GetMapping("")
    //@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getUsers(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return userService.find(page, size, sortBy);
		
	}

	@GetMapping("/{userId}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public User findById(@PathVariable UUID userId) {
		return userService.findById(userId);
	}

	@PutMapping("/{userId}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public User updateUser(@PathVariable UUID userId, @RequestBody NewUserPayload body) {
		return userService.findByIdAndUpdate(userId, body);
	}

	@DeleteMapping("/{userId}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		userService.findByIdAndDelete(userId);
	}
	
	
	@PostMapping("/{userId}/favorites")
	public ResponseEntity<NewPreferiti> addProductToFavorites(
	        @PathVariable UUID userId, 
	        @RequestBody NewPreferiti newPreferiti) {
	    NewPreferiti responsePreferiti = userService.addProductToFavorites(userId, newPreferiti.getProductId());
	    return ResponseEntity.status(HttpStatus.CREATED).body(responsePreferiti);
	}

	@DeleteMapping("/{userId}/favorites")
	public ResponseEntity<NewPreferiti> removeProductFromFavorites(
	        @PathVariable UUID userId, 
	        @RequestBody NewPreferiti newPreferiti) {
	    NewPreferiti responsePreferiti = userService.removeProductFromFavorites(userId, newPreferiti.getProductId());
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responsePreferiti);
	}

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Prodotto>> getFavoriteProducts(@PathVariable UUID userId) {
        List<Prodotto> favoriteProducts = userService.getFavoriteProducts(userId);
        return ResponseEntity.ok(favoriteProducts);
    }
	

	@GetMapping("/top-favorites")
    public ResponseEntity<Page<TopFavoritePayload>> getTopFavoriteProducts(
    		@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
        Page<TopFavoritePayload> topFavoriteProducts = userService.getTopFavoriteProducts(page, size);
        if (topFavoriteProducts.isEmpty()) {
          return ResponseEntity.noContent().build();
          } else {
        return ResponseEntity.ok(topFavoriteProducts);
    }
	}
}


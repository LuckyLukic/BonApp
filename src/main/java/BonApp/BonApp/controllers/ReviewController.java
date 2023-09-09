package BonApp.BonApp.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.http.HttpStatus;
import BonApp.BonApp.entities.Review;
import BonApp.BonApp.payload.NewReviewPayload;
import BonApp.BonApp.service.ReviewService;

@RestController
	@RequestMapping("/reviews")
	public class ReviewController {

	@Autowired
    private ReviewService reviewService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Review saveReview(@RequestBody NewReviewPayload body) {
        return reviewService.save(body);
    }

    @GetMapping("")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Review> getReviews(@RequestParam(defaultValue = "0") int page, 
                                   @RequestParam(defaultValue = "10") int size, 
                                   @RequestParam(defaultValue = "reviewDate") String sortBy) {
        return reviewService.findAllReviews(page, size, sortBy);
    }

    @GetMapping("/{reviewId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Review findReviewById(@PathVariable UUID reviewId) {
        return reviewService.findReviewById(reviewId);
    }

    @PutMapping("/{reviewId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Review updateReview(@PathVariable UUID reviewId, @RequestBody NewReviewPayload body) {
        return reviewService.updateReview(reviewId, body);
    }

    @DeleteMapping("/{reviewId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }


	    // Endpoint to find reviews by date with pagination
	    @GetMapping("/by-date")
	    public ResponseEntity<Page<Review>> getReviewsByDate(
	            @RequestParam LocalDate reviewDate, 
	            @RequestParam int page, 
	            @RequestParam int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Review> reviewsPage = reviewService.findReviewsByDate(reviewDate, pageable);
	        return ResponseEntity.ok(reviewsPage);
	    }

	    // Endpoint to find reviews between two dates with pagination
	    @GetMapping("/between-dates")
	    public ResponseEntity<Page<Review>> getReviewsBetweenDates(
	            @RequestParam LocalDate startDate, 
	            @RequestParam LocalDate endDate, 
	            @RequestParam int page, 
	            @RequestParam int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Review> reviewsPage = reviewService.findReviewsBetweenDates(startDate, endDate, pageable);
	        return ResponseEntity.ok(reviewsPage);
	    }

	    // Endpoint to find reviews by username with pagination
	    @GetMapping("/by-username")
	    public ResponseEntity<Page<Review>> getReviewsByUser(
	            @RequestParam String username, 
	            @RequestParam int page, 
	            @RequestParam int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Review> reviewsPage = reviewService.findReviewsByUser(username, pageable);
	        return ResponseEntity.ok(reviewsPage);
	    }

	    // Endpoint to find reviews by rating with pagination
	    @GetMapping("/by-rating")
	    public ResponseEntity<Page<Review>> getReviewsByRating(
	            @RequestParam Integer rating, 
	            @RequestParam int page, 
	            @RequestParam int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Review> reviewsPage = reviewService.findReviewsByRating(rating, pageable);
	        return ResponseEntity.ok(reviewsPage);
	    }
	}


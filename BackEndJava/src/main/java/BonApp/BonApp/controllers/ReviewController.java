package BonApp.BonApp.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

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
import org.springframework.http.HttpStatus;
import BonApp.BonApp.entities.Review;
import BonApp.BonApp.payload.NewReviewPayload;
import BonApp.BonApp.service.ReviewService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("")
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public Review saveReview(@RequestBody @Valid NewReviewPayload body) {
		return reviewService.save(body);
	}

	@GetMapping("")
	public ResponseEntity<Page<Review>> getReviews(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "reviewDate") String sortBy) {
		return ResponseEntity.ok(reviewService.findAllReviews(page, size, sortBy));
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> findReviewById(@PathVariable UUID reviewId) {
		return ResponseEntity.ok(reviewService.findReviewById(reviewId));
	}

	@PutMapping("/{reviewId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Review> updateReview(@PathVariable UUID reviewId, @RequestBody @Valid NewReviewPayload body) {
		return ResponseEntity.ok(reviewService.updateReview(reviewId, body));
	}


	@GetMapping("/search")
	public ResponseEntity<List<Review>> findReviewsByCriteria(
			@RequestParam(value = "startDate", required = false) String startDateStr,
			@RequestParam(value = "endDate", required = false) String endDateStr,
			@RequestParam(value = "rating", required = false) Integer rating,
			@RequestParam(value = "username", required = false) String username) {

		LocalDate startDate = null;
		LocalDate endDate = null;

		if (startDateStr != null && !startDateStr.isEmpty()) {
			startDate = LocalDate.parse(startDateStr);
		}

		if (endDateStr != null && !endDateStr.isEmpty()) {
			endDate = LocalDate.parse(endDateStr);
		}

		List<Review> reviews = reviewService.findReviewsByCriteria(startDate, endDate, rating, username);
		return ResponseEntity.ok(reviews);
	}

}


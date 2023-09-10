package BonApp.BonApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Review;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.ForbiddenException;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewReviewPayload;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ReviewRepository;
import BonApp.BonApp.repositories.UserRepository;
import BonApp.BonApp.service.UsersService;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private UsersService userService;

	public Review save(NewReviewPayload body) throws NotFoundException {
        User authenticatedUser = userService.getCurrentUser();
        Review review = new Review();
        review.setComment(body.getComment());
        review.setRating(body.getRating());
        review.setUser(authenticatedUser);
        review.setReviewDate(LocalDate.now());
        review.setUsername(authenticatedUser.getUsername()); 
        return reviewRepository.save(review);
    }

	// Read single review
	public Review findReviewById(UUID id) throws NotFoundException {
		return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
	}

	// Read all reviews with pagination
	public Page<Review> findAllReviews(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return reviewRepository.findAll(pageable);
	}

	// Update
	public Review updateReview(UUID id, NewReviewPayload body) throws NotFoundException {
		User currentUser = userService.getCurrentUser();
		Review existingReview = this.findReviewById(id);

		if(!existingReview.getUser().getId().equals(currentUser.getId())) {
			throw new ForbiddenException("User not authorized to modify this review");
		}

		existingReview.setComment(body.getComment());
		existingReview.setRating(body.getRating());
		existingReview.setUsername(currentUser.getUsername());

		return reviewRepository.save(existingReview);
	}

	// Delete
	public void deleteReview(UUID id) throws NotFoundException {
		User currentUser = userService.getCurrentUser();
		Review existingReview = findReviewById(id);

		if(!existingReview.getUser().getId().equals(currentUser.getId())) {
			throw new ForbiddenException("User not authorized to delete this review");
		}

		reviewRepository.delete(existingReview);
	}

	public Page<Review> findReviewsByDate(LocalDate reviewDate, Pageable pageable) {
		return reviewRepository.findByReviewDate(reviewDate, pageable);
	}

	public Page<Review> findReviewsBetweenDates(LocalDate startDate, LocalDate endDate, Pageable pageable) {
		return reviewRepository.findByReviewDateBetween(startDate, endDate, pageable);
	}

	public Page<Review> findReviewsByUser(String username, Pageable pageable) {
		return reviewRepository.findByUsername(username, pageable);
	}

	public Page<Review> findReviewsByRating(Integer rating, Pageable pageable) {
		return reviewRepository.findByRating(rating, pageable);
	}
}

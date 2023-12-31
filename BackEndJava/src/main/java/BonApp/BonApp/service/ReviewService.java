package BonApp.BonApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import BonApp.BonApp.entities.Review;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.ForbiddenException;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewReviewPayload;

import BonApp.BonApp.repositories.ReviewRepository;
import BonApp.BonApp.repositories.UserRepository;


@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserRepository userRepository;

	public Review save(NewReviewPayload body) throws NotFoundException {
		User authenticatedUser = userService.getCurrentUser();
		Review review = new Review();
		review.setTitle(body.getTitle());
		review.setComment(body.getComment());
		review.setRating(body.getRating());
		review.setUser(authenticatedUser);
		review.setReviewDate(LocalDate.now());
		review.setUsername(authenticatedUser.getUsername());
		return reviewRepository.save(review);
	}
	
	public Review createReview(UUID userId, NewReviewPayload newReviewPayload) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

		Review review = new Review(newReviewPayload.getTitle(), newReviewPayload.getComment(),
				newReviewPayload.getRating(), user);

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

		if (!existingReview.getUser().getId().equals(currentUser.getId())) {
			throw new ForbiddenException("User not authorized to modify this review");
		}
		existingReview.setTitle(body.getTitle());
		existingReview.setComment(body.getComment());
		existingReview.setRating(body.getRating());
		existingReview.setUsername(currentUser.getUsername());

		return reviewRepository.save(existingReview);
	}

	// Delete Own review
	 	public void deleteReview(UUID reviewId, UUID userId) throws NotFoundException {
	 	    Review existingReview = findReviewById(reviewId);

	 	    if (!existingReview.getUser().getId().equals(userId)) {
	 	        throw new ForbiddenException("User not authorized to delete this review");
	 	    }

	 	    reviewRepository.delete(existingReview);
	 	}

	
	public List<Review> findReviewsByCriteria(LocalDate startDate, LocalDate endDate, Integer rating, String username) {
		return reviewRepository.findReviewsByCriteria(startDate, endDate, rating, username);
	}
	
	

    public Page<Review> getAllReviewsByUserId(UUID userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable);
    }
	

}

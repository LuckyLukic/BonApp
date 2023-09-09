package BonApp.BonApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

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
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewReviewPayload;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ReviewRepository;
import BonApp.BonApp.repositories.UserRepository;

    @Service
	public class ReviewService {

	    @Autowired
	    private ReviewRepository reviewRepository;

	    @Autowired
	    private UserRepository userRepository;

	    
           // save review
	        public Review save(NewReviewPayload body) {
	            Review review = new Review();
	            review.setComment(body.getComment());
	            review.setRating(body.getRating());
	            review.setUsername(body.getUsername());
	            review.setReviewDate(LocalDate.now());
	            return reviewRepository.save(review);
	        }

	        // Read single review
	        public Review findReviewById(UUID id) throws NotFoundException {
	            return reviewRepository.findById(id)
	                    .orElseThrow(() -> new NotFoundException("Review not found"));
	        }

	        // Read all reviews with pagination
	        public Page<Review> findAllReviews(int page, int size, String sort) {
	        	Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
	            return reviewRepository.findAll(pageable);
	        }

	        // Update
	        public Review updateReview(UUID id, NewReviewPayload body) throws NotFoundException {
	            Review review = this.findReviewById(id);

	            review.setComment(body.getComment());
	            review.setRating(body.getRating());
	            review.setUsername(body.getUsername());

	            return reviewRepository.save(review);
	        }

	        // Delete
	        public void deleteReview(UUID id) throws NotFoundException {
	            Review review = findReviewById(id);
	            reviewRepository.delete(review);
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
	






	         
	 


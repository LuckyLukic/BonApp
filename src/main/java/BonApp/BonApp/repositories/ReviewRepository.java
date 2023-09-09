package BonApp.BonApp.repositories;

import java.time.LocalDate;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import BonApp.BonApp.entities.Review;


public interface ReviewRepository extends JpaRepository<Review, UUID> {
	Page<Review> findByReviewDate(LocalDate reviewDate, Pageable pageable);
    Page<Review> findByReviewDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Review> findByUsername(String username, Pageable pageable);
    Page<Review> findByRating(Integer rating, Pageable pageable);

}


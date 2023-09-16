package BonApp.BonApp.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.Review;
import BonApp.BonApp.payload.NewReviewPayload;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
	
	Page<Review> findByUserId(UUID userId, Pageable pageable);
	 
	 Review save(NewReviewPayload newReviewPayload);

	@Query("SELECT r FROM Review r WHERE " 
	        + "(:startDate IS NULL OR r.reviewDate >= :startDate) AND " 
	        + "(:endDate IS NULL OR r.reviewDate <= :endDate) AND " 
			+ "(:rating IS NULL OR r.rating = :rating) AND "
			+ "(COALESCE(:username, '') = '' OR r.username = :username)")
	List<Review> findReviewsByCriteria(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
			@Param("rating") Integer rating, @Param("username") String username);

}


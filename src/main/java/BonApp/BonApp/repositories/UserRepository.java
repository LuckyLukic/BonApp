package BonApp.BonApp.repositories;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;



@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
	
	@Query("SELECT g FROM User u JOIN u.prodottiPreferiti g WHERE u.id = ?1")
    Page<Prodotto> findProdottiFavoritiByUserId(UUID userId, Pageable pageable);
	
	@Query(value = "SELECT p.id AS product_id, COUNT(upp.user_id) AS favorite_count " +
            "FROM user_preferred_products upp " +
            "JOIN prodotto p ON upp.product_id = p.id " +
            "GROUP BY p.id " +
            "ORDER BY favorite_count DESC",
    countQuery = "SELECT COUNT(DISTINCT p.id) " +
                 "FROM user_preferred_products upp " +
                 "JOIN prodotto p ON upp.product_id = p.id",
    nativeQuery = true)
    Page<Object[]> findTopFavoriteProducts(Pageable pageable);

	
	
}

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
}

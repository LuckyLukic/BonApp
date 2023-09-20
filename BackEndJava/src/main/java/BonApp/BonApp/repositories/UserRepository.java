package BonApp.BonApp.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BonApp.BonApp.Enum.StatusOrdine;
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);

	List<User> findByProdottiPreferitiContaining(Prodotto prodotto);

	@Query("SELECT g FROM User u JOIN u.prodottiPreferiti g WHERE u.id = ?1")
    Page<Prodotto> findProdottiFavoritiByUserId(UUID userId, Pageable pageable);
	

	@Query(value = "SELECT p.id AS prodotto_id, COUNT(upp.user_id) AS favorite_count " +
            "FROM user_prodotti_preferiti upp " +
            "JOIN prodotto p ON upp.prodotto_id = p.id " +
            "GROUP BY p.id " +
            "ORDER BY favorite_count DESC",
    countQuery = "SELECT COUNT(DISTINCT p.id) " +
                 "FROM user_prodotti_preferiti upp " +
                 "JOIN prodotto p ON upp.prodotto_id = p.id",
    nativeQuery = true)
    Page<Object[]> findTopFavoriteProducts(Pageable pageable);

	@Query("SELECT u FROM User u JOIN u.indirizzo i WHERE " + "(COALESCE(:cap, '') = '' OR i.cap = :cap) AND "
			+ "(COALESCE(:localita, '') = '' OR i.localita = :localita) AND "
			+ "(COALESCE(:comune, '') = '' OR i.comune = :comune)")
	Page<User> findByCapLocalitaAndComune(@Param("cap") String cap, @Param("localita") String localita,
			@Param("comune") String comune, Pageable pageable);



}
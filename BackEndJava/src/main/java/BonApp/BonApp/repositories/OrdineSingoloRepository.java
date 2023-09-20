package BonApp.BonApp.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import BonApp.BonApp.Enum.StatusOrdine;
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;

public interface OrdineSingoloRepository extends JpaRepository<OrdineSingolo, UUID> {

	List<OrdineSingolo> findByProdottiContaining(Prodotto prodotto);

	@Query("SELECT o FROM OrdineSingolo o JOIN o.user u JOIN u.indirizzo i WHERE "
			+ "(COALESCE(:cap, '') = '' OR LOWER(i.cap) LIKE LOWER(CONCAT('%', :cap, '%'))) AND "
			+ "(COALESCE(:localita, '') = '' OR LOWER(i.localita) LIKE LOWER(CONCAT('%', :localita, '%'))) AND "
			+ "(COALESCE(:comune, '') = '' OR LOWER(i.comune) LIKE LOWER(CONCAT('%', :comune, '%'))) AND "
			+ "(:minPrice IS NULL OR o.totalPrice >= :minPrice) AND "
			+ "(:maxPrice IS NULL OR o.totalPrice <= :maxPrice) AND "
			+ "(CAST(:startDate AS date) IS NULL OR o.dataOrdine >= :startDate) AND "
			+ "(CAST(:endDate AS date) IS NULL OR o.dataOrdine <= :endDate)")
	Page<OrdineSingolo> findByMultipleCriteria(@Param("cap") String cap, @Param("localita") String localita,
			@Param("comune") String comune, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

	@Query("SELECT o FROM OrdineSingolo o WHERE " + "(COALESCE(:userId, NULL) IS NULL OR o.user.id = :userId)")
	Page<OrdineSingolo> findByUserId(@Param("userId") UUID userId, Pageable pageable);

	List<OrdineSingolo> findByUserAndStatus(User user, StatusOrdine status);

	OrdineSingolo findByStatus(StatusOrdine status);

}

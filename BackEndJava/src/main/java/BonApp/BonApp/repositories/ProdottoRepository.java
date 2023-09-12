package BonApp.BonApp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import BonApp.BonApp.Enum.Categoria;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;


public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {

	Page<Prodotto> findByNomeContainingIgnoreCase(String partialName, Pageable page);

	
	
	@Query("SELECT p FROM Prodotto p JOIN p.ingredienti i WHERE " +
		       "( :categoria IS NULL OR p.categoria = :categoria ) AND " +
		       "( :minPrice IS NULL OR p.prezzo >= :minPrice ) AND " +
		       "( :maxPrice IS NULL OR p.prezzo <= :maxPrice ) AND " +
		       "( :ingredienteName IS NULL OR LOWER(i.nome) LIKE LOWER(CONCAT('%', :ingredienteName, '%')) )")
	    List<Prodotto> findByCategoriaAndPriceRangeAndIngredienteName(
	            @Param("categoria") Categoria categoria, 
	            @Param("minPrice") Double minPrice, 
	            @Param("maxPrice") Double maxPrice,
	            @Param("ingredienteName") String ingredienteName);

	
//	@Query(value = "SELECT p.id AS prodotto_id, COUNT(upp.user_id) AS favorite_count " +
//	        "FROM user_prodotti_preferiti upp " +
//	        "JOIN prodotto p ON upp.prodotto_id = p.id " +
//	        "GROUP BY p.id " +
//	        "ORDER BY favorite_count DESC",
//	    countQuery = "SELECT COUNT(DISTINCT p.id) " +
//	                 "FROM user_prodotti_preferiti upp " +
//	                 "JOIN prodotto p ON upp.prodotto_id = p.id",
//	    nativeQuery = true)
//	Page<Object[]> findTopFavoriteProducts(Pageable pageable);
}

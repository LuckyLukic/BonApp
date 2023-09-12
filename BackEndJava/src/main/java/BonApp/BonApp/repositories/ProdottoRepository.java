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

}

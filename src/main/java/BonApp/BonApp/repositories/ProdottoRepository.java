package BonApp.BonApp.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BonApp.BonApp.entities.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID>{
	
	Page<Prodotto> findByNameContainingIgnoreCase(String partialName, Pageable page);

}

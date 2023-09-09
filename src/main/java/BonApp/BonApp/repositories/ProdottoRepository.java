package BonApp.BonApp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import BonApp.BonApp.entities.Prodotto;


public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {
	
	Page<Prodotto> findByNomeContainingIgnoreCase(String partialName, Pageable page);
	List<Prodotto> findByIdIn(List<UUID> ids);


}

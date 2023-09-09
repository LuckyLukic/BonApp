package BonApp.BonApp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;

public interface OrdineSingoloRepository extends JpaRepository<OrdineSingolo, UUID>{

	List<OrdineSingolo> findByProdottiContaining(Prodotto prodotto);
}

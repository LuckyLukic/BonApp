package BonApp.BonApp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BonApp.BonApp.entities.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID>{

}

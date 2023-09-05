package BonApp.BonApp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BonApp.BonApp.entities.Ingredienti;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingredienti, UUID> {

}

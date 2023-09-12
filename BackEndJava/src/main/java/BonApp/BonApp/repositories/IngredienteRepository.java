package BonApp.BonApp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import BonApp.BonApp.entities.Ingrediente;


public interface IngredienteRepository extends JpaRepository<Ingrediente, UUID>{

}

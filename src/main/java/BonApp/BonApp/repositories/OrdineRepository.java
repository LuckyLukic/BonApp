package BonApp.BonApp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import BonApp.BonApp.entities.Ordine;

public interface OrdineRepository extends JpaRepository<Ordine, UUID>{

}

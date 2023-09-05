package BonApp.BonApp.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Ingredienti {
    @Id
    private UUID id;
    private String name;

   
}


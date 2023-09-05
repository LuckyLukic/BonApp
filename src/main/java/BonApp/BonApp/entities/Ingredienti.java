package BonApp.BonApp.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ingredienti {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    
	public Ingredienti(String name) {
	
		this.name = name;
	}
    
    

   
}


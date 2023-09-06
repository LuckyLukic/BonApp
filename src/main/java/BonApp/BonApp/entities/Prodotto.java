package BonApp.BonApp.entities;

import java.util.List;
import java.util.UUID;

import BonApp.BonApp.Enum.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Prodotto {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Categoria categoria ;

    @ManyToMany
    @JoinTable(
        name = "product_ingredient",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredienti> ingredienti;

	public Prodotto(String name, String description, double price, Categoria categoria, List<Ingredienti> ingredienti, String imgUrl) {
		
		this.name = name;
		this.description = description;
		this.price = price;
		this.categoria = categoria;
		this.ingredienti = ingredienti;
		this.imgUrl = imgUrl;
	}
    
    

}


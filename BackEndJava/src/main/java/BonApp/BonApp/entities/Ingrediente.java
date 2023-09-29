package BonApp.BonApp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingrediente {

	@Id
	@GeneratedValue
	private UUID id;
	private String nome;

	@JsonIgnore
	@ManyToMany(mappedBy = "ingredienti")
	private List<Prodotto> prodotti = new ArrayList<>();

	public Ingrediente(String nome) {
		this.nome = nome;
	}
}

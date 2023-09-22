package BonApp.BonApp.payload;

import java.util.List;

import BonApp.BonApp.Enum.Categoria;
import BonApp.BonApp.entities.Ingrediente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class NewProdottoPayload {

	@NotNull(message = "Il nome del prodotto è obbligatorio")
	private String nome;

	@NotNull(message = "la descrizione del prodotto e' obbligatorio")
	@Size(min = 20, message = "la descrizione deve avere minimo 20 caratteri")
	private String descrizione;

	@NotNull(message = "Il prezzo del prodotto è obbligatorio")
	private Double prezzo;

	@NotNull(message = "La categoria del prodotto è obbligatoria")
	private Categoria categoria;

	@NotNull(message = "inserisci almento 1 ingrediente")
	private List<Ingrediente> ingredienti;
	

	private String imgUrl;
}

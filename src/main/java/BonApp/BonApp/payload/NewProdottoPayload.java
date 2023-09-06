package BonApp.BonApp.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import BonApp.BonApp.Enum.Categoria;
import BonApp.BonApp.entities.Ingredienti;

@AllArgsConstructor
@Getter
@Setter
public class NewProdottoPayload {
	
    @NotNull(message = "Il nome del prodotto è obbligatorio")
    private String nome;

    @NotBlank(message = "la descrizione del prodotto e' obbligatorio")
    @Size(min = 20, message = "la descrizione deve avere minimo 20 caratteri")
    private String descrizione;

    @NotNull(message = "Il prezzo del prodotto è obbligatorio")
    private Double prezzo;

    @NotNull(message = "La categoria del prodotto è obbligatoria")
    private Categoria categoria;
    
    @NotNull(message = "Il prodotto deve avere almeno un ingrediente")
    private List<Ingredienti> ingredienti;
    
    private String imgUrl;

  
}


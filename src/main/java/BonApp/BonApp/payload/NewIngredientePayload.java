package BonApp.BonApp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NewIngredientePayload {
	
	    @NotNull(message = "Il nome dell'ingrediente è obbligatorio")
	    private String nome;

}

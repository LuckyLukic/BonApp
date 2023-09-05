package BonApp.BonApp.payload;

import jakarta.validation.constraints.NotNull;

public class NewIngredientePayload {
	
	    @NotNull(message = "Il nome dell'ingrediente è obbligatorio")
	    private String nome;

}

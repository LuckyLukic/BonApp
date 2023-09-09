package BonApp.BonApp.payload;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NewOrdineSingoloPayload {
	
	
	

	    @NotNull(message = "L'ID dell'utente è obbligatorio")
	    private UUID userId;

	    @NotNull(message = "Aggiungi almeno 1 prodotto")
	    private List<UUID> prodotti;
	    
	    
	    private double totalPrice;
	    
	    private LocalDate dataOrdine;

	    
	    private LocalTime oraOrdine;
	      
	}

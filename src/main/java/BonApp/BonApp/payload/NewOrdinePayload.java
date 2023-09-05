package BonApp.BonApp.payload;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



import BonApp.BonApp.entities.Prodotto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewOrdinePayload {
	
	@NotNull(message = "devi aggiungere almeno un prodotto")
    private List<Prodotto> prodotto; 
	@NotNull(message = "devi aggiungere una data")
	private LocalDate data;
	@NotNull(message = "devi aggiungere un orario")
	private LocalTime ora;

}


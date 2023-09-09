package BonApp.BonApp.DAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdineSingoloDTO {
	
	private UUID id;
    private UUID userId; // to reference the user id without creating a circular relationship
    private double totalPrice;
    private LocalDate dataOrdine;
    private LocalTime oraOrdine;
    private List<ProdottoDTO> prodotti;

}

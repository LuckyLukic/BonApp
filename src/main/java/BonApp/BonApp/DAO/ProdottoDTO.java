package BonApp.BonApp.DAO;

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
public class ProdottoDTO {
	private UUID id;
    private String nome;
    private double prezzo;
    private List<IngredienteDTO> ingredienti;
}

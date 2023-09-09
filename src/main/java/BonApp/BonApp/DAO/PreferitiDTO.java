package BonApp.BonApp.DAO;

import BonApp.BonApp.entities.Prodotto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreferitiDTO {
	
	  private Prodotto prodotto;
	    private Long favoriteCount;


}

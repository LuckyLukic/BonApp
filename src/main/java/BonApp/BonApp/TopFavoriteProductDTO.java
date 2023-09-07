package BonApp.BonApp;

import BonApp.BonApp.entities.Prodotto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopFavoriteProductDTO {
    private Prodotto prodotto;
    private Long favoriteCount;

   
}
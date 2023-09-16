package BonApp.BonApp.payload;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NewPreferiti {
	
	@NotNull(message =  "devi inserire l'id prodotto")
    private UUID productId;
    private String message;

}

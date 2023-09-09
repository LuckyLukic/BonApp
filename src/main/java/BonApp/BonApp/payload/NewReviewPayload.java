package BonApp.BonApp.payload;

import java.time.LocalDate;

import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewReviewPayload {
	
	@NotNull(message = "Il commento e' obbligatorio")
	private String comment;
	@NotNull(message = "Il rating e' obbligatorio")
    private Integer rating;

    private String username;

    private LocalDate reviewDate;

}

package BonApp.BonApp.payload;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewReviewPayload {

	@NotNull(message = "il titolo e' obbligatorio")
	private String title;
	@NotNull(message = "Il commento e' obbligatorio")
	private String comment;
	@NotNull(message = "Il rating e' obbligatorio")
	private Integer rating;
	

}

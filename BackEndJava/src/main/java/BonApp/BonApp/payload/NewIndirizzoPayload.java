package BonApp.BonApp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewIndirizzoPayload {

	@NotNull(message = "La via è obbligatorio")
	private String via;
	@NotNull(message = "Il civico è obbligatorio")
	private String civico;
	@NotNull(message = "La località è obbligatorio")
	private String localita;
	@NotNull(message = "Il cap è obbligatorio")
	private String cap;
	@NotNull(message = "Il comune è obbligatorio")
	private String comune;
	@NotNull(message = "La provincia è obbligatorio")
	private String provincia;
}

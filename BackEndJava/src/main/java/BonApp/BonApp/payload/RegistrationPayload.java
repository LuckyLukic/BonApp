package BonApp.BonApp.payload;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationPayload {
	
	@Valid
    private NewUserPayload newUserPayload;

    @Valid
    private NewIndirizzoPayload newIndirizzoPayload;


}

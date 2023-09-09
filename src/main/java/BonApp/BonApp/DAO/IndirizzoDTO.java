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
public class IndirizzoDTO {
	
	private UUID indirizzo_id;
    private String via;
    private int civico;
    private String località;
    private String cap;
    private String comune;
    private List<UserDTO> users;

}

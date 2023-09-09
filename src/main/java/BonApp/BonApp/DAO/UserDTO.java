package BonApp.BonApp.DAO;

import java.time.LocalDate;
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
public class UserDTO {

	private UUID id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String role;
    private LocalDate dataRegistrazione;
    private IndirizzoDTO indirizzo;  
    private List<OrdineSingoloDTO> singleOrders;
   
}

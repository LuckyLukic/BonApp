package BonApp.BonApp.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Indirizzo {

	@Id
	@GeneratedValue
	private UUID indirizzo_id;
	private String via;
	private int civico;
	private String località;
	private String cap;
	private String comune;
	@OneToOne(mappedBy = "indirizzo")
	@JoinColumn(name = "user_id")
	private User user;
	

	public Indirizzo(String cap, int civico, String localita, String via, User user, String comune) {

		this.cap = cap;
		this.civico = civico;
		this.località = localita;
		this.via = via;
		this.user = user;
		this.comune = comune;
	}
}

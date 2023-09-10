package BonApp.BonApp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
public class Indirizzo {

	@Id
	@GeneratedValue
	private UUID indirizzo_id;
	private String via;
	private String civico;
	private String località;
	private String cap;
	private String comune;
	private String provincia;

	@OneToMany(mappedBy = "indirizzo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users = new ArrayList<>();

	public Indirizzo(String cap, String civico, String localita, String via, String comune, String provincia) {

		this.cap = cap;
		this.civico = civico;
		this.località = localita;
		this.via = via;
		this.comune = comune;
		this.provincia = provincia;
	}

	public void addUser(User user) {
		this.users.add(user);
		user.setIndirizzo(this);
	}

}


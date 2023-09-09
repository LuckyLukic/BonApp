package BonApp.BonApp.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import BonApp.BonApp.Enum.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private UUID id;
	@SuppressWarnings("unused")
	private String username;
	private String name;
	private String surname;
	
	private String email;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "indirizzo_id", nullable = true)
    private Indirizzo indirizzo;
	
	@JsonIgnore
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrdineSingolo> singleOrders = new ArrayList<>();
	
	private LocalDate dataRegistrazione;
	
	@ManyToMany
	@JoinTable(
	    name = "user_prodotti_preferiti", 
	    joinColumns = @JoinColumn(name = "user_id"), 
	    inverseJoinColumns = @JoinColumn(name = "prodotto_id")
	)	private List<Prodotto> prodottiPreferiti = new ArrayList<>();
	
	

	@SuppressWarnings("static-access")
	public User(String username, String name, String surname, String email, 
			Indirizzo indirizzo, 
			String password) {

		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.indirizzo = indirizzo;
		this.role = role.USER;
		
		this.dataRegistrazione = LocalDate.now();
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public void addSingleOrder(OrdineSingolo ordineSingolo) {
        this.singleOrders.add(ordineSingolo);
    }
	
	public void addPreferredProduct(Prodotto prodotto) {
	    this.prodottiPreferiti.add(prodotto);
	    prodotto.getUsersFavouriteProducts().add(this);
	}

	public void removePreferredProduct(Prodotto prodotto) {
	    this.prodottiPreferiti.remove(prodotto);
	    prodotto.getUsersFavouriteProducts().remove(this);
	}
}

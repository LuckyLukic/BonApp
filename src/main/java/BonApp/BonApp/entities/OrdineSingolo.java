package BonApp.BonApp.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrdineSingolo {
	
@Id
@GeneratedValue
private UUID id;

@ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private User user;

private double totalPrice;

private LocalDate dataOrdine;
private LocalTime oraOrdine;


@ManyToMany(cascade = { CascadeType.MERGE })
@JoinTable(
		name ="ordinesingolo_prodotto",
		joinColumns = @JoinColumn(name = "ordinesingolo_id"),
		inverseJoinColumns = @JoinColumn (name = "prodotto_id"))
private List<Prodotto> prodotti = new ArrayList<>();

public OrdineSingolo( User user, List<Prodotto> prodotti) {
  
  this.user = user;	
  this.prodotti = prodotti;
    this.totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();
   this.dataOrdine = LocalDate.now();
	this.oraOrdine = LocalTime.now();
}

public void addProduct(Prodotto prodotto) {
    this.prodotti.add(prodotto);
    this.totalPrice += prodotto.getPrezzo();
}


}




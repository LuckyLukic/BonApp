package BonApp.BonApp.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import BonApp.BonApp.Enum.StatusOrdine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

	private StatusOrdine status;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "ordinesingolo_prodotto", joinColumns = @JoinColumn(name = "ordinesingolo_id"), inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
	private List<Prodotto> prodotti = new ArrayList<>();

	public OrdineSingolo(User user, List<Prodotto> prodotti) {

		this.user = user;
		this.prodotti = prodotti;
		this.totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();
		this.dataOrdine = LocalDate.now();
		this.oraOrdine = LocalTime.now();
		this.status = status.IN_CART;
	}

	public void addProduct(Prodotto prodotto, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.prodotti.add(prodotto);
			this.totalPrice += prodotto.getPrezzo();
		}
	}

	public void removeProduct(Prodotto prodotto, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.prodotti.remove(prodotto);
			this.totalPrice -= prodotto.getPrezzo();
		}
	}

	public void checkout() {
		if (this.status == status.IN_CART) {
			this.status = status.COMPLETATO;
			this.dataOrdine = LocalDate.now();
			this.oraOrdine = LocalTime.now();

			User user = this.getUser();
			OrdineSingolo newOrdineSingolo = new OrdineSingolo();
			user.addSingleOrder(newOrdineSingolo);
		} else {
			throw new IllegalStateException("Cannot checkout a cart that is not in IN_CART status");
		}
	}

}




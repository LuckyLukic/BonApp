package BonApp.BonApp.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import BonApp.BonApp.Enum.StatusOrdine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
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
	
    private double shippingCost;


	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "ordinesingolo_prodotto", joinColumns = @JoinColumn(name = "ordinesingolo_id"), inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
	private List<Prodotto> prodotti = new ArrayList<>();

	public OrdineSingolo(User user, List<Prodotto> prodotti) {

		this.user = user;
		this.prodotti = prodotti;
		this.totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();
		this.dataOrdine = LocalDate.now();
		this.oraOrdine = LocalTime.now();
		this.status = StatusOrdine.IN_CART;
		this.shippingCost =  2.5;
	}
	
	public double calculateShippingCost() {
        if (this.totalPrice > 15) {
            this.shippingCost = 0.0;
        } else {
            this.shippingCost = 2.5;
        }
        return this.shippingCost;
    }
	
	@ElementCollection
	private Map<UUID, Integer> productQuantities = new HashMap<>();

	public void addProduct(Prodotto prodotto, int quantity) {
		this.prodotti.add(prodotto);
		this.productQuantities.put(prodotto.getId(), this.productQuantities.getOrDefault(prodotto.getId(), 0) + quantity);
		this.totalPrice += prodotto.getPrezzo() * quantity;
	}

	public void removeProduct(Prodotto prodotto, int quantity) {
	    Integer currentQuantity = this.productQuantities.getOrDefault(prodotto.getId(), 0);
	    if(currentQuantity < quantity) {
	        throw new IllegalArgumentException("Cannot remove more products than present in the cart");
	    }
	    
	    this.prodotti.remove(prodotto);
	    this.productQuantities.put(prodotto.getId(), currentQuantity - quantity);
	    this.totalPrice -= prodotto.getPrezzo() * quantity;
	}
	

	public void checkout() {
		if (this.status == StatusOrdine.IN_CART) {
			this.status = StatusOrdine.COMPLETATO;
			this.dataOrdine = LocalDate.now();
			this.oraOrdine = LocalTime.now();
			this.shippingCost = calculateShippingCost();

			User user = this.getUser();
			OrdineSingolo newOrdineSingolo = new OrdineSingolo();
			user.addSingleOrder(newOrdineSingolo);
		} else {
			throw new IllegalStateException("Cannot checkout a cart that is not in IN_CART status");
		}
	}

}




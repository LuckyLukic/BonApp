package BonApp.BonApp.entities;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import BonApp.BonApp.Enum.StatusOrdine;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	private double totalPrice;

	private LocalDate dataOrdine;
	private LocalTime oraOrdine;

	@Enumerated(EnumType.STRING)
	private StatusOrdine status;

	private double shippingCost;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "ordinesingolo_prodotto", joinColumns = @JoinColumn(name = "ordinesingolo_id"), inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
	private List<Prodotto> prodotti = new ArrayList<>();

	public OrdineSingolo(User user, List<Prodotto> prodotti) {

		this.user = user;
		this.prodotti = prodotti;
		this.totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum() + this.shippingCost;
		this.dataOrdine = LocalDate.now();
		this.oraOrdine = LocalTime.now();
		this.status = StatusOrdine.IN_CART;
		this.shippingCost = calculateShippingCost();
	}

	public double calculateShippingCost() {
		if (this.totalPrice > 15) {
			this.shippingCost = 0.0;
		} else {
			this.shippingCost = 2.50;
		}
		return this.shippingCost;
	}

	@ElementCollection
	private Map<UUID, Integer> productQuantities = new HashMap<>();

	public void addProduct(Prodotto prodotto, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.prodotti.add(prodotto);
		}
		this.productQuantities.put(prodotto.getId(),
				this.productQuantities.getOrDefault(prodotto.getId(), 0) + quantity);
		this.totalPrice += prodotto.getPrezzo() * quantity;
		calculateShippingCost();
	}

	public void removeProduct(Prodotto prodotto, int quantity) {
		Integer currentQuantity = this.productQuantities.getOrDefault(prodotto.getId(), 0);
		if (currentQuantity < quantity) {
			throw new IllegalArgumentException("Cannot remove more products than present in the cart");
		}

		currentQuantity -= quantity;
		this.productQuantities.put(prodotto.getId(), currentQuantity);
		this.totalPrice -= prodotto.getPrezzo() * quantity;

		for (int i = 0; i < quantity; i++) {
			this.prodotti.remove(prodotto);
		}

		if (currentQuantity == 0) {
			this.productQuantities.remove(prodotto.getId());
			this.prodotti.remove(prodotto);
		}

		if (this.prodotti.isEmpty()) {
			this.totalPrice = 0.0;
		}
		calculateShippingCost();

	}

	public int getProductQuantity(UUID productId) {
		return this.productQuantities.getOrDefault(productId, 0);
	}

	public void checkout() {
		if (this.status == StatusOrdine.IN_CART) {
			this.status = StatusOrdine.COMPLETATO;
			this.dataOrdine = LocalDate.now();
			this.oraOrdine = LocalTime.now();

		} else {
			throw new IllegalStateException("Cannot checkout a cart that is not in IN_CART status");
		}
	}

	public void invioEmail(OrdineSingolo ordine) throws IOException {

		Email from = new Email("lucabjjiannice@gmail.com");
		String subject = "Sending with SendGrid is Fun";
		Email to = new Email("luca.iannice@icloud.com");
		String message = "Hi " + ordine.getUser().getName() + ",\n\n" +
                "Thank you for your recent order. We'd love to hear your thoughts!\n" +
                "Please leave a review at the following link:\n " +
                "http://localhost:4200/rate-us/" + ordine.getUser().getId() + "\n\n" +
                "Best Regards,\nBonApp";
	
	    Content content = new Content("text/plain", message);

		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("API_KEY_SENDGRID"));
		System.out.println("SENDGRID_API_KEY: " + System.getenv("API_KEY_SENDGRID"));

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

}

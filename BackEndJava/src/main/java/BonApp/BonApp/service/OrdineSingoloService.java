package BonApp.BonApp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BonApp.BonApp.Enum.StatusOrdine;
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewOrdineSingoloPayload;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.UserRepository;

@Service
public class OrdineSingoloService {

	@Autowired
	OrdineSingoloRepository ordineSingoloRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UsersService userService;

	@Autowired
	ProdottoRepository prodottoRepository;

	public OrdineSingolo save(NewOrdineSingoloPayload body) throws NotFoundException {
		User user = userRepository.findById(body.getUserId())
				.orElseThrow(() -> new NotFoundException("User not found with ID: " + body.getUserId()));

		List<Prodotto> prodotti = prodottoRepository.findAllById(body.getProdotti());

		if (prodotti.size() != body.getProdotti().size()) {
			throw new NotFoundException("Some products were not found");
		}

		double totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();

		OrdineSingolo newSingleOrder = new OrdineSingolo(user, prodotti);
		newSingleOrder.setTotalPrice(totalPrice);
		newSingleOrder.setDataOrdine(LocalDate.now());
		newSingleOrder.setOraOrdine(LocalTime.now());
		return ordineSingoloRepository.save(newSingleOrder);
	}

	public Page<OrdineSingolo> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return ordineSingoloRepository.findAll(pageable);
	}

	public OrdineSingolo findById(UUID id) throws NotFoundException {
		return ordineSingoloRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Single Order not found with ID: " + id));
	}

	public OrdineSingolo findByIdAndUpdate(UUID id, NewOrdineSingoloPayload body) throws NotFoundException {
		OrdineSingolo existingSingleOrder = findById(id);

		User user = userRepository.findById(body.getUserId())
				.orElseThrow(() -> new NotFoundException("User not found with ID: " + body.getUserId()));

		List<Prodotto> prodotti = prodottoRepository.findAllById(body.getProdotti());

		if (prodotti.size() != body.getProdotti().size()) {
			throw new NotFoundException("Some products were not found");
		}

		double totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();

		existingSingleOrder.setUser(user);
		existingSingleOrder.setProdotti(prodotti);
		existingSingleOrder.setTotalPrice(totalPrice);
		existingSingleOrder.setDataOrdine(
				body.getDataOrdine() != null ? body.getDataOrdine() : existingSingleOrder.getDataOrdine());
		existingSingleOrder
				.setOraOrdine(body.getOraOrdine() != null ? body.getOraOrdine() : existingSingleOrder.getOraOrdine());

		OrdineSingolo savedSingleOrder = ordineSingoloRepository.save(existingSingleOrder);

		return savedSingleOrder;

	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		OrdineSingolo existingSingleOrder = findById(id);
		ordineSingoloRepository.delete(existingSingleOrder);
	}

	public OrdineSingolo processCheckout(UUID userId, UUID ordineSingoloId) throws IllegalStateException {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
		OrdineSingolo ordineSingolo = ordineSingoloRepository.findById(ordineSingoloId)
				.orElseThrow(() -> new NotFoundException("Order not found"));

		ordineSingolo.checkout();

		userService.initializeUserCart(userId);
		return ordineSingoloRepository.save(ordineSingolo);

	}

	public Page<OrdineSingolo> findByMultipleCriteria(String cap, String localita, String comune, Double minPrice,
			Double maxPrice, LocalDate startDate, LocalDate endDate, Pageable pageable) {
		return ordineSingoloRepository.findByMultipleCriteria(cap, localita, comune, minPrice, maxPrice, startDate,
				endDate, pageable);
	}

	public Page<OrdineSingolo> findOrdersByUserId(UUID userId, Pageable pageable) {
		return ordineSingoloRepository.findByUserId(userId, pageable);
	}

	public double calculateShippingCost(double totalPrice) {
		if (totalPrice > 15) {
			return 0.0;
		} else {
			return 2.5;
		}
	}

	// INVIO EMAIL CON SENDGRID
	public void checkStatusSendEmail() throws IOException {
		List<OrdineSingolo> ordiniCompletati = ordineSingoloRepository.findByStatus(StatusOrdine.COMPLETATO);

		for (OrdineSingolo ordine : ordiniCompletati) {
			LocalDate currentDate = LocalDate.now();

			if (currentDate.isEqual(ordine.getDataOrdine())) {
				ordine.invioEmail(ordine);

			}
		}

	}

	// RICERCA TUTTI GLI ORDINI DI UN UTENTE CON STATUS COMPLETATO
	public List<OrdineSingolo> findAllCompletedOrdersForUser(UUID userId) {
		User user = userService.findById(userId);
		if (user == null) {

			return Collections.emptyList();
		}

		return user.getSingleOrders().stream().filter(order -> order.getStatus() == StatusOrdine.COMPLETATO)
				.collect(Collectors.toList());
	}

}

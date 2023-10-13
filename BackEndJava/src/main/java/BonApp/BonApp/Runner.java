package BonApp.BonApp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import BonApp.BonApp.Enum.Role;
import BonApp.BonApp.entities.Indirizzo;
import BonApp.BonApp.entities.Ingrediente;
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.Review;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.repositories.IndirizzoRepository;
import BonApp.BonApp.repositories.IngredienteRepository;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.ReviewRepository;
import BonApp.BonApp.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	IndirizzoRepository indirizzoRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	IngredienteRepository ingredienteRepository;

	@Autowired
	ProdottoRepository prodottoRepository;

	@Autowired
	OrdineSingoloRepository ordineSingoloRepository;

	@Autowired
	ReviewRepository reviewRepository;

	private Faker faker = new Faker(new Locale("it"));

	@Override
	public void run(String... args) throws Exception {
		try {
			seedIngredients();
			seedProducts();
			seedUsersAndOrders();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	private void seedIngredients() {
		if (ingredienteRepository.count() == 0) {
			List<Ingrediente> ingredients = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				Ingrediente ingrediente = new Ingrediente();
				ingrediente.setNome(faker.food().ingredient());
				ingredients.add(ingrediente);
			}
			ingredienteRepository.saveAll(ingredients);
		}
	}

	@Transactional
	private void seedProducts() {

		if (prodottoRepository.count() == 0) {
			List<Ingrediente> ingredients = ingredienteRepository.findAll();
			List<Prodotto> prodottoList = new ArrayList<>();

			for (int i = 0; i < 30; i++) {
				int numberOfIngredients = faker.number().numberBetween(2, 5);
				Collections.shuffle(ingredients);
				List<Ingrediente> selectedIngredients = ingredients.subList(0, numberOfIngredients);

				Prodotto prodotto = new Prodotto();
				prodotto.setNome(faker.commerce().productName());
				prodotto.setPrezzo(Double.parseDouble(faker.commerce().price().replace(",", ".")));
				prodotto.setIngredienti(new ArrayList<>(selectedIngredients));
				prodottoList.add(prodotto);
			}
			prodottoRepository.saveAll(prodottoList);
		}
	}

	@Transactional
	private void seedUsersAndOrders() {
		if (userRepository.count() == 0) {
			List<Indirizzo> indirizzi = new ArrayList<>();
			List<User> users = new ArrayList<>();
			List<Prodotto> products = prodottoRepository.findAll();
			List<Review> reviews = new ArrayList<>();

			Random random = new Random();

			for (int i = 0; i < 15; i++) {
				Indirizzo indirizzo = new Indirizzo();
				indirizzo.setVia(faker.address().streetAddress());
				indirizzo.setCivico(String.valueOf(faker.number().numberBetween(1, 100)));
				indirizzo.setLocalita(faker.address().city());
				indirizzo.setCap(faker.address().zipCode());
				indirizzo.setComune(faker.address().cityName());
				indirizzo.setProvincia(faker.address().state());
				indirizzi.add(indirizzo);
			}

			indirizzoRepository.saveAll(indirizzi);
			indirizzoRepository.flush(); //aggiorno il DB con il PC

			for (int i = 0; i < 15; i++) {
				User user = new User();
				user.setUsername(faker.name().username());
				user.setName(faker.name().firstName());
				user.setSurname(faker.name().lastName());
				user.setEmail(faker.internet().emailAddress());
				user.setPassword(faker.internet().password());
				user.setDataRegistrazione(LocalDate.now());
				user.setRole(Role.USER);

				Indirizzo indirizzo = indirizzi.get(i);
				user.setIndirizzo(indirizzo);
				indirizzo.addUser(user);

				Collections.shuffle(products);
				int numPreferredProducts = random.nextInt(5);
				for (int k = 0; k < numPreferredProducts; k++) {
					user.getProdottiPreferiti().add(products.get(k));
				}

				int reviewCount = faker.number().numberBetween(1, 3);
				for (int j = 0; j < reviewCount; j++) {
					Review review = new Review();
					review.setUser(user);
					review.setTitle(faker.lorem().word());
					review.setComment(faker.lorem().paragraph());
					review.setReviewDate(LocalDate.now());
					review.setRating(faker.number().numberBetween(1, 6));
					review.setUsername(user.getUsername());
					reviews.add(review);

					user.addReview(review);
				}

				// Create 0 to 3 single orders for each user
				for (int j = 0; j < random.nextInt(4); j++) {
					OrdineSingolo ordineSingolo = new OrdineSingolo();
					ordineSingolo.setUser(user);

					// Add 1 to 3 products to each single order

					for (int k = 0; k < random.nextInt(3) + 1; k++) {
						Collections.shuffle(products);
						ordineSingolo.addProduct(products.get(0), 1);

					}

					// Calculate the total price for the order
					ordineSingolo.setTotalPrice(ordineSingolo.getTotalPrice());

					// Set the order date and time
					ordineSingolo.setDataOrdine(LocalDate.now());
					ordineSingolo.setOraOrdine(LocalTime.now());

					user.addSingleOrder(ordineSingolo);
				}

				users.add(user);
			}

			userRepository.saveAll(users);
		}
	}

}

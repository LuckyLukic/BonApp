package BonApp.BonApp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.BadRequestException;
import BonApp.BonApp.exceptions.ForbiddenException;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewPreferiti;
import BonApp.BonApp.payload.NewUserPayload;
import BonApp.BonApp.payload.TopFavoritePayload;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.UserRepository;

@Service
public class UsersService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProdottoRepository prodottoRepository;

	// SALVA NUOVO UTENTE + ECCEZIONE SE VIENE USATA LA STESSA EMAIL
	public User save(NewUserPayload body) {
		userRepository.findByEmail(body.getEmail()).ifPresent(user -> {
			throw new BadRequestException("L'email " + body.getEmail() + " è gia stata utilizzata");
		});
		User newUser = new User(body.getUsername(), body.getName(), body.getSurname(), body.getEmail(),
				body.getIndirizzo(), body.getPassword());
		return userRepository.save(newUser);
	}

	// TORNA LA LISTA DEGLI UTENTI
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	// TORNA LA LISTA DEGLI UTENTI CON L'IMPAGINAZIONE
	public Page<User> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

		return userRepository.findAll(pageable);
	}

	// CERCA UTENTE TRAMITE ID
	public User findById(UUID id) throws NotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// CERCA E MODIFICA UTENTE TRAMITE ID
	public User findByIdAndUpdate(UUID id, NewUserPayload body) throws NotFoundException {
		User found = this.findById(id);
		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setEmail(body.getEmail());
		found.setUsername(body.getUsername());
		found.setIndirizzo(body.getIndirizzo());
		return userRepository.save(found);
	}

	// CERCA E CANCELLA UTENTE TRAMITE ID
	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		userRepository.delete(found);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
	}

	// METODO PER IL TEST LOGIN
	public static boolean authenticateUser(User inputUser, User userFromDatabase,
			BCryptPasswordEncoder passwordEncoder) {
		if (inputUser == null || userFromDatabase == null) {
			return false;
		}

		if (!inputUser.getEmail().equals(userFromDatabase.getEmail())) {
			return false;
		}

		String rawPassword = inputUser.getPassword();
		String encryptedPasswordFromDatabase = userFromDatabase.getPassword();

		return passwordEncoder.matches(rawPassword, encryptedPasswordFromDatabase);
	}

	// PRENDI L'ID DELL'UTENTE LOGGATO
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		return userRepository.findByEmail(currentUserName)
				.orElseThrow(() -> new NotFoundException("Utente con email " + currentUserName + " non trovato"));
	}

	public NewPreferiti addProductToFavorites(UUID userId, UUID productId)
			throws NotFoundException, ForbiddenException {

		User authenticatedUser = getCurrentUser();

		if (!authenticatedUser.getId().equals(userId)) {
			throw new ForbiddenException(
					"Access denied: You do not have permission to add products to this user's favorites");
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
		Prodotto product = prodottoRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found"));

		user.getProdottiPreferiti().add(product);
		userRepository.save(user);

		return new NewPreferiti(userId, productId, "Product successfully added to favorites");
	}

	public NewPreferiti removeProductFromFavorites(UUID userId, UUID productId)
			throws NotFoundException, ForbiddenException {

		User authenticatedUser = getCurrentUser();

		if (!authenticatedUser.getId().equals(userId)) {
			throw new ForbiddenException(
					"Access denied: You do not have permission to remove products from this user's favorites");
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
		Prodotto product = prodottoRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found"));

		user.getProdottiPreferiti().remove(product);
		userRepository.save(user);

		return new NewPreferiti(userId, productId, "Product successfully removed from favorites");
	}

	public List<Prodotto> getFavoriteProducts(UUID userId) throws NotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

		return user.getProdottiPreferiti();
	}

	public Page<TopFavoritePayload> getTopFavoriteProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Object[]> result = userRepository.findTopFavoriteProducts(pageable);

		Page<TopFavoritePayload> topProducts = result.map(row -> {
			UUID productId = (UUID) row[0];
			Long favoriteCount = (Long) row[1];

			Optional<Prodotto> productOptional = prodottoRepository.findById(productId);
			if (productOptional.isPresent()) {
				Prodotto product = productOptional.get();
				Map<Prodotto, Long> productFavoriteCount = Map.of(product, favoriteCount);

				return new TopFavoritePayload(product, favoriteCount);

			} else {
				return null;
			}
		});

		return topProducts;
	}

	public void initializeUserCart(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
		user.initializeCart();
		userRepository.save(user);
	}
} 
	    


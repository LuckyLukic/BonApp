package BonApp.BonApp.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BonApp.BonApp.Enum.Categoria;
import BonApp.BonApp.entities.Ingrediente;
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewProdottoPayload;
import BonApp.BonApp.payload.TopFavoritePayload;
import BonApp.BonApp.repositories.IngredienteRepository;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdottoService {

	@Autowired
	private ProdottoRepository prodottoRepository;

	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private OrdineSingoloRepository ordineSingoloRepository;

	@Autowired
	private UserRepository userRepository;

	public Prodotto save(NewProdottoPayload body) {
		if (body.getPrezzo() == null) {
			throw new IllegalArgumentException("Prezzo cannot be null");
		}

		if (body.getNome() == null || body.getDescrizione() == null || body.getCategoria() == null) {
			throw new IllegalArgumentException("Some other fields are null");
		}

		Prodotto newProdotto = new Prodotto(body.getNome(), body.getDescrizione(), body.getPrezzo(),
				body.getCategoria(), body.getIngredienti(), body.getImgUrl());
		return prodottoRepository.save(newProdotto);
	}

	public Page<Prodotto> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return prodottoRepository.findAll(pageable);
	}

	public Prodotto findById(UUID id) throws NotFoundException {
		return prodottoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public Prodotto findByIdAndUpdate(UUID id, Prodotto updatedProdotto) throws NotFoundException {
		Prodotto existingProdotto = findById(id);

		existingProdotto.setNome(updatedProdotto.getNome());
		existingProdotto.setDescrizione(updatedProdotto.getDescrizione());
		existingProdotto.setPrezzo(updatedProdotto.getPrezzo());
		existingProdotto.setCategoria(updatedProdotto.getCategoria());
		existingProdotto.setIngredienti(updatedProdotto.getIngredienti());
		existingProdotto.setImgUrl(updatedProdotto.getImgUrl());

		return prodottoRepository.save(existingProdotto);
	}

	@Transactional

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Prodotto existingProdotto = findById(id);

		List<Ingrediente> ingredienti = existingProdotto.getIngredienti();

		for (Ingrediente ingrediente : ingredienti) {
			ingrediente.getProdotti().remove(existingProdotto);
		}

		for (Ingrediente ingrediente : ingredienti) {
			ingredienteRepository.save(ingrediente);
		}

		List<OrdineSingolo> ordini = ordineSingoloRepository.findByProdottiContaining(existingProdotto);
		for (OrdineSingolo ordine : ordini) {
			ordine.getProdotti().remove(existingProdotto);
			ordineSingoloRepository.save(ordine);
		}

		List<User> users = userRepository.findByProdottiPreferitiContaining(existingProdotto);
		for (User user : users) {
			user.getProdottiPreferiti().remove(existingProdotto);
			userRepository.save(user);
		}

		prodottoRepository.delete(existingProdotto);
	}

	
	public Page<Prodotto> findByPartialName(String partialName, int page, int size, String sortBy) {
		Pageable prodottiPageable = PageRequest.of(page, size, Sort.by(sortBy));
		return prodottoRepository.findByNomeContainingIgnoreCase(partialName, prodottiPageable);
	}

	
	public List<Prodotto> findByCriteria(Categoria categoria, Double minPrice, Double maxPrice, String ingredienteName) {
        return prodottoRepository.findByCategoriaAndPriceRangeAndIngredienteName(categoria, minPrice, maxPrice, ingredienteName);
    }
	

}	

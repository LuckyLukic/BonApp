package BonApp.BonApp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BonApp.BonApp.entities.Ingrediente;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewIngredientePayload;
import BonApp.BonApp.repositories.IngredienteRepository;
import BonApp.BonApp.repositories.ProdottoRepository;

@Service
public class IngredienteService {

	@Autowired
	IngredienteRepository ingredienteRepository;

	@Autowired
	ProdottoRepository prodottoRepository;

	// Create and save a new ingredient
	public Ingrediente save(NewIngredientePayload body) {
        if (body.getNome() == null || body.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("The ingredient name cannot be null or empty");
        }

        Ingrediente newIngrediente = new Ingrediente(body.getNome());
        return ingredienteRepository.save(newIngrediente);
    }

	// Get a list of all ingredients
	public List<Ingrediente> getAllIngredienti() {
		return ingredienteRepository.findAll();
	}

	// Torna la lista degli ingredienti impaginata
	public Page<Ingrediente> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

		return ingredienteRepository.findAll(pageable);
	}

	// Get an ingredient by its ID
	public Ingrediente findById(UUID id) throws NotFoundException {
		return ingredienteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// Update an ingredient by its ID
	public Ingrediente findByIdAndUpdate(UUID id, NewIngredientePayload body) throws NotFoundException {
        if (body.getNome() == null || body.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("The ingredient name cannot be null or empty");
        }

        Ingrediente found = findById(id);
        found.setNome(body.getNome());

        return ingredienteRepository.save(found);
    }

	// Delete an ingredient by its ID
	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Ingrediente existingIngredient = findById(id);

		// Get all Prodottos that contain this Ingrediente
		List<Prodotto> prodotti = existingIngredient.getProdotti();

		// Remove the Ingrediente from each Prodotto
		for (Prodotto prodotto : prodotti) {
			prodotto.getIngredienti().remove(existingIngredient);
		}

		// Save the updated Prodottos
		for (Prodotto prodotto : prodotti) {
			prodottoRepository.save(prodotto);
		}
		ingredienteRepository.delete(existingIngredient);
	}
}

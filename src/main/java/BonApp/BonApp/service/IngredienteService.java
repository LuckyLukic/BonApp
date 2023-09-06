package BonApp.BonApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


import BonApp.BonApp.entities.Ingredienti;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewIngredientePayload;
import BonApp.BonApp.repositories.IngredienteRepository;


@Service
public class IngredienteService {

    @Autowired
    IngredienteRepository ingredienteRepository;

    // Create and save a new ingredient
    public Ingredienti save(NewIngredientePayload body) {
    	
    	Ingredienti newIngrediente = new Ingredienti(body.getNome());
        return ingredienteRepository.save(newIngrediente);
    }

    // Get a list of all ingredients
    public List<Ingredienti> getAllIngredienti() {
        return ingredienteRepository.findAll();
    }
    
   // Torna la lista degli ingredienti impaginata
 	public Page<Ingredienti> find(int page, int size, String sort) {
 		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

 		return ingredienteRepository.findAll(pageable);
 	}

    // Get an ingredient by its ID
    public Ingredienti findById(UUID id) throws NotFoundException {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    // Update an ingredient by its ID
    public Ingredienti findByIdAndUpdate(UUID id, Ingredienti updatedIngredient) throws NotFoundException {
        Ingredienti existingIngrediente = findById(id);
        // Update the properties of the existing ingredient with the values from updatedIngredient
        existingIngrediente.setName(updatedIngredient.getName());
        // You can update other properties here as needed
        return ingredienteRepository.save(existingIngrediente);
    }

    // Delete an ingredient by its ID
    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Ingredienti existingIngredient = findById(id);
        ingredienteRepository.delete(existingIngredient);
    }
}

package BonApp.BonApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import BonApp.BonApp.entities.Ingredienti;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.repositories.IngredienteRepository;


@Service
public class IngredienteService {

    @Autowired
    IngredienteRepository ingredientiRepository;

    // Create and save a new ingredient
    public Ingredienti save(Ingredienti ingredienti) {
        return ingredientiRepository.save(ingredienti);
    }

    // Get a list of all ingredients
    public List<Ingredienti> getAllIngredients() {
        return ingredientiRepository.findAll();
    }

    // Get an ingredient by its ID
    public Ingredienti getIngredientById(UUID id) throws NotFoundException {
        return ingredientiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    // Update an ingredient by its ID
    public Ingredienti updateIngredient(UUID id, Ingredienti updatedIngredient) throws NotFoundException {
        Ingredienti existingIngredient = getIngredientById(id);
        // Update the properties of the existing ingredient with the values from updatedIngredient
        existingIngredient.setName(updatedIngredient.getName());
        // You can update other properties here as needed
        return ingredientiRepository.save(existingIngredient);
    }

    // Delete an ingredient by its ID
    public void deleteIngredient(UUID id) throws NotFoundException {
        Ingredienti existingIngredient = getIngredientById(id);
        ingredientiRepository.delete(existingIngredient);
    }
}

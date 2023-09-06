package BonApp.BonApp.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import BonApp.BonApp.entities.Ingredienti;
import BonApp.BonApp.payload.NewIngredientePayload;
import BonApp.BonApp.service.IngredienteService;

    @RestController
	@RequestMapping("/ingredienti")
	public class IngredientiController {

	    @Autowired
	    IngredienteService ingredienteService;

	    @PostMapping("")
	    @ResponseStatus(HttpStatus.CREATED)
	    public Ingredienti saveIngredienti(@RequestBody NewIngredientePayload body) {
	        Ingredienti createIngredienti = ingredienteService.save(body);
	        return createIngredienti;
	    }

	    @GetMapping("")
	    // @PreAuthorize("hasAuthority('ADMIN')")
	    public Page<Ingredienti> getIngredienti(@RequestParam(defaultValue = "0") int page,
	                                            @RequestParam(defaultValue = "10") int size,
	                                            @RequestParam(defaultValue = "id") String sortBy) {
	        return ingredienteService.find(page, size, sortBy);
	    }

	    @GetMapping("/{ingredientiId}")
	    // @PreAuthorize("hasAuthority('ADMIN')")
	    public Ingredienti findById(@PathVariable UUID ingredientiId) {
	        return ingredienteService.findById(ingredientiId);
	    }

	    @PutMapping("/{ingredientiId}")
	    // @PreAuthorize("hasAuthority('ADMIN')")
	    public Ingredienti updateIngredienti(@PathVariable UUID ingredientiId, @RequestBody Ingredienti ingredienti) {
	        return ingredienteService.findByIdAndUpdate(ingredientiId, ingredienti);
	    }

	    @DeleteMapping("/{ingredientiId}")
	    // @PreAuthorize("hasAuthority('ADMIN')")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteIngredienti(@PathVariable UUID ingredientiId) {
	        ingredienteService.findByIdAndDelete(ingredientiId);
	    }
	}



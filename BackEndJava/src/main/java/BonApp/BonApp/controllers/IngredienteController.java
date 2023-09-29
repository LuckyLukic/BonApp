package BonApp.BonApp.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import BonApp.BonApp.entities.Ingrediente;
import BonApp.BonApp.payload.NewIngredientePayload;
import BonApp.BonApp.service.IngredienteService;

@RestController
@RequestMapping("/ingredienti")
public class IngredienteController {

	@Autowired
	IngredienteService ingredienteService;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Ingrediente saveIngredienti(@RequestBody NewIngredientePayload body) {
		Ingrediente createIngredienti = ingredienteService.save(body);
		return createIngredienti;
	}

	@GetMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<Ingrediente> getIngredienti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return ingredienteService.find(page, size, sortBy);
	}

	@GetMapping("/{ingredientiId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Ingrediente findById(@PathVariable UUID ingredientiId) {
		return ingredienteService.findById(ingredientiId);
	}

	@PutMapping("/{ingredientiId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Ingrediente updateIngredienti(@PathVariable UUID ingredientiId, @RequestBody NewIngredientePayload body) {
		return ingredienteService.findByIdAndUpdate(ingredientiId, body);
	}

	@DeleteMapping("/{ingredientiId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIngredienti(@PathVariable UUID ingredientiId) {
		ingredienteService.findByIdAndDelete(ingredientiId);
	}
}

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

import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewProdottoPayload;
import BonApp.BonApp.service.ProdottoService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Prodotto saveProdotto(@RequestBody NewProdottoPayload body) {
    	Prodotto createdProdotto = prodottoService.save(body);
        return createdProdotto;
    }

    @GetMapping("")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Prodotto> getProdotti(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return prodottoService.find(page, size, sortBy);
    }

    @GetMapping("/{prodottoId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto findById(@PathVariable UUID prodottoId) throws NotFoundException {
        return prodottoService.findById(prodottoId);
    }

    @PutMapping("/{prodottoId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto updateProdotto(@PathVariable UUID prodottoId, @RequestBody Prodotto updatedProdotto) throws NotFoundException {
        return prodottoService.findByIdAndUpdate(prodottoId, updatedProdotto);
    }

    @DeleteMapping("/{prodottoId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProdotto(@PathVariable UUID prodottoId) throws NotFoundException {
        prodottoService.findByIdAndDelete(prodottoId);
    }
}

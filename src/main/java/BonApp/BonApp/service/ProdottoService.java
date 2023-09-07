package BonApp.BonApp.service;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import BonApp.BonApp.entities.Prodotto;

import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewProdottoPayload;
import BonApp.BonApp.repositories.ProdottoRepository;


@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    public Prodotto save(NewProdottoPayload body) {
        Prodotto newProdotto = new Prodotto(
                body.getNome(),
                body.getDescrizione(),
                body.getPrezzo(),
                body.getCategoria(),
                body.getIngredienti(),
                body.getImgUrl()
        );
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
        // Update the properties of the existing product with the values from updatedProdotto
        existingProdotto.setName(updatedProdotto.getName());
        existingProdotto.setDescription(updatedProdotto.getDescription());
        existingProdotto.setPrice(updatedProdotto.getPrice());
        existingProdotto.setCategoria(updatedProdotto.getCategoria());
        existingProdotto.setIngredienti(updatedProdotto.getIngredienti());
        existingProdotto.setImgUrl(updatedProdotto.getImgUrl());
        // You can update other properties here as needed
        return prodottoRepository.save(existingProdotto);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Prodotto existingProdotto = findById(id);
        prodottoRepository.delete(existingProdotto);
    }
    
    public Page<Prodotto> findByPartialName(String partialName, int page, int size, String sortBy) {
        Pageable prodottiPageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prodottoRepository.findByNameContainingIgnoreCase(partialName, prodottiPageable);
    }
}

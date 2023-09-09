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
import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewProdottoPayload;
import BonApp.BonApp.repositories.IngredienteRepository;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.UserRepository;

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
    	 if (body.getPrezzo() != null) {
             double prezzoValue = body.getPrezzo().doubleValue();
             // Now you can use prezzoValue in your code
             Prodotto newProdotto = new Prodotto(
                     body.getNome(),
                     body.getDescrizione(),
                     body.getPrezzo(), 
                     body.getCategoria(),
                     body.getIngredienti(),
                     body.getImgUrl()
             );
             return prodottoRepository.save(newProdotto);
         } else {
             // Handle the case where prezzo is null, e.g., throw an exception or provide a default value
             throw new IllegalArgumentException("Prezzo cannot be null");
         }
     
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
        existingProdotto.setNome(updatedProdotto.getNome());
        existingProdotto.setDescrizione(updatedProdotto.getDescrizione());
        existingProdotto.setPrezzo(updatedProdotto.getPrezzo());
        existingProdotto.setCategoria(updatedProdotto.getCategoria());
        existingProdotto.setIngredienti(updatedProdotto.getIngredienti());
        existingProdotto.setImgUrl(updatedProdotto.getImgUrl());
        // You can update other properties here as needed
        return prodottoRepository.save(existingProdotto);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Prodotto existingProdotto = findById(id);
        
        List<Ingrediente> ingredienti = existingProdotto.getIngredienti();
        
        for (Ingrediente ingrediente : ingredienti) {
            ingrediente.getProdotti().remove(existingProdotto);
        }
        
        for (Ingrediente ingrediente : ingredienti) {
            ingredienteRepository.save(ingrediente);
        }
        
        // Retrieve and update all OrdineSingolo entities that reference the Prodotto
        List<OrdineSingolo> ordini = ordineSingoloRepository.findByProdottiContaining(existingProdotto);
        for (OrdineSingolo ordine : ordini) {
            ordine.getProdotti().remove(existingProdotto);
            ordineSingoloRepository.save(ordine);
        }
        
        // Retrieve and update all User entities that have the Prodotto in their preferred products list
        List<User> users = userRepository.findByProdottiPreferitiContaining(existingProdotto);
        for (User user : users) {
            user.getProdottiPreferiti().remove(existingProdotto);
            userRepository.save(user);
        }
        
        // Finally, delete the Prodotto
        prodottoRepository.delete(existingProdotto);
    }
    
    public Page<Prodotto> findByPartialName(String partialName, int page, int size, String sortBy) {
        Pageable prodottiPageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prodottoRepository.findByNomeContainingIgnoreCase(partialName, prodottiPageable);
    }
    
    public List<Prodotto> findProductsByIds(List<UUID> ids) {
        return prodottoRepository.findAllById(ids);
    }
   
}

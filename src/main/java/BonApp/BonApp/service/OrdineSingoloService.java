package BonApp.BonApp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.entities.Prodotto;
import BonApp.BonApp.entities.User;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewOrdineSingoloPayload;
import BonApp.BonApp.repositories.OrdineSingoloRepository;
import BonApp.BonApp.repositories.ProdottoRepository;
import BonApp.BonApp.repositories.UserRepository;

@Service
public class OrdineSingoloService {

	 @Autowired
	    OrdineSingoloRepository ordineSingoloRepository;
	    
	    @Autowired
	    UserRepository userRepository;
	    
	    @Autowired
	    ProdottoRepository prodottoRepository;
	    
	   
	    public OrdineSingolo save(NewOrdineSingoloPayload body) throws NotFoundException {
	        User user = userRepository.findById(body.getUserId())
	                .orElseThrow(() -> new NotFoundException("User not found with ID: " + body.getUserId()));

	        List<Prodotto> prodotti = prodottoRepository.findAllById(body.getProdotti());

	        if (prodotti.size() != body.getProdotti().size()) {
	            throw new NotFoundException("Some products were not found");
	        }
	        
	        double totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();

	        OrdineSingolo newSingleOrder = new OrdineSingolo(user, prodotti);
	        newSingleOrder.setTotalPrice(totalPrice);
	        newSingleOrder.setDataOrdine(LocalDate.now());
	        newSingleOrder.setOraOrdine(LocalTime.now());
	        return ordineSingoloRepository.save(newSingleOrder);
	    }
	    

	    public Page<OrdineSingolo> find(int page, int size, String sort) {
	    	 Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
	        return ordineSingoloRepository.findAll(pageable);
	    }

	    public OrdineSingolo findById(UUID id) throws NotFoundException {
	        return ordineSingoloRepository.findById(id)
	                .orElseThrow(() -> new NotFoundException("Single Order not found with ID: " + id));
	    }

	    public OrdineSingolo findByIdAndUpdate(UUID id, NewOrdineSingoloPayload body) throws NotFoundException {
	    	OrdineSingolo existingSingleOrder = findById(id);

	        User user = userRepository.findById(body.getUserId())
	                .orElseThrow(() -> new NotFoundException("User not found with ID: " + body.getUserId()));

	        List<Prodotto> prodotti = prodottoRepository.findAllById(body.getProdotti());

	        if (prodotti.size() != body.getProdotti().size()) {
	            throw new NotFoundException("Some products were not found");
	        }
	        
	        double totalPrice = prodotti.stream().mapToDouble(Prodotto::getPrezzo).sum();

	        existingSingleOrder.setUser(user);
	        existingSingleOrder.setProdotti(prodotti);
	        existingSingleOrder.setTotalPrice(totalPrice);
	        existingSingleOrder.setDataOrdine(body.getDataOrdine() != null ? body.getDataOrdine() : existingSingleOrder.getDataOrdine());
	        existingSingleOrder.setOraOrdine(body.getOraOrdine() != null ? body.getOraOrdine() : existingSingleOrder.getOraOrdine());

	        OrdineSingolo savedSingleOrder = ordineSingoloRepository.save(existingSingleOrder);
	    
	        return savedSingleOrder;
	    
	    }

	    public void findByIdAndDelete(UUID id) throws NotFoundException {
	       OrdineSingolo existingSingleOrder = findById(id);
	        ordineSingoloRepository.delete(existingSingleOrder);
	    }
	    
	    public OrdineSingolo processCheckout(UUID userId, UUID ordineSingoloId) throws IllegalStateException {
	        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
	        OrdineSingolo ordineSingolo = ordineSingoloRepository.findById(ordineSingoloId).orElseThrow(() -> new NotFoundException("Order not found"));

	        ordineSingolo.checkout();

	        return ordineSingoloRepository.save(ordineSingolo);
	    }
}

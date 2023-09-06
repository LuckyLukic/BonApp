package BonApp.BonApp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import BonApp.BonApp.entities.Ordine;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewOrdinePayload;
import BonApp.BonApp.repositories.OrdineRepository;

@Service
public class OrdineService {

    @Autowired
    OrdineRepository ordineRepository;

    public Ordine save(NewOrdinePayload body) {
        Ordine newOrdine = new Ordine(body.getUser(), body.getProdotto());
        return ordineRepository.save(newOrdine);
    }

    public Page<Ordine> find(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ordineRepository.findAll(pageable);
    }

    public Ordine findById(UUID id) throws NotFoundException {
        return ordineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Ordine findByIdAndUpdate(UUID id, NewOrdinePayload body) throws NotFoundException {
        Ordine existingOrdine = findById(id);
        existingOrdine.setDataOrdine(body.getData());
        existingOrdine.setOraOrdine(body.getOra());
        existingOrdine.setProdotto(body.getProdotto());
        return ordineRepository.save(existingOrdine);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Ordine existingOrdine = findById(id);
        ordineRepository.delete(existingOrdine);
    }
}

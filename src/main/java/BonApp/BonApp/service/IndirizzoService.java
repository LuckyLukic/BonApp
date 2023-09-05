package BonApp.BonApp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import BonApp.BonApp.entities.Indirizzo;

import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewIndirizzoPayload;
import BonApp.BonApp.repositories.IndirizzoRepository;


@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzoRepository;

	// SALVA NUOVO INDIRIZZO
	public Indirizzo save(NewIndirizzoPayload body) {
		

		Indirizzo newIndirizzo = new Indirizzo(body.getCap(), body.getCivico(), body.getLocalità(), body.getVia(),
				body.getUser(), body.getComune());
		return indirizzoRepository.save(newIndirizzo);
	}

	// TORNA LA LISTA DEGLI INDIRIZZO
	public List<Indirizzo> getIndirizzi() {
		return indirizzoRepository.findAll();
	}

	// TORNA LA LISTA DEGLI INDIRIZZO CON L'IMPAGINAZIONE
	public Page<Indirizzo> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

		return indirizzoRepository.findAll(pageable);
	}

	// CERCA INDIRIZZO TRAMITE ID
	public Indirizzo findById(UUID id) throws NotFoundException {
		return indirizzoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// CERCA E MODIFICA INDIRIZZO TRAMITE ID
	public Indirizzo findByIdAndUpdate(UUID id, NewIndirizzoPayload body) throws NotFoundException {
		Indirizzo found = this.findById(id);
		found.setCap(body.getCap());
		found.setCivico(body.getCivico());
		found.setLocalità(body.getLocalità());
		found.setVia(body.getVia());
		found.setUser(body.getUser());     
		found.setComune(body.getComune());
		return indirizzoRepository.save(found);
	}

	// CERCA E CANCELLA INDIRIZZO TRAMITE ID
	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Indirizzo found = this.findById(id);
		indirizzoRepository.delete(found);
	}

}

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
import BonApp.BonApp.entities.Indirizzo;
import BonApp.BonApp.payload.NewIndirizzoPayload;
import BonApp.BonApp.service.IndirizzoService;

@RestController
	@RequestMapping("/indirizzi")
	public class IndirizzoController {

		@Autowired
		IndirizzoService indirizzoService;

		@PostMapping("")
		@ResponseStatus(HttpStatus.CREATED)
		public Indirizzo saveIndirizzi(@RequestBody NewIndirizzoPayload body) {
			Indirizzo createdIndirizzo = indirizzoService.save(body);
			return createdIndirizzo;
		}

		@GetMapping("")
//		@PreAuthorize("hasAuthority('ADMIN')")
		public Page<Indirizzo> getIndirizzi(@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "via") String sortBy) {
			return indirizzoService.find(page, size, sortBy);
		}

		@GetMapping("/{indirizzoId}")
		//@PreAuthorize("hasAuthority('ADMIN')")
		public Indirizzo findById(@PathVariable UUID indirizzoId) {
			return indirizzoService.findById(indirizzoId);
		}

		@PutMapping("/{indirizzoId}")
		//@PreAuthorize("hasAuthority('ADMIN')")
		public Indirizzo updateIndirizzo(@PathVariable UUID indirizzoId, @RequestBody NewIndirizzoPayload body) {
			
			System.out.println("Received JSON Payload: " + body);
			return indirizzoService.findByIdAndUpdate(indirizzoId, body);
		}

		@DeleteMapping("/{indirizzoId}")
		//@PreAuthorize("hasAuthority('ADMIN')")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteIndirizzo(@PathVariable UUID indirizzoId) {
			indirizzoService.findByIdAndDelete(indirizzoId);
		}
	}




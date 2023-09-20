package BonApp.BonApp.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.http.ResponseEntity;

import BonApp.BonApp.entities.OrdineSingolo;
import BonApp.BonApp.exceptions.NotFoundException;
import BonApp.BonApp.payload.NewOrdineSingoloPayload;
import BonApp.BonApp.service.OrdineSingoloService;

@RestController
@RequestMapping("/ordine-singolo")

public class OrdineSingoloController {

	@Autowired
	OrdineSingoloService ordineSingoloService;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public OrdineSingolo saveSingleOrder(@RequestBody NewOrdineSingoloPayload body) {
		OrdineSingolo createdSingleOrder = ordineSingoloService.save(body);
		return createdSingleOrder;
	}

	@GetMapping("")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Page<OrdineSingolo> getSingleOrders(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return ordineSingoloService.find(page, size, sortBy);
	}

	@GetMapping("/{singleOrderId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public OrdineSingolo findById(@PathVariable UUID singleOrderId) {
		return ordineSingoloService.findById(singleOrderId);
	}

	@PutMapping("/{singleOrderId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public OrdineSingolo updateSingleOrder(@PathVariable UUID singleOrderId,
			@RequestBody NewOrdineSingoloPayload body) {
		return ordineSingoloService.findByIdAndUpdate(singleOrderId, body);
	}

	@DeleteMapping("/{singleOrderId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSingleOrder(@PathVariable UUID singleOrderId) {
		ordineSingoloService.findByIdAndDelete(singleOrderId);
	}

	@PostMapping("/{userId}/checkout")
	public ResponseEntity<?> processCheckout(@PathVariable UUID userId, @RequestBody UUID ordineSingoloId) {
		try {
			return ResponseEntity.ok(ordineSingoloService.processCheckout(userId, ordineSingoloId));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/search")
	public ResponseEntity<Page<OrdineSingolo>> findByMultipleCriteria(
	        @RequestParam(value = "cap", required = false) String cap,
	        @RequestParam(value = "localita", required = false) String localita,
	        @RequestParam(value = "comune", required = false) String comune,
	        @RequestParam(value = "minPrice", required = false) Double minPrice,
	        @RequestParam(value = "maxPrice", required = false) Double maxPrice,
	        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        Pageable pageable) {

	    Page<OrdineSingolo> ordini = ordineSingoloService.findByMultipleCriteria(cap, localita, comune, minPrice, maxPrice, startDate, endDate, pageable);
	    return ResponseEntity.ok(ordini);
	}
	
	 @GetMapping("/searchByUserId")
	    public ResponseEntity<Page<OrdineSingolo>> findByUserId(
	            @RequestParam("userId") UUID userId,
	            Pageable pageable) {
	        
	        Page<OrdineSingolo> ordini = ordineSingoloService.findOrdersByUserId(userId, pageable);
	        return ResponseEntity.ok(ordini);
	    }
	 
	
}





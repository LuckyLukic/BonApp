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
import BonApp.BonApp.entities.OrdineSingolo;
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
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        return ordineSingoloService.find(page, size, sortBy);
    }

    @GetMapping("/{singleOrderId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public OrdineSingolo findById(@PathVariable UUID singleOrderId) {
        return ordineSingoloService.findById(singleOrderId);
    }

    @PutMapping("/{singleOrderId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public OrdineSingolo updateSingleOrder(@PathVariable UUID singleOrderId, @RequestBody NewOrdineSingoloPayload body) {
        return ordineSingoloService.findByIdAndUpdate(singleOrderId, body);
    }

    @DeleteMapping("/{singleOrderId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSingleOrder(@PathVariable UUID singleOrderId) {
        ordineSingoloService.findByIdAndDelete(singleOrderId);
    }
}




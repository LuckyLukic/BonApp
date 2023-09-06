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

import BonApp.BonApp.entities.Ordine;
import BonApp.BonApp.payload.NewOrdinePayload;
import BonApp.BonApp.service.OrdineService;

@RestController
@RequestMapping("/ordini")
public class OrdineController {

    @Autowired
    OrdineService ordineService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Ordine saveOrdine(@RequestBody NewOrdinePayload body) {
        Ordine createdOrdine = ordineService.save(body);
        return createdOrdine;
    }

    @GetMapping("")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Ordine> getOrdini(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return ordineService.find(page, size, sortBy);
    }

    @GetMapping("/{ordineId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public Ordine findById(@PathVariable UUID ordineId) {
        return ordineService.findById(ordineId);
    }

    @PutMapping("/{ordineId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public Ordine updateOrdine(@PathVariable UUID ordineId, @RequestBody NewOrdinePayload body) {
        return ordineService.findByIdAndUpdate(ordineId, body);
    }

    @DeleteMapping("/{ordineId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdine(@PathVariable UUID ordineId) {
        ordineService.findByIdAndDelete(ordineId);
    }
}

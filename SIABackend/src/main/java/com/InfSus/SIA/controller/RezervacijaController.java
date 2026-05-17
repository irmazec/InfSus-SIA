package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.KanalRezervacije;
import com.InfSus.SIA.model.Rezervacija;
import com.InfSus.SIA.service.RezervacijaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rezervacija")
public class RezervacijaController {
    private final RezervacijaService rezervacijaService;

    public RezervacijaController(RezervacijaService rezervacijaService) {
        this.rezervacijaService = rezervacijaService;
    }

    @GetMapping("/{rezervacijaId}")
    public ResponseEntity<Rezervacija> getReservation(@PathVariable Integer rezervacijaId){
        return ResponseEntity.ok(this.rezervacijaService.getReservation(rezervacijaId));
    }

    @GetMapping
    public ResponseEntity<List<Rezervacija>> getAllReservations(){
        return ResponseEntity.ok(this.rezervacijaService.getAllReservations());
    }

    @PostMapping
    ResponseEntity<HttpStatus> addNewReservation(@RequestBody Rezervacija rezervacija) {
        this.rezervacijaService.addNewReservation(rezervacija);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{rezervacijaId}")
    public ResponseEntity<HttpStatus> updateReservation(@PathVariable Integer rezervacijaId, @RequestBody Rezervacija rezervacija){
        this.rezervacijaService.updateReservation(rezervacijaId, rezervacija);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{rezervacijaId}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable Integer rezervacijaId){
        this.rezervacijaService.deleteReservation(rezervacijaId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

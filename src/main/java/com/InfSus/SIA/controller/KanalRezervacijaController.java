package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.KanalRezervacije;
import com.InfSus.SIA.service.KanalRezervacijeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kanal-rezervacije")
public class KanalRezervacijaController {

    @Autowired
    private KanalRezervacijeService kanalRezervacije;

    public KanalRezervacijaController(KanalRezervacijeService kanalRezervacije) {
        this.kanalRezervacije = kanalRezervacije;
    }

    @GetMapping("/{kanalRezervacijeId}")
    public ResponseEntity<KanalRezervacije> getReservationChannel(@PathVariable Integer kanalRezervacijeId){
        return ResponseEntity.ok(this.kanalRezervacije.getReservationChannel(kanalRezervacijeId));
    }

    @GetMapping
    public ResponseEntity<List<KanalRezervacije>> getAllReservationChannels(){
        return ResponseEntity.ok(this.kanalRezervacije.getAllReservationChannels());
    }

    @PostMapping
    ResponseEntity<HttpStatus> addNewReservationChannel(@RequestBody KanalRezervacije kanalRezervacije) {
        this.kanalRezervacije.addNewReservationChannel(kanalRezervacije);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{kanalRezervacijeId}")
    public ResponseEntity<HttpStatus> updateReservationChannel(@PathVariable Integer kanalRezervacijeId, @RequestBody KanalRezervacije kanalRezervacije){
        this.kanalRezervacije.updateReservationChannel(kanalRezervacijeId, kanalRezervacije);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{kanalRezervacijeId}")
    public ResponseEntity<HttpStatus> deleteReservationChannel(@PathVariable Integer kanalRezervacijeId){
        this.kanalRezervacije.deleteReservationChannel(kanalRezervacijeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.StatusRezervacije;
import com.InfSus.SIA.service.StatusRezervacijeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/status-rezervacije")
public class StatusRezervacijeController {

    private final StatusRezervacijeService statusRezervacijeService;

    public StatusRezervacijeController(StatusRezervacijeService statusRezervacijeService) {
        this.statusRezervacijeService = statusRezervacijeService;
    }


    @GetMapping("/{statusRezervacijeId}")
    public ResponseEntity<StatusRezervacije> getReservationStatus (@PathVariable Integer statusRezervacijeId){
        return ResponseEntity.ok(this.statusRezervacijeService.getReservationStatus(statusRezervacijeId));
    }

    @GetMapping
    public ResponseEntity<List<StatusRezervacije>> getAllReservationStatus(){
        return ResponseEntity.ok(this.statusRezervacijeService.getAllReservationStatus());
    }
}

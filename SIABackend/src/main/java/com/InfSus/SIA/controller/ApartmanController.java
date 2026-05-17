package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.Apartman;
import com.InfSus.SIA.service.ApartmanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/apartman")
public class ApartmanController {

    private final ApartmanService apartmanService;

    public ApartmanController(ApartmanService apartmanService) {
        this.apartmanService = apartmanService;
    }

    @GetMapping("/{apartmanId}")
    public ResponseEntity<Apartman> getApartman (@PathVariable Integer apartmanId){
        return ResponseEntity.ok(this.apartmanService.getApartman(apartmanId));
    }

    @GetMapping
    public ResponseEntity<List<Apartman>> getAllApartments(){
        return ResponseEntity.ok(this.apartmanService.getAllApartments());
    }
}

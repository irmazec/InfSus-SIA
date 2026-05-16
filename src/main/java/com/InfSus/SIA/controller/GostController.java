package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.Gost;
import com.InfSus.SIA.service.GostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gost")
public class GostController {

    private final GostService gostService;

    public GostController(GostService gostService) {
        this.gostService = gostService;
    }

    @GetMapping("/{gostId}")
    public ResponseEntity<Gost> getGuest (@PathVariable Integer gostId){
        return ResponseEntity.ok(this.gostService.getGuest(gostId));
    }

    @GetMapping
    public ResponseEntity<List<Gost>> getAllGuests(){
        return ResponseEntity.ok(this.gostService.getAllGuests());
    }

    @PostMapping
    ResponseEntity<HttpStatus> addNewGuest(@RequestBody Gost gost) {
        this.gostService.addNewGuest(gost);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{gostId}")
    public ResponseEntity<HttpStatus> updateGuest(@PathVariable Integer gostId, @RequestBody Gost gost){
        this.gostService.updateGuest(gostId, gost);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{gostId}")
    public ResponseEntity<HttpStatus> deleteGuest(@PathVariable Integer gostId){
        this.gostService.deleteGuest(gostId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

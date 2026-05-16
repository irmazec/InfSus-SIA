package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.Uplata;
import com.InfSus.SIA.service.UplataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uplata")
public class UplataController {

    private final UplataService uplataService;

    public UplataController(UplataService uplataService) {
        this.uplataService = uplataService;
    }

    @GetMapping("/{uplataId}")
    public ResponseEntity<Uplata> getPayment(@PathVariable Integer uplataId){
        return ResponseEntity.ok(this.uplataService.getPayment(uplataId));
    }

    @GetMapping
    public ResponseEntity<List<Uplata>> getAllPayments(){
        return ResponseEntity.ok(this.uplataService.getAllPayments());
    }

    @PostMapping
    ResponseEntity<HttpStatus> addNewPayment(@RequestBody Uplata uplata) {
        this.uplataService.addNewPayment(uplata);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{uplataId}")
    public ResponseEntity<HttpStatus> updatePayment(@PathVariable Integer uplataId, @RequestBody Uplata uplata){
        this.uplataService.updatePayment(uplataId, uplata);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{uplataId}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Integer uplataId){
        this.uplataService.deletePayment(uplataId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

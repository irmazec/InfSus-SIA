package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.StatusUplate;
import com.InfSus.SIA.service.StatusUplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/status-uplate")
public class StatusUplateController {
    private final StatusUplateService statusUplateService;

    public StatusUplateController(StatusUplateService statusUplateService) {
        this.statusUplateService = statusUplateService;
    }

    @GetMapping("/{statusUplateId}")
    public ResponseEntity<StatusUplate> getPaymentStatus (@PathVariable Integer statusUplateId){
        return ResponseEntity.ok(this.statusUplateService.getPaymentStatus(statusUplateId));
    }

    @GetMapping
    public ResponseEntity<List<StatusUplate>> getAllPaymentStatus(){
        return ResponseEntity.ok(this.statusUplateService.getAllPaymentStatus());
    }
}

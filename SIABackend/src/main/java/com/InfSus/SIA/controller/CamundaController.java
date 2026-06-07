package com.InfSus.SIA.controller;

import com.InfSus.SIA.service.CamundaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/uplata-proces")
public class CamundaController {

    @Autowired
    private CamundaService camundaService;

    @GetMapping("/zadaci")
    public ResponseEntity<List<Map<String, Object>>> getTasks(){
        return ResponseEntity.ok(camundaService.getTasksForUser());
    }

    @GetMapping("/{processInstanceId}/status")
    public ResponseEntity<Map<String, Object>> status(@PathVariable String processInstanceId){
        return ResponseEntity.ok(camundaService.getProcessStatus(processInstanceId));
    }

    @PostMapping("/start")
    public ResponseEntity<String> startProcess(@RequestBody Map<String, Object> req) {
        String processInstanceId = camundaService.startProcess(
                Long.valueOf(req.get("rezervacijaId").toString()),
                req.get("gostEmail").toString()
        );
        return ResponseEntity.ok(processInstanceId);
    }

    @PostMapping("/zadaci/{zadatakId}/provedi")
    public ResponseEntity<Void> provediEvidenciju(
            @PathVariable String zadatakId,
            @RequestBody Map<String, Object> body) {
        try{
            camundaService.registerPayment(zadatakId,
                    Double.valueOf(body.get("iznos").toString()),
                    Boolean.valueOf(body.get("podaciIspravni").toString()),
                    Boolean.valueOf(body.get("uplataOtplacena").toString())
            );
            return ResponseEntity.ok().build();
        }catch(RuntimeException re){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }


    }

    @PostMapping("/zadaci/{zadatakId}/ispravi")
    public ResponseEntity<Void> ispraviPodatke(
            @PathVariable String zadatakId,
            @RequestBody Map<String, Object> body) {
        camundaService.correctData(zadatakId, body);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/zadaci/{zadatakId}/placeno")
    public ResponseEntity<Void> placenaRezervacija(@PathVariable String zadatakId) {
        camundaService.changeStatusToPayed(zadatakId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("zadaci/{processInstanceId}/plati")
    public ResponseEntity<Void> platiRezervaciju(@PathVariable String processInstanceId){
        camundaService.payReservationFully(processInstanceId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/zadaci/{zadatakId}/izbrisi")
    public ResponseEntity<Void> izbrisiRezervaciju(@PathVariable String zadatakId) {
        camundaService.deletePaymentAndReservation(zadatakId);
        return ResponseEntity.ok().build();
    }
}

package com.InfSus.SIA.service;

import com.InfSus.SIA.model.Rezervacija;
import com.InfSus.SIA.model.Uplata;
import com.InfSus.SIA.repository.UplataRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UplataService {

    private final UplataRepository uplataRepository;

    public UplataService(UplataRepository uplataRepository) {
        this.uplataRepository = uplataRepository;
    }


    public @Nullable Uplata getPayment(Integer uplataId) {
        return uplataRepository.findById(uplataId).orElse(null);
    }

    public List<Uplata> getAllPayments() {
        return uplataRepository.findAll();
    }

    public List<Uplata> getAllPaymentsByReservation(Integer rezervacijaId){
        return uplataRepository.findByRezervacija_IdRezervacija(rezervacijaId);
    }

    public void addNewPayment(Uplata uplata) {
        if (PriceValidation.validatePrice(uplata.getIznos())){
            uplataRepository.save(uplata);
        }else{
            throw new RuntimeException("Netocan iznos!");
        }
    }

    public void updatePayment(Integer uplataId, Uplata uplata) {
        Uplata existing = uplataRepository.findById(uplataId)
                .orElseThrow(() -> new RuntimeException("Uplata nije pronađen: " + uplataId));
        existing.setNapomena(uplata.getNapomena());
        existing.setIznos(uplata.getIznos());
        existing.setRezervacija(uplata.getRezervacija());
        existing.setStatusUplate(uplata.getStatusUplate());
        uplataRepository.save(existing);
    }

    public void deletePayment(Integer uplataId) {
        uplataRepository.deleteById(uplataId);
    }
}

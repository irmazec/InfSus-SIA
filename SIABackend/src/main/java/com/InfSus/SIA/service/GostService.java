package com.InfSus.SIA.service;

import com.InfSus.SIA.model.Gost;
import com.InfSus.SIA.repository.GostRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GostService {

    private final GostRepository gostRepository;

    public GostService(GostRepository gostRepository) {
        this.gostRepository = gostRepository;
    }

    public @Nullable Gost getGuest(Integer gostId) {
        return gostRepository.findById(gostId).orElse(null);
    }


    public List<Gost> getAllGuests() {
        return gostRepository.findAll();
    }

    public void addNewGuest(Gost gost) {
        gostRepository.save(gost);
    }

    public void updateGuest(Integer gostId, Gost gost) {
        Gost existing = gostRepository.findById(gostId)
                .orElseThrow(() -> new RuntimeException("Gost nije pronađen: " + gostId));
        existing.setIme(gost.getIme());
        existing.setPrezime(gost.getPrezime());
        existing.setEmail(gost.getEmail());
        existing.setBrojTelefona(gost.getBrojTelefona());
        existing.setDrzavljanstvo(gost.getDrzavljanstvo());
        gostRepository.save(existing);
    }

    public void deleteGuest(Integer gostId) {
        gostRepository.deleteById(gostId);
    }
}

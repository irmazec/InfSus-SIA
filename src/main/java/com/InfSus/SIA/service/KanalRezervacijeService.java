package com.InfSus.SIA.service;

import com.InfSus.SIA.model.KanalRezervacije;
import com.InfSus.SIA.repository.KanalRezervacijeRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanalRezervacijeService {

    private final KanalRezervacijeRepository kanalRezervacijeRepository;

    public KanalRezervacijeService(KanalRezervacijeRepository kanalRezervacijeRepository) {
        this.kanalRezervacijeRepository = kanalRezervacijeRepository;
    }

    public @Nullable KanalRezervacije getReservationChannel(Integer kanalRezervacijeId) {
        return kanalRezervacijeRepository.findById(kanalRezervacijeId).orElse(null);
    }

    public List<KanalRezervacije> getAllReservationChannels() {
        return kanalRezervacijeRepository.findAll();
    }

    public void addNewReservationChannel(KanalRezervacije kanalRezervacije) {
        kanalRezervacijeRepository.save(kanalRezervacije);
    }

    public void updateReservationChannel(Integer kanalRezervacijeId, KanalRezervacije kanalRezervacije) {
        KanalRezervacije existing = kanalRezervacijeRepository.findById(kanalRezervacijeId)
                .orElseThrow(() -> new RuntimeException("Kanal rezervacije nije pronađen: " + kanalRezervacijeId));
        existing.setNaziv(kanalRezervacije.getNaziv());
        kanalRezervacijeRepository.save(existing);
    }

    public void deleteReservationChannel(Integer kanalRezervacijeId) {
        kanalRezervacijeRepository.deleteById(kanalRezervacijeId);
    }
}

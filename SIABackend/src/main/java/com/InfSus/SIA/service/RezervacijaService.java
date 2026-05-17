package com.InfSus.SIA.service;

import com.InfSus.SIA.model.Rezervacija;
import com.InfSus.SIA.repository.RezervacijaRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RezervacijaService {
    private final RezervacijaRepository rezervacijaRepository;

    public RezervacijaService(RezervacijaRepository rezervacijaRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
    }

    public @Nullable Rezervacija getReservation(Integer rezervacijaId) {
        return rezervacijaRepository.findById(rezervacijaId).orElse(null);
    }

    public List<Rezervacija> getAllReservations() {
        return rezervacijaRepository.findAll();
    }

    public void addNewReservation(Rezervacija rezervacija) {
        rezervacijaRepository.save(rezervacija);
    }

    public void updateReservation(Integer rezervacijaId, Rezervacija rezervacija) {
        Rezervacija existing = rezervacijaRepository.findById(rezervacijaId)
                .orElseThrow(() -> new RuntimeException("Rezervacija nije pronađen: " + rezervacijaId));
        existing.setApartman(rezervacija.getApartman());
        existing.setBrojOsoba(rezervacija.getBrojOsoba());
        existing.setDatumDo(rezervacija.getDatumDo());
        existing.setDatumOd(rezervacija.getDatumOd());
        existing.setKanalRezervacije(rezervacija.getKanalRezervacije());
        existing.setStatusRezervacije(rezervacija.getStatusRezervacije());
        existing.setUkupnaCijena(rezervacija.getUkupnaCijena());
        rezervacijaRepository.save(existing);
    }

    public void deleteReservation(Integer rezervacijaId) {
        rezervacijaRepository.deleteById(rezervacijaId);
    }
}

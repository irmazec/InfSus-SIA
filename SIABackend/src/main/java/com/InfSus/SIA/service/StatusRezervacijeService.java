package com.InfSus.SIA.service;

import com.InfSus.SIA.model.StatusRezervacije;
import com.InfSus.SIA.repository.StatusRezervacijeRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusRezervacijeService {

    private final StatusRezervacijeRepository statusRezervacijeRepository;

    public StatusRezervacijeService(StatusRezervacijeRepository statusRezervacijeRepository) {
        this.statusRezervacijeRepository = statusRezervacijeRepository;
    }

    public @Nullable StatusRezervacije getReservationStatus(Integer reservationStatusId) {
        return statusRezervacijeRepository.findById(reservationStatusId).orElse(null);
    }

    public List<StatusRezervacije> getAllReservationStatus() {
        return statusRezervacijeRepository.findAll();
    }
}

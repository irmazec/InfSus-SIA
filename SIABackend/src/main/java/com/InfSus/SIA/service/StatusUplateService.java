package com.InfSus.SIA.service;

import com.InfSus.SIA.model.StatusUplate;
import com.InfSus.SIA.repository.StatusUplateRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusUplateService {
    private final StatusUplateRepository statusUplateRepository;

    public StatusUplateService(StatusUplateRepository statusUplateRepository) {
        this.statusUplateRepository = statusUplateRepository;
    }

    public @Nullable StatusUplate getPaymentStatus(Integer paymentStatusId) {
        return statusUplateRepository.findById(paymentStatusId).orElse(null);
    }

    public List<StatusUplate> getAllPaymentStatus() {
        return statusUplateRepository.findAll();
    }
}

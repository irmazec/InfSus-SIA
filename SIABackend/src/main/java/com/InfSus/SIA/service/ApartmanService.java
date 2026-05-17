package com.InfSus.SIA.service;

import com.InfSus.SIA.model.Apartman;
import com.InfSus.SIA.model.Gost;
import com.InfSus.SIA.repository.ApartmanRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmanService {

    private final ApartmanRepository apartmanRepository;

    public ApartmanService(ApartmanRepository apartmanRepository) {
        this.apartmanRepository = apartmanRepository;
    }

    public @Nullable Apartman getApartman(Integer apartmanId) {
        return apartmanRepository.findById(apartmanId).orElse(null);
    }

    public List<Apartman> getAllApartments() {
        return apartmanRepository.findAll();
    }
}

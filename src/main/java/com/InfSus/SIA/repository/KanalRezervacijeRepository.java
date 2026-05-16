package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.KanalRezervacije;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanalRezervacijeRepository extends JpaRepository<KanalRezervacije, Integer> {}

package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.StatusRezervacije;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRezervacijeRepository extends JpaRepository<StatusRezervacije, Integer> {}
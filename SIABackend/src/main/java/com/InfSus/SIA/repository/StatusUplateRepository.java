package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.StatusUplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusUplateRepository extends JpaRepository<StatusUplate, Integer> {}

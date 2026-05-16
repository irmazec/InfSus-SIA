package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.Gost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GostRepository extends JpaRepository<Gost, Integer> {}


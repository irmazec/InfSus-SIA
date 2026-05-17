package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.Uplata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UplataRepository extends JpaRepository<Uplata, Integer> {}

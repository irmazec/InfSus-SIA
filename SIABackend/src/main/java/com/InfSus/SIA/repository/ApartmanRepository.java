package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.Apartman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmanRepository extends JpaRepository<Apartman, Integer> {}

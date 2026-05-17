package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.Uplata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UplataRepository extends JpaRepository<Uplata, Integer> {
    List<Uplata> findByRezervacija_IdRezervacija(Integer idRezervacija);
}

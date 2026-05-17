package com.InfSus.SIA.service;

import com.InfSus.SIA.model.*;
import com.InfSus.SIA.repository.RezervacijaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RezervacijaServiceTest {

    @Mock
    private RezervacijaRepository rezervacijaRepository;

    @InjectMocks
    private RezervacijaService rezervacijaService;

    private Rezervacija rezervacija;

    @BeforeEach
    void setUp() {
        rezervacija = new Rezervacija();
        rezervacija.setDatumOd(LocalDate.of(2025, 6, 1));
        rezervacija.setDatumDo(LocalDate.of(2025, 6, 9));
        rezervacija.setUkupnaCijena(BigDecimal.valueOf(890));
        rezervacija.setBrojOsoba(3);
    }

    @Test
    void shouldReturnRezervacijaById() {
        when(rezervacijaRepository.findById(1)).thenReturn(Optional.of(rezervacija));

        Rezervacija result = rezervacijaService.getReservation(1);

        assertThat(result).isNotNull();
        assertThat(result.getBrojOsoba()).isEqualTo(3);
        verify(rezervacijaRepository, times(1)).findById(1);
    }

    @Test
    void shouldReturnNull() {
        when(rezervacijaRepository.findById(99)).thenReturn(Optional.empty());

        Rezervacija result = rezervacijaService.getReservation(99);

        assertThat(result).isNull();
    }

    @Test
    void shouldReturnAllRezervacije() {
        when(rezervacijaRepository.findAll()).thenReturn(List.of(rezervacija));

        List<Rezervacija> result = rezervacijaService.getAllReservations();

        assertThat(result).hasSize(1);
        verify(rezervacijaRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveNewRezervacija() {
        rezervacijaService.addNewReservation(rezervacija);

        verify(rezervacijaRepository, times(1)).save(rezervacija);
    }

    @Test
    void shouldUpdateExistingRezervacija() {
        Rezervacija updated = new Rezervacija();
        updated.setDatumOd(LocalDate.of(2025, 7, 1));
        updated.setDatumDo(LocalDate.of(2025, 7, 7));
        updated.setUkupnaCijena(BigDecimal.valueOf(900));
        updated.setBrojOsoba(3);

        when(rezervacijaRepository.findById(1)).thenReturn(Optional.of(rezervacija));

        rezervacijaService.updateReservation(1, updated);

        assertThat(rezervacija.getBrojOsoba()).isEqualTo(3);
        assertThat(rezervacija.getUkupnaCijena()).isEqualTo(BigDecimal.valueOf(900));
        verify(rezervacijaRepository, times(1)).save(rezervacija);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentRezervacija() {
        when(rezervacijaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                rezervacijaService.updateReservation(99, new Rezervacija())
        );
    }

    @Test
    void shouldDeleteRezervacija() {
        rezervacijaService.deleteReservation(1);
        verify(rezervacijaRepository, times(1)).deleteById(1);
    }

}
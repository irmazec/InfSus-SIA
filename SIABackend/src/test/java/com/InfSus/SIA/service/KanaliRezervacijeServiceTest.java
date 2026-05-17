package com.InfSus.SIA.service;

import com.InfSus.SIA.model.KanalRezervacije;
import com.InfSus.SIA.repository.KanalRezervacijeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KanaliRezervacijeServiceTest {
    @Mock
    private KanalRezervacijeRepository kanalRezervacijeRepository;

    @InjectMocks
    private KanalRezervacijeService kanalRezervacijeService;

    private KanalRezervacije kanalRezervacije;

    @BeforeEach
    void setUp() {
        kanalRezervacije = new KanalRezervacije();
        kanalRezervacije.setNaziv("Reddit");
    }

    @Test
    void shouldReturnKanalRezervacijeById() {
        when(kanalRezervacijeRepository.findById(1)).thenReturn(Optional.of(kanalRezervacije));

        KanalRezervacije result = kanalRezervacijeService.getReservationChannel(1);

        assertThat(result).isNotNull();
        assertThat(result.getNaziv()).isEqualTo("Reddit");
        verify(kanalRezervacijeRepository, times(1)).findById(1);
    }

    @Test
    void shouldBeNull(){
        when(kanalRezervacijeRepository.findById(99)).thenReturn(Optional.empty());

        KanalRezervacije result = kanalRezervacijeService.getReservationChannel(99);

        assertThat(result).isNull();
    }

    @Test
    void shouldReturnAllKanaliRezervacije() {
        when(kanalRezervacijeRepository.findAll()).thenReturn(List.of(kanalRezervacije));

        List<KanalRezervacije> result = kanalRezervacijeService.getAllReservationChannels();

        assertThat(result).hasSize(1);
        verify(kanalRezervacijeRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveNewKanalRezervacije() {
        kanalRezervacijeService.addNewReservationChannel(kanalRezervacije);

        verify(kanalRezervacijeRepository, times(1)).save(kanalRezervacije);
    }

    @Test
    void shouldUpdateExistingRezervacija() {
        KanalRezervacije update = new KanalRezervacije();
        update.setNaziv("BnB");

        when(kanalRezervacijeRepository.findById(1)).thenReturn(Optional.of(kanalRezervacije));

        kanalRezervacijeService.updateReservationChannel(1, update);

        assertThat(kanalRezervacije.getNaziv()).isEqualTo("BnB");
        verify(kanalRezervacijeRepository, times(1)).save(kanalRezervacije);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentRezervacija() {
        when(kanalRezervacijeRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                kanalRezervacijeService.updateReservationChannel(99, new KanalRezervacije())
        );
    }

    @Test
    void shouldDeleteRezervacija() {
        kanalRezervacijeService.deleteReservationChannel(1);
        verify(kanalRezervacijeRepository, times(1)).deleteById(1);
    }
}

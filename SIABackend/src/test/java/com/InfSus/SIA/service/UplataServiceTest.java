package com.InfSus.SIA.service;

import com.InfSus.SIA.model.Rezervacija;
import com.InfSus.SIA.model.StatusUplate;
import com.InfSus.SIA.model.Uplata;
import com.InfSus.SIA.repository.UplataRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UplataServiceTest {
    @Mock
    private UplataRepository uplataRepository;

    @InjectMocks
    private UplataService uplataService;

    private Uplata uplata;

    @BeforeEach
    void setUp() {
        uplata = new Uplata();
        uplata.setDatumIzvrsenja(LocalDate.of(2026, 6, 1));
        uplata.setIznos(BigDecimal.valueOf(456));
        uplata.setRezervacija(new Rezervacija());
        StatusUplate statusUplate = new StatusUplate();
        statusUplate.setNaziv("JA");
        uplata.setStatusUplate(statusUplate);
        uplata.setNapomena("");
    }

    @Test
    void shouldReturnUplataById() {
        when(uplataRepository.findById(1)).thenReturn(Optional.of(uplata));

        Uplata result = uplataService.getPayment(1);

        assertThat(result).isNotNull();
        assertThat(result.getIznos()).isEqualTo(BigDecimal.valueOf(456));
        verify(uplataRepository, times(1)).findById(1);
    }

    @Test
    void shouldReturnNull() {
        when(uplataRepository.findById(99)).thenReturn(Optional.empty());

        Uplata result = uplataService.getPayment(99);

        assertThat(result).isNull();
    }

    @Test
    void shouldReturnAllUplate() {
        when(uplataRepository.findAll()).thenReturn(List.of(uplata));

        List<Uplata> result = uplataService.getAllPayments();

        assertThat(result).hasSize(1);
        verify(uplataRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveNewUplata() {
        uplataService.addNewPayment(uplata);

        verify(uplataRepository, times(1)).save(uplata);
    }

    @Test
    void shouldUpdateExistingUplata() {
        Uplata updated = new Uplata();
        updated.setDatumIzvrsenja(LocalDate.of(2026, 7, 1));
        updated.setIznos(BigDecimal.valueOf(900));
        updated.setStatusUplate(uplata.getStatusUplate());
        updated.setNapomena("Krivi izračun");
        updated.setRezervacija(uplata.getRezervacija());

        when(uplataRepository.findById(1)).thenReturn(Optional.of(uplata));

        uplataService.updatePayment(1, updated);

        assertThat(uplata.getIznos()).isEqualTo(BigDecimal.valueOf(900));
        verify(uplataRepository, times(1)).save(uplata);
    }

    @Test
    void shouldDeleteUplata() {
        uplataService.deletePayment(1);
        verify(uplataRepository, times(1)).deleteById(1);
    }
}

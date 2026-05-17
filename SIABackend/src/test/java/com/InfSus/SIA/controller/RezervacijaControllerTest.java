package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.Rezervacija;
import com.InfSus.SIA.service.RezervacijaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebMvcTest(RezervacijaController.class)
public class RezervacijaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RezervacijaService rezervacijaService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturnRezervacijaById() throws Exception {
        when(rezervacijaService.getReservation(1)).thenReturn(rezervacija);

        mockMvc.perform(get("/api/rezervacija/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brojOsoba").value(3));
    }

    @Test
    void shoudReturnAllRezervacije() throws Exception {
        when(rezervacijaService.getAllReservations()).thenReturn(List.of(rezervacija));

        mockMvc.perform((get("/api/rezervacija")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldAddNewRezervacija() throws Exception {
        mockMvc.perform(post("/api/rezervacija")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(rezervacija)))
                .andExpect(status().isCreated());

        verify(rezervacijaService, times(1)).addNewReservation(any(Rezervacija.class));
    }

    @Test
    void shouldUpdateRezervacija() throws Exception {
        mockMvc.perform(put("/api/rezervacija/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(rezervacija)))
                .andExpect(status().isOk());

        verify(rezervacijaService, times(1)).updateReservation(eq(1), any(Rezervacija.class));
    }

    @Test
    void shouldDeleteRezervacija() throws Exception {
        mockMvc.perform(delete("/api/rezervacija/1"))
                .andExpect(status().isOk());
        verify(rezervacijaService, times(1)).deleteReservation(1);
    }
}

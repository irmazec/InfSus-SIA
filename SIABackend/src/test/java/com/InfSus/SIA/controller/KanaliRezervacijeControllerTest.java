package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.KanalRezervacije;
import com.InfSus.SIA.service.KanalRezervacijeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KanalRezervacijaController.class)
public class KanaliRezervacijeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KanalRezervacijeService kanalRezervacijeService;

    @Autowired
    private ObjectMapper objectMapper;

    private KanalRezervacije kanalRezervacije;

    @BeforeEach
    void setUp() {
        kanalRezervacije = new KanalRezervacije();
        kanalRezervacije.setNaziv("Reddit");
    }

    @Test
    void shouldReturnKanalRezervacijeById() throws Exception {
        when(kanalRezervacijeService.getReservationChannel(1)).thenReturn(kanalRezervacije);

        mockMvc.perform(get("/api/kanal-rezervacije/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("Reddit"));
    }

    @Test
    void shoudReturnAllKanaliRezervacije() throws Exception {
        when(kanalRezervacijeService.getAllReservationChannels()).thenReturn(List.of(kanalRezervacije));

        mockMvc.perform((get("/api/kanal-rezervacije")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldAddNewKanalRezervacije() throws Exception {
        mockMvc.perform(post("/api/kanal-rezervacije")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(kanalRezervacije)))
                .andExpect(status().isCreated());

        verify(kanalRezervacijeService, times(1)).addNewReservationChannel(any(KanalRezervacije.class));
    }

    @Test
    void shouldUpdateKanalRezervacije() throws Exception {
        mockMvc.perform(put("/api/kanal-rezervacije/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(kanalRezervacije)))
                .andExpect(status().isOk());

        verify(kanalRezervacijeService, times(1)).updateReservationChannel(eq(1), any(KanalRezervacije.class));
    }

    @Test
    void shouldDeleteKanalRezervacije() throws Exception {
        mockMvc.perform(delete("/api/kanal-rezervacije/1"))
                .andExpect(status().isOk());
        verify(kanalRezervacijeService, times(1)).deleteReservationChannel(1);

    }
}

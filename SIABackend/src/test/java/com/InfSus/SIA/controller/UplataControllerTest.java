package com.InfSus.SIA.controller;

import com.InfSus.SIA.model.Rezervacija;
import com.InfSus.SIA.model.StatusUplate;
import com.InfSus.SIA.model.Uplata;
import com.InfSus.SIA.service.UplataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UplataController.class)
public class UplataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UplataService uplataService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturnUplataById() throws Exception {
        when(uplataService.getPayment(1)).thenReturn(uplata);

        mockMvc.perform(get("/api/uplata/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iznos").value(456));
    }

    @Test
    void shoudReturnAllUplate() throws Exception {
        when(uplataService.getAllPayments()).thenReturn(List.of(uplata));

        mockMvc.perform((get("/api/uplata")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldAddNewUplata() throws Exception {
        mockMvc.perform(post("/api/uplata")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(uplata)))
                .andExpect(status().isCreated());

        verify(uplataService, times(1)).addNewPayment(any(Uplata.class));
    }

    @Test
    void shouldUpdateUplata() throws Exception {
        mockMvc.perform(put("/api/uplata/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(uplata)))
                .andExpect(status().isOk());

        verify(uplataService, times(1)).updatePayment(eq(1), any(Uplata.class));
    }

    @Test
    void shouldDeleteUplata() throws Exception {
        mockMvc.perform(delete("/api/uplata/1"))
                .andExpect(status().isOk());
        verify(uplataService, times(1)).deletePayment(1);
    }
}

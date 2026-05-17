package com.InfSus.SIA.integration;

import com.InfSus.SIA.model.*;
import com.InfSus.SIA.repository.KanalRezervacijeRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@Transactional
public class KanalRezervacijeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KanalRezervacijeRepository kanalRezervacijeRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    private KanalRezervacije kanalRezervacije;

    @BeforeEach
    void setUp(){
        kanalRezervacije = new KanalRezervacije();
        kanalRezervacije.setNaziv("Reddit");
        entityManager.persist(kanalRezervacije);
        entityManager.flush();
    }

    @Test
    void shoudReturnAllKanaliRezervacije() throws Exception {
        mockMvc.perform((get("/api/kanal-rezervacije")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldReturnKanalRezervacijeById() throws Exception {
        mockMvc.perform(get("/api/kanal-rezervacije/"+kanalRezervacije.getSifraKanala()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("Reddit"));
    }

    @Test
    void shouldAddNewKanalRezervacije() throws Exception {
        String payload = String.format("""
            {
                "naziv": "BnB"
            }
            """);
        mockMvc.perform(post("/api/kanal-rezervacije")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(payload))
                .andExpect(status().isCreated());

        assertThat(kanalRezervacijeRepository.findAll()).hasSize(2);
    }

    @Test
    void shouldUpdateKanalRezervacije() throws Exception {
        String payload = String.format("""
            {
                "naziv": "Cookie-Apartments"
            }
            """
        );
        mockMvc.perform(put("/api/kanal-rezervacije/"+kanalRezervacije.getSifraKanala())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(payload))
                .andExpect(status().isOk());
        KanalRezervacije update = kanalRezervacijeRepository.getReferenceById(kanalRezervacije.getSifraKanala());
        assertThat(update.getNaziv()).isEqualTo("Cookie-Apartments");
    }

    @Test
    void shouldDeleteKanalRezervacije() throws Exception {
        mockMvc.perform(delete("/api/kanal-rezervacije/"+kanalRezervacije.getSifraKanala()))
                .andExpect(status().isOk());
        assertThat(kanalRezervacijeRepository.findById(kanalRezervacije.getSifraKanala())).isEmpty();
    }
}

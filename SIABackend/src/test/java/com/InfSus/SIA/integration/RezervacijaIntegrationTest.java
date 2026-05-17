package com.InfSus.SIA.integration;

import com.InfSus.SIA.model.*;
import com.InfSus.SIA.repository.RezervacijaRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@Transactional
public class RezervacijaIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    private Rezervacija rezervacija;

    @BeforeEach
    void setUp(){
        rezervacija = createRezervacija();
        entityManager.persist(rezervacija);
        entityManager.flush();
    }

    @Test
    void shoudReturnAllRezervacije() throws Exception {
        mockMvc.perform((get("/api/rezervacija")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldReturnRezervacijaById() throws Exception {
        mockMvc.perform(get("/api/rezervacija/"+rezervacija.getIdRezervacija()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("brojOsoba").value(4));
    }

    @Test
    void shouldAddNewRezervacija() throws Exception {
        String payload = String.format("""
            {
                "datumOd": "2026-07-01",
                "datumDo": "2026-07-07",
                "ukupnaCijena": 500,
                "brojOsoba": 3,
                "datumRezervacije": "%s",
                "gost": { "idGost": %d },
                "apartman": { "idApartman": %d },
                "kanalRezervacije": { "sifraKanala": %d },
                "statusRezervacije": { "sifraStatusaRezervacije": %d }
            }
            """,
                LocalDateTime.now(),
                rezervacija.getGost().getIdGost(),
                rezervacija.getApartman().getIdApartman(),
                rezervacija.getKanalRezervacije().getSifraKanala(),
                rezervacija.getStatusRezervacije().getSifraStatusaRezervacije()
        );
        mockMvc.perform(post("/api/rezervacija")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(payload))
                .andExpect(status().isCreated());

        assertThat(rezervacijaRepository.findAll()).hasSize(2);
    }

    @Test
    void shouldUpdateRezervacija() throws Exception {
        String payload = String.format("""
            {
                "datumOd": "2026-07-01",
                "datumDo": "2026-07-07",
                "ukupnaCijena": 478,
                "brojOsoba": 2,
                "datumRezervacije": "%s",
                "gost": { "idGost": %d },
                "apartman": { "idApartman": %d },
                "kanalRezervacije": { "sifraKanala": %d },
                "statusRezervacije": { "sifraStatusaRezervacije": %d }
            }
            """,
                LocalDateTime.now(),
                rezervacija.getGost().getIdGost(),
                rezervacija.getApartman().getIdApartman(),
                rezervacija.getKanalRezervacije().getSifraKanala(),
                rezervacija.getStatusRezervacije().getSifraStatusaRezervacije()
        );
        mockMvc.perform(put("/api/rezervacija/"+rezervacija.getIdRezervacija())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(payload))
                .andExpect(status().isOk());
        Rezervacija update = rezervacijaRepository.getReferenceById(rezervacija.getIdRezervacija());
        assertThat(update.getBrojOsoba()).isEqualTo(2);
        assertThat(update.getUkupnaCijena()).isEqualTo(BigDecimal.valueOf(478));
    }

    @Test
    void shouldDeleteRezervacija() throws Exception {
        mockMvc.perform(delete("/api/rezervacija/"+rezervacija.getIdRezervacija()))
                .andExpect(status().isOk());
        assertThat(rezervacijaRepository.findById(rezervacija.getIdRezervacija())).isEmpty();
    }


    private Rezervacija createRezervacija(){
        Iznajmljivac iznajmljivac = new Iznajmljivac();
        iznajmljivac.setIme("Pero");
        iznajmljivac.setPrezime("Perić");
        iznajmljivac.setEmail("pero@test.com");
        iznajmljivac.setBrojTelefona("0911234567");
        entityManager.persist(iznajmljivac);

        TipApartmana tipApartmana = new TipApartmana();
        tipApartmana.setNaziv("Studio");
        entityManager.persist(tipApartmana);
        Gost gost = new Gost();
        gost.setIme("Ivan");
        gost.setPrezime("Horvat");
        gost.setEmail("ivanhorvat@gmail.com");
        gost.setBrojTelefona("0911234567");
        gost.setDrzavljanstvo("hrvatsko");
        gost.setDatumRodenja(LocalDate.of(1990, 1, 1));
        entityManager.persist(gost);

        Apartman apartman = new Apartman();
        apartman.setNaziv("Apartman Divni");
        apartman.setAdresa("Ulica 1");
        apartman.setIznajmljivac(iznajmljivac);
        apartman.setTipApartmana(tipApartmana);
        apartman.setKapacitet(4);
        apartman.setCijenanocenja(BigDecimal.valueOf(100));
        entityManager.persist(apartman);

        KanalRezervacije kanal = new KanalRezervacije();
        kanal.setNaziv("Online");
        entityManager.persist(kanal);

        StatusRezervacije status = new StatusRezervacije();
        status.setNaziv("Potvrđena");
        entityManager.persist(status);

        entityManager.flush();

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setGost(gost);
        rezervacija.setApartman(apartman);
        rezervacija.setKanalRezervacije(kanal);
        rezervacija.setStatusRezervacije(status);
        rezervacija.setUkupnaCijena(BigDecimal.valueOf(670.00));
        rezervacija.setBrojOsoba(4);
        rezervacija.setDatumOd(LocalDate.of(2026, 5, 9));
        rezervacija.setDatumDo(LocalDate.of(2026, 5, 16));
        rezervacija.setDatumRezervacije(LocalDateTime.now());

        return rezervacija;
    }
}

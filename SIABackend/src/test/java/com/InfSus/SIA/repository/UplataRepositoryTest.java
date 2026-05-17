package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class UplataRepositoryTest {
    @Autowired
    private UplataRepository uplataRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Rezervacija rezervacija;
    private StatusUplate statusUplate;

    @BeforeEach
    void setUp(){
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

        rezervacija = new Rezervacija();
        rezervacija.setGost(gost);
        rezervacija.setApartman(apartman);
        rezervacija.setKanalRezervacije(kanal);
        rezervacija.setStatusRezervacije(status);
        rezervacija.setUkupnaCijena(BigDecimal.valueOf(670.00));
        rezervacija.setBrojOsoba(4);
        rezervacija.setDatumOd(LocalDate.of(2026, 5, 9));
        rezervacija.setDatumDo(LocalDate.of(2026, 5, 16));
        rezervacija.setDatumRezervacije(LocalDateTime.now());
        entityManager.persist(rezervacija);

        statusUplate = new StatusUplate();
        statusUplate.setNaziv("Na rate");
        entityManager.persist(statusUplate);

        entityManager.flush();
    }

    @Test
    void shouldSaveAndFindUplata() {
        Uplata uplata = createUplata();
        uplataRepository.save(uplata);

        Optional<Uplata> found = uplataRepository.findById(uplata.getIdUplata());
        assertThat(found).isPresent();
        assertThat(found.get().getIdUplata()).isEqualTo(uplata.getIdUplata());
    }

    @Test
    void shouldReturnAllUplate(){
        Uplata uplata1 = createUplata();
        Uplata uplata2 = createUplata();

        uplataRepository.save(uplata1);
        uplataRepository.save(uplata2);

        List<Uplata> all = uplataRepository.findAll();

        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void deleteUplata(){
        Uplata uplata = createUplata();
        uplataRepository.save(uplata);

        uplataRepository.deleteById(uplata.getIdUplata());
        Optional<Uplata> found = uplataRepository.findById(uplata.getIdUplata());
        assertThat(found).isEmpty();
    }

    private Uplata createUplata(){
        Uplata uplata = new Uplata();
        uplata.setStatusUplate(statusUplate);
        uplata.setRezervacija(rezervacija);
        uplata.setNapomena("Sve uredno.");
        uplata.setDatumIzvrsenja(LocalDate.of(2026, 5, 22));
        uplata.setIznos(BigDecimal.valueOf(22.0));

        return uplata;
    }
}

package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.*;
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
public class RezervacijaRepositoryTest {

    @Autowired
    private RezervacijaRepository rezervacijaRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindRezervacija() {
        Rezervacija rezervacija = createRezervacija();
        rezervacijaRepository.save(rezervacija);

        Optional<Rezervacija> found = rezervacijaRepository.findById(rezervacija.getIdRezervacija());

        assertThat(found).isPresent();
        assertThat(found.get().getIdRezervacija()).isEqualTo(rezervacija.getIdRezervacija());
    }

    @Test
    void shouldReturnAllRezervacija(){
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

        Rezervacija r1 = new Rezervacija();
        r1.setApartman(apartman);
        r1.setBrojOsoba(5);
        r1.setDatumOd(LocalDate.of(2026, 5, 9));
        r1.setDatumDo(LocalDate.of(2026, 5, 16));
        r1.setDatumRezervacije(LocalDateTime.now());
        r1.setGost(gost);
        r1.setKanalRezervacije(kanal);
        r1.setStatusRezervacije(status);
        r1.setUkupnaCijena(BigDecimal.valueOf(500));
        Rezervacija r2 = new Rezervacija();
        r2.setApartman(apartman);
        r2.setBrojOsoba(4);
        r2.setDatumOd(LocalDate.of(2026, 10, 9));
        r2.setDatumDo(LocalDate.of(2026, 10, 16));
        r2.setDatumRezervacije(LocalDateTime.now());
        r2.setGost(gost);
        r2.setKanalRezervacije(kanal);
        r2.setStatusRezervacije(status);
        r2.setUkupnaCijena(BigDecimal.valueOf(500));

        rezervacijaRepository.save(r1);
        rezervacijaRepository.save(r2);

        List<Rezervacija> all = rezervacijaRepository.findAll();

        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void deleteRezervacija(){
        Rezervacija rezervacija = createRezervacija();
        rezervacijaRepository.save(rezervacija);

        rezervacijaRepository.deleteById(rezervacija.getIdRezervacija());
        Optional<Rezervacija> found = rezervacijaRepository.findById(rezervacija.getIdRezervacija());
        assertThat(found).isEmpty();
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

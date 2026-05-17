package com.InfSus.SIA.repository;

import com.InfSus.SIA.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class KanaliRezervacijeRepositoryTest {
    @Autowired
    private KanalRezervacijeRepository kanalRezervacijeRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindKanalRezervacije(){
        KanalRezervacije kanalRezervacije = new KanalRezervacije();
        kanalRezervacije.setNaziv("Reddit");

        kanalRezervacijeRepository.save(kanalRezervacije);

        Optional<KanalRezervacije> found = kanalRezervacijeRepository.findById(kanalRezervacije.getSifraKanala());

        assertThat(found).isPresent();
        assertThat(found.get().getSifraKanala()).isEqualTo(kanalRezervacije.getSifraKanala());
    }

    @Test
    void shouldReturnaAllKanaliRezervacije(){
        KanalRezervacije kanalRezervacije1 = new KanalRezervacije();
        kanalRezervacije1.setNaziv("Reddit");
        KanalRezervacije kanalRezervacije2 = new KanalRezervacije();
        kanalRezervacije2.setNaziv("BnB");

        kanalRezervacijeRepository.save(kanalRezervacije1);
        kanalRezervacijeRepository.save(kanalRezervacije2);

        List<KanalRezervacije> all = kanalRezervacijeRepository.findAll();

        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldDeleteKanalRezervacije(){
        KanalRezervacije kanalRezervacije = new KanalRezervacije();
        kanalRezervacije.setNaziv("Reddit");

        kanalRezervacijeRepository.save(kanalRezervacije);
        kanalRezervacijeRepository.deleteById(kanalRezervacije.getSifraKanala());

        Optional<KanalRezervacije> found = kanalRezervacijeRepository.findById(kanalRezervacije.getSifraKanala());
        assertThat(found).isEmpty();
    }
}

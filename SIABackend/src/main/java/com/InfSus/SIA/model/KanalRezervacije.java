package com.InfSus.SIA.model;

import javax.persistence.*;

@Entity
@Table(name = "KANAL_REZERVACIJE")
public class KanalRezervacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sifra_kanala")
    private Integer sifraKanala;

    @Column(name = "Naziv", nullable = false, length = 15, unique = true)
    private String naziv;

    public Integer getSifraKanala() {
        return sifraKanala;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

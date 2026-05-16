package com.InfSus.SIA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TIP_APARTMANA")
public class TipApartmana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sifra_tipa_apartmana")
    private Integer sifraTimaApartmana;

    @Column(name = "Naziv", nullable = false, length = 15, unique = true)
    private String naziv;

    public Integer getSifraTimaApartmana() {
        return sifraTimaApartmana;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

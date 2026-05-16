package com.InfSus.SIA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "STATUS_UPLATE")
public class StatusUplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sifra_statusa_uplate")
    private Integer sifraStatusaUplate;

    @Column(name = "Naziv", nullable = false, unique = true)
    private String naziv;

    public Integer getSifraStatusaUplate() {
        return sifraStatusaUplate;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

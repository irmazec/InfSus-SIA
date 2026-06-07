package com.InfSus.SIA.model;

import javax.persistence.*;

@Entity
@Table(name = "STATUS_REZERVACIJE")
public class StatusRezervacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sifra_statusa_rezervacije")
    private Integer sifraStatusaRezervacije;

    @Column(name = "Naziv", nullable = false, length = 15, unique = true)
    private String naziv;

    public Integer getSifraStatusaRezervacije() {
        return sifraStatusaRezervacije;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

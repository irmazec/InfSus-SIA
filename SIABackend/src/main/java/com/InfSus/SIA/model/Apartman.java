package com.InfSus.SIA.model;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "APARTMAN")
public class Apartman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_apartman")
    private Integer idApartman;

    @Column(name = "Naziv", nullable = false, length = 50)
    private String naziv;

    @Column(name = "Adresa", nullable = false, length = 80)
    private String adresa;

    @Column(name = "Kapacitet", nullable = false)
    private Integer kapacitet;

    @Column(name = "Cijena_nocenja", nullable = false, precision = 10, scale = 2)
    private BigDecimal cijenanocenja;

    @ManyToOne
    @JoinColumn(name = "Sifra_tipa_apartmana", nullable = false)
    private TipApartmana tipApartmana;

    @ManyToOne
    @JoinColumn(name = "Id_iznajmljivac", nullable = false)
    private Iznajmljivac iznajmljivac;

    public Integer getIdApartman() {
        return idApartman;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public BigDecimal getCijenanocenja() {
        return cijenanocenja;
    }

    public void setCijenanocenja(BigDecimal cijenanocenja) {
        this.cijenanocenja = cijenanocenja;
    }

    public TipApartmana getTipApartmana() {
        return tipApartmana;
    }

    public void setTipApartmana(TipApartmana tipApartmana) {
        this.tipApartmana = tipApartmana;
    }

    public Iznajmljivac getIznajmljivac() {
        return iznajmljivac;
    }

    public void setIznajmljivac(Iznajmljivac iznajmljivac) {
        this.iznajmljivac = iznajmljivac;
    }
}

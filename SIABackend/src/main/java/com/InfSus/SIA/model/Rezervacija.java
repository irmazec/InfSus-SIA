package com.InfSus.SIA.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "REZERVACIJA")
public class Rezervacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_rezervacija")
    private Integer idRezervacija;

    @Column(name = "Datum_od", nullable = false)
    private LocalDate datumOd;

    @Column(name = "Datum_do", nullable = false)
    private LocalDate datumDo;

    @Column(name = "Ukupna_cijena", nullable = false, precision = 10, scale = 2)
    private BigDecimal ukupnaCijena;

    @Column(name = "Broj_osoba", nullable = false)
    private Integer brojOsoba;

    @Column(name = "Datum_rezervacije", nullable = false)
    private LocalDateTime datumRezervacije;

    @ManyToOne
    @JoinColumn(name = "Id_apartman", nullable = false)
    private Apartman apartman;

    @ManyToOne
    @JoinColumn(name = "Sifra_statusa_rezervacije", nullable = false)
    private StatusRezervacije statusRezervacije;

    @ManyToOne
    @JoinColumn(name = "Sifra_kanala", nullable = false)
    private KanalRezervacije kanalRezervacije;

    @ManyToOne
    @JoinColumn(name = "Id_gost", nullable = false)
    private Gost gost;

    public Integer getIdRezervacija() {
        return idRezervacija;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }

    public BigDecimal getUkupnaCijena() {
        return ukupnaCijena;
    }

    public void setUkupnaCijena(BigDecimal ukupnaCijena) {
        this.ukupnaCijena = ukupnaCijena;
    }

    public Integer getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(Integer brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public LocalDateTime getDatumRezervacije() {
        return datumRezervacije;
    }

    public void setDatumRezervacije(LocalDateTime datumRezervacije){
        this.datumRezervacije = datumRezervacije;
    }

    public Apartman getApartman() {
        return apartman;
    }

    public void setApartman(Apartman apartman) {
        this.apartman = apartman;
    }

    public StatusRezervacije getStatusRezervacije() {
        return statusRezervacije;
    }

    public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }

    public KanalRezervacije getKanalRezervacije() {
        return kanalRezervacije;
    }

    public void setKanalRezervacije(KanalRezervacije kanalRezervacije) {
        this.kanalRezervacije = kanalRezervacije;
    }

    public Gost getGost() {
        return gost;
    }

    public void setGost(Gost gost) {
        this.gost = gost;
    }
}

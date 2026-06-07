package com.InfSus.SIA.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "UPLATA")
public class Uplata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_uplata")
    private Integer idUplata;

    @Column(name = "Iznos", nullable = false, precision = 10, scale = 2)
    private BigDecimal iznos;

    @Column(name = "Datum_izvrsenja")
    private LocalDate datumIzvrsenja;

    @Column(name = "Napomena", length = 100)
    private String napomena;

    @ManyToOne
    @JoinColumn(name = "Sifra_statusa_uplate", nullable = false)
    private StatusUplate statusUplate;

    @ManyToOne
    @JoinColumn(name = "Id_rezervacija", nullable = false)
    private Rezervacija rezervacija;

    public Integer getIdUplata() {
        return idUplata;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public LocalDate getDatumIzvrsenja() {
        return datumIzvrsenja;
    }

    public void setDatumIzvrsenja(LocalDate datumIzvrsenja) {
        this.datumIzvrsenja = datumIzvrsenja;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public StatusUplate getStatusUplate() {
        return statusUplate;
    }

    public void setStatusUplate(StatusUplate statusUplate) {
        this.statusUplate = statusUplate;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }
}

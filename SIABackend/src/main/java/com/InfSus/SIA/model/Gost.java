package com.InfSus.SIA.model;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "GOST")
public class Gost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_gost")
    private Integer idGost;

    @Column(name = "Ime", nullable = false, length = 50)
    private String ime;

    @Column(name = "Prezime", nullable = false, length = 50)
    private String prezime;

    @Column(name = "Email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "Broj_telefona", nullable = false, length = 20)
    private String brojTelefona;

    @Column(name = "Drzavljanstvo", nullable = false, length = 50)
    private String drzavljanstvo;

    @Column(name = "Datum_rodenja", nullable = false)
    private LocalDate datumRodenja;

    public Integer getIdGost() {
        return idGost;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getDrzavljanstvo() {
        return drzavljanstvo;
    }

    public void setDrzavljanstvo(String drzavljanstvo) {
        this.drzavljanstvo = drzavljanstvo;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

}

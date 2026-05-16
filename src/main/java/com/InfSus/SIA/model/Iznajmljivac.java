package com.InfSus.SIA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "IZNAJMLJIVAC")
public class Iznajmljivac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_iznajmljivac")
    private Integer idIznajmljivac;

    @Column(name = "Ime", nullable = false, length = 50)
    private String ime;

    @Column(name = "Prezime", nullable = false, length = 50)
    private String prezime;

    @Column(name = "Email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "Broj_telefona", nullable = false, length = 20)
    private String brojTelefona;

    public Integer getIdIznajmljivac() {
        return idIznajmljivac;
    }

    public void setIdIznajmljivac(Integer idIznajmljivac) {
        this.idIznajmljivac = idIznajmljivac;
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
}

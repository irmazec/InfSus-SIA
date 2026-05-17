CREATE TABLE IZNAJMLJIVAC
(
  Ime VARCHAR(50) NOT NULL,
  Prezime VARCHAR(50) NOT NULL,
  Email VARCHAR(100) NOT NULL,
  Broj_telefona VARCHAR(20) NOT NULL,
  Id_iznajmljivac INT GENERATED ALWAYS AS IDENTITY,
  PRIMARY KEY (Id_iznajmljivac),
  UNIQUE (Email)
);

CREATE TABLE TIP_APARTMANA
(
  Sifra_tipa_apartmana INT GENERATED ALWAYS AS IDENTITY,
  Naziv VARCHAR(15) NOT NULL,
  PRIMARY KEY (Sifra_tipa_apartmana),
  UNIQUE (Naziv)
);

CREATE TABLE APARTMAN
(
  Naziv VARCHAR(50) NOT NULL,
  Adresa VARCHAR(80) NOT NULL,
  Kapacitet INT NOT NULL,
  Cijena_nocenja NUMERIC(10, 2) NOT NULL,
  Id_apartman INT GENERATED ALWAYS AS IDENTITY,
  Sifra_tipa_apartmana INT NOT NULL,
  Id_iznajmljivac INT NOT NULL,
  PRIMARY KEY (Id_apartman),
  FOREIGN KEY (Sifra_tipa_apartmana) REFERENCES TIP_APARTMANA(Sifra_tipa_apartmana),
  FOREIGN KEY (Id_iznajmljivac) REFERENCES IZNAJMLJIVAC(Id_iznajmljivac),
  CHECK (Kapacitet > 0 AND Cijena_nocenja > 0)
);

CREATE TABLE STATUS_REZERVACIJE
(
  Sifra_statusa_rezervacije INT GENERATED ALWAYS AS IDENTITY,
  Naziv VARCHAR(15) NOT NULL,
  PRIMARY KEY (Sifra_statusa_rezervacije),
  UNIQUE (Naziv)
);

CREATE TABLE KANAL_REZERVACIJE
(
  Sifra_kanala INT GENERATED ALWAYS AS IDENTITY,
  Naziv VARCHAR(15) NOT NULL,
  PRIMARY KEY (Sifra_kanala),
  UNIQUE (Naziv)
);

CREATE TABLE GOST
(
  Id_gost INT GENERATED ALWAYS AS IDENTITY,
  Ime VARCHAR(50) NOT NULL,
  Prezime VARCHAR(50) NOT NULL,
  Email VARCHAR(100) NOT NULL,
  Broj_telefona VARCHAR(20) NOT NULL,
  Drzavljanstvo VARCHAR(50) NOT NULL,
  Datum_rodenja DATE NOT NULL,
  PRIMARY KEY (Id_gost),
  UNIQUE (Email)
);

CREATE TABLE REZERVACIJA
(
  Id_rezervacija INT GENERATED ALWAYS AS IDENTITY,
  Datum_od DATE NOT NULL,
  Datum_do DATE NOT NULL,
  Ukupna_cijena NUMERIC(10, 2) NOT NULL,
  Broj_osoba INT NOT NULL,
  Datum_rezervacije TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  Id_apartman INT NOT NULL,
  Sifra_statusa_rezervacije INT NOT NULL,
  Sifra_kanala INT NOT NULL,
  Id_gost INT NOT NULL,
  PRIMARY KEY (Id_rezervacija),
  FOREIGN KEY (Id_apartman) REFERENCES APARTMAN(Id_apartman),
  FOREIGN KEY (Sifra_statusa_rezervacije) REFERENCES STATUS_REZERVACIJE(Sifra_statusa_rezervacije),
  FOREIGN KEY (Sifra_kanala) REFERENCES KANAL_REZERVACIJE(Sifra_kanala),
  FOREIGN KEY (Id_gost) REFERENCES GOST(Id_gost),
  CHECK (Datum_do > Datum_od AND Broj_osoba > 0)
);

CREATE TABLE RECENZIJA
(
  Ocjena INT NOT NULL,
  Komentar VARCHAR(500) NULL,
  Datum_unosa TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  Id_recenzija INT GENERATED ALWAYS AS IDENTITY,
  Id_rezervacija INT NOT NULL,
  PRIMARY KEY (Id_recenzija),
  FOREIGN KEY (Id_rezervacija) REFERENCES REZERVACIJA(Id_rezervacija),
  UNIQUE (Id_rezervacija),
  CHECK (Ocjena BETWEEN 1 AND 5)
);

CREATE TABLE STATUS_UPLATE
(
  Sifra_statusa_uplate INT GENERATED ALWAYS AS IDENTITY,
  Naziv VARCHAR(15) NOT NULL,
  PRIMARY KEY (Sifra_statusa_uplate),
  UNIQUE (Naziv)
);

CREATE TABLE UPLATA
(
  Iznos NUMERIC(10, 2) NOT NULL,
  Datum_izvrsenja DATE NULL,
  Napomena VARCHAR(100) NULL,
  Id_uplata INT GENERATED ALWAYS AS IDENTITY,
  Sifra_statusa_uplate INT NOT NULL,
  Id_rezervacija INT NOT NULL,
  PRIMARY KEY (Id_uplata),
  FOREIGN KEY (Sifra_statusa_uplate) REFERENCES STATUS_UPLATE(Sifra_statusa_uplate),
  FOREIGN KEY (Id_rezervacija) REFERENCES REZERVACIJA(Id_rezervacija),
  CHECK (Iznos > 0)
);
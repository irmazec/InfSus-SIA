INSERT INTO TIP_APARTMANA (Naziv) VALUES
  ('Studio'),
  ('Vila'),
  ('Apartman');

INSERT INTO STATUS_REZERVACIJE (Naziv) VALUES
  ('Na čekanju'),
  ('Odobreno'),
  ('Odbijeno');

INSERT INTO STATUS_UPLATE (Naziv) VALUES
  ('Plaćeno'),
  ('Neplaćeno');

INSERT INTO KANAL_REZERVACIJE (Naziv) VALUES
  ('Airbnb'),
  ('Booking'),
  ('Facebook'),
  ('Instagram'),
  ('Direktno');

INSERT INTO IZNAJMLJIVAC (Ime, Prezime, Email, Broj_telefona) VALUES
  ('Jadran', 'Jukić', 'jadran.jukic@gmail.com', '+385912345678');

INSERT INTO APARTMAN (Naziv, Adresa, Kapacitet, Cijena_nocenja, Sifra_tipa_apartmana, Id_iznajmljivac) VALUES
  ('Studio Lavanda', 'Ulica nepoznate junakinje 100', 2, 120.00, 1, 1),
  ('Apartman Bura', 'Ulica nepoznate junakinje 70', 4, 180.00, 3, 1),
  ('Vila Jadran', 'Ulica nepoznate junakinje 1', 8, 420.00, 2, 1),
  ('Vila Sunce', 'Ulica nepoznate junakinje 42', 6, 350.00, 2, 1),
  ('Vila Maslina', 'Ulica nepoznate junakinje 42', 6, 350.00, 2, 1);

INSERT INTO GOST (Ime, Prezime, Email, Broj_telefona, Drzavljanstvo, Datum_rodenja) VALUES
  ('Marko',    'Horvat',   'marko.horvat@gmail.com',      '+385981234567', 'Hrvatska',  '1985-03-14'),
  ('Anna',     'Müller',   'anna.mueller@web.de',         '+4915123456789','Njemačka',  '1990-07-22'),
  ('Luca',     'Rossi',    'luca.rossi@libero.it',        '+393312345678', 'Italija',   '1978-11-05'),
  ('Sophie',   'Martin',   'sophie.martin@orange.fr',     '+33612345678',  'Francuska', '1995-04-30'),
  ('Ivan',     'Petrović', 'ivan.petrovic@yahoo.com',     '+385915556677', 'Hrvatska',  '1982-09-18');

INSERT INTO REZERVACIJA (Datum_od, Datum_do, Ukupna_cijena, Broj_osoba, Datum_rezervacije, Id_apartman, Sifra_statusa_rezervacije, Sifra_kanala, Id_gost) VALUES
  ('2026-06-14', '2026-06-21', 840.00,  2, '2026-04-10 09:14:00', 1, 2, 2, 2),
  ('2026-07-01', '2026-07-06', 900.00,  3, '2026-05-03 09:14:00', 2, 2, 5, 4),
  ('2026-07-12', '2026-07-19', 2940.00, 6, '2026-04-28 09:14:00', 3, 2, 4, 3),
  ('2026-08-02', '2026-08-09', 2450.00, 4, '2026-03-15 09:14:00', 4, 2, 3, 1),
  ('2026-09-06', '2026-09-13', 2450.00, 5, '2026-04-11 09:14:00', 5, 1, 2, 5),
  ('2026-06-18', '2026-06-23',  600.00, 2, '2026-04-12 09:14:00', 1, 3, 1, 1);

INSERT INTO UPLATA (Iznos, Datum_izvrsenja, Napomena, Sifra_statusa_uplate, Id_rezervacija) VALUES
  (840.00, '2026-04-11', 'Plaćeno karticom putem Bookinga', 1, 1),
  (300.00, '2026-05-04', 'Predujam 1/3',     1, 2),
  (600.00, '2026-06-25', 'Ostatak iznosa',   1, 2),
  (1000.00, '2026-04-29', 'Predujam',         1, 3),
  (1940.00, NULL,          NULL,              2, 3),
  (2450.00, '2026-08-02', 'Gotovina pri dolasku', 1, 4);

INSERT INTO RECENZIJA (Ocjena, Komentar, Datum_unosa, Id_rezervacija) VALUES
  (5, 'Predivan studio, savršena lokacija. Sve je bilo besprijekorno čisto, domaćin vrlo susretljiv. Preporučujem svima!', '2026-06-22', 1),
  (4, 'Ugodan apartman, lijepo uređen i dobro opremljen. Jedina napomena je buka s ulice, ali sve ostalo odlično.', '2026-07-07', 2);
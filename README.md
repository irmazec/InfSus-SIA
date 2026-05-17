# InfSus-SIA
Službeni repozitorij za programsko rješenje Sustava za iznajmljivanje apartmana koje odgovara specifikaciji zahtjeva.

### Sadržaj repozitorija
- dokumentacija arhitekture (dijagram komponenti)
- zaslon/forma zaglavlja s detaljima (master-detail)
- zaslon/forma šifrarnika s traženim funkcionalnostima
- definicije odvojenih jediničnih testova prema slojevima
- integracijski testovi


Pri testiranju aplikacije lokalno frontend se pokreće iz direktorija SIAFrontend/frontend s naredbom **npm run dev**. Time se otvara adresa na http://localhost:5173.
Dostupne adrese su: http://localhost:5173/rezervacije za master-detail i http://localhost:5173/kanali-rezervacije.
Backend se pokreće iz direktorija SIABackend s naredbom **./mvnw spring-boot:run** čime se otvara adresa http://localhost:8080. Prije pokretanja frontenda i backenda potrebno je pokrenuti lokalno Postgres server na portu 5432.

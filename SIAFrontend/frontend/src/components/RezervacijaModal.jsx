import { useState, useEffect } from 'react'
import GostModal from './GostModal'

function RezervacijaModal({ rezervacija, onSave, onClose }) {
    const [form, setForm] = useState({
        datumOd: '',
        datumDo: '',
        ukupnaCijena: '',
        brojOsoba: '',
        idGost: '',
        idApartman: '',
        sifraKanala: '',
        sifraStatusa: '',
    })

    const [gosti, setGosti] = useState([])
    const [apartmani, setApartmani] = useState([])
    const [kanali, setKanali] = useState([])
    const [statusi, setStatusi] = useState([])
    const [loading, setLoading] = useState(true)
    const [gostModalOpen, setGostModalOpen] = useState(false)

    const fetchDropdowns = async () => {
        const [g, a, k, s] = await Promise.all([
          fetch('http://localhost:8080/api/gost').then(r => r.json()),
          fetch('http://localhost:8080/api/apartman').then(r => r.json()),
          fetch('http://localhost:8080/api/kanal-rezervacije').then(r => r.json()),
          fetch('http://localhost:8080/api/status-rezervacije').then(r => r.json()),
        ])
        setGosti(g)
        setApartmani(a)
        setKanali(k)
        setStatusi(s)
        setLoading(false)
    }

    useEffect(() => {
        fetchDropdowns()
    }, [])

    useEffect(() => {
        if (rezervacija) {
            setForm({
                datumOd: rezervacija.datumOd,
                datumDo: rezervacija.datumDo,
                ukupnaCijena: rezervacija.ukupnaCijena,
                brojOsoba: rezervacija.brojOsoba,
                idGost: rezervacija.gost.idGost,
                idApartman: rezervacija.apartman.idApartman,
                sifraKanala: rezervacija.kanalRezervacije.sifraKanala,
                sifraStatusa: rezervacija.statusRezervacije.sifraStatusaRezervacije,
            })
        }
    }, [rezervacija])

    const handleChange = (e) => {
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
    }

    const handleGostCreated = (noviGost) => {
        setGosti(prev => [...prev, noviGost])
        setForm(prev => ({ ...prev, idGost: noviGost.idGost }))
        setGostModalOpen(false)
    }

    const handleSubmit = () => {
        if (!form.datumOd || !form.datumDo || !form.ukupnaCijena || !form.brojOsoba ||
            !form.idGost || !form.idApartman || !form.sifraKanala || !form.sifraStatusa) {
            alert('Sva polja su obavezna.')
            return
        }
        onSave(form)
    }

    if (loading) return (
        <div className="modal-overlay">
            <div className="modal"><p>Učitavanje...</p></div>
        </div>
    )
    var today = new Date().toISOString().split('T')[0];
    return (
        <>
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={e => e.stopPropagation()}>
                <h3>{rezervacija ? 'Uredi rezervaciju' : 'Nova rezervacija'}</h3>

                <label>Datum od</label>
                <input name="datumOd" type="date" min= {today} value={form.datumOd} onChange={handleChange} />

                <label>Datum do</label>
                <input name="datumDo" type="date" min =  {today} value={form.datumDo} onChange={handleChange} />

                <label>Ukupna cijena (€)</label>
                <input name="ukupnaCijena" type="number" min = "0" value={form.ukupnaCijena} onChange={handleChange} />

                <label>Broj osoba</label>
                <input name="brojOsoba" type="number" min = "0" value={form.brojOsoba} onChange={handleChange} />

                <label>Apartman</label>
                <select name="idApartman" value={form.idApartman} onChange={handleChange}>
                <option value="">-- Odaberi apartman --</option>
                    {apartmani.map(a => (
                        <option key={a.idApartman} value={a.idApartman}>
                            {a.naziv} — {a.adresa}
                        </option>
                    ))}
                </select>

                <label>Gost</label>
                <div className="inputRow">
                    <select name="idGost" value={form.idGost} onChange={handleChange}>
                    <option value="">-- Odaberi gosta --</option>
                        {gosti.map(g => (
                            <option key={g.idGost} value={g.idGost}>
                                {g.ime} {g.prezime}
                            </option>
                        ))}
                    </select>
                    <button className="addNew" type="button" onClick={() => setGostModalOpen(true)}>Dodaj novog gosta</button>
                </div>

                <label>Kanal rezervacije</label>
                <select name="sifraKanala" value={form.sifraKanala} onChange={handleChange}>
                <option value="">-- Odaberi kanal --</option>
                    {kanali.map(k => (
                        <option key={k.sifraKanala} value={k.sifraKanala}>
                            {k.naziv}
                        </option>
                    ))}
                </select>

                <label>Status rezervacije</label>
                <select name="sifraStatusa" value={form.sifraStatusa} onChange={handleChange}>
                <option value="">-- Odaberi status --</option>
                {statusi.map(s => (
                    <option key={s.sifraStatusaRezervacije} value={s.sifraStatusaRezervacije}>
                        {s.naziv}
                    </option>
                ))}
                </select>

                <div className="modal-actions">
                    <button onClick={onClose}>Odustani</button>
                    <button onClick={handleSubmit}>Spremi</button>
                    </div>
                </div>
            </div>

            {gostModalOpen && (
            <GostModal
            onSave={handleGostCreated}
            onClose={() => setGostModalOpen(false)}
            />
            )}
        </>
    )
}

export default RezervacijaModal
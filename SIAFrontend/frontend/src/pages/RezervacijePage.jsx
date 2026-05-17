import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import RezervacijaModal from '../components/RezervacijaModal'
import SearchBar from '../components/SearchBar'
import '../App.css'

function RezervacijePage() {
    const [rezervacije, setRezervacije] = useState([])
    const [loading, setLoading] = useState(true)
    const [modalOpen, setModalOpen] = useState(false)
    const [selectedRezervacija, setSelectedRezervacija] = useState(null)
    const [search, setSearch] = useState('')
    const navigate = useNavigate()

    const fetchRezervacije = async () => {
        const res = await fetch('http://localhost:8080/api/rezervacija')
        const data = await res.json()
        setRezervacije(data)
        setLoading(false)
    }

    useEffect(() => {
        fetchRezervacije()
    }, [])

    const handleAdd = () => {
        setSelectedRezervacija(null)
        setModalOpen(true)
    }

    const handleEdit = (rezervacija) => {
        setSelectedRezervacija(rezervacija)
        setModalOpen(true)
    }

    const handleDelete = async (id) => {
        if (!confirm('Obrisati rezervaciju?')) {
            return
        }
        await fetch(`http://localhost:8080/api/rezervacija/${id}`, { method: 'DELETE' })
        setRezervacije(prev => prev.filter(r => r.idRezervacija !== id))
    }

    const filtered = rezervacije.filter(r =>
        `${r.apartman.naziv}`.toLowerCase().includes(search.toLowerCase())
        )

    const handleSave = async (formData) => {
        const payload = {
          datumOd: formData.datumOd,
          datumDo: formData.datumDo,
          ukupnaCijena: formData.ukupnaCijena,
          brojOsoba: formData.brojOsoba,
          datumRezervacije: new Date().toISOString(),
          gost: { idGost: parseInt(formData.idGost) },
          apartman: { idApartman: parseInt(formData.idApartman) },
          kanalRezervacije: { sifraKanala: formData.sifraKanala },
          statusRezervacije: { sifraStatusaRezervacije: formData.sifraStatusa },
        }

        if (selectedRezervacija) {
            await fetch(`http://localhost:8080/api/rezervacija/${selectedRezervacija.idRezervacija}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
        } else {
            await fetch('http://localhost:8080/api/rezervacija', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
        }

        await fetchRezervacije()
            setModalOpen(false)
        }

    if (loading) return <p>Učitavanje...</p>

    return (
    <div>
        <h2>Rezervacije</h2>
            <div className="reservationTab">
                <SearchBar value={search} onChange={setSearch} placeholder="Pretraži po nazivu apartmana..."/>
                <button onClick={handleAdd}>Dodaj rezervaciju</button>
            </div>

        <table>
            <thead>
                    <tr>
                    <th>Gost</th>
                    <th>Apartman</th>
                    <th>Datum od</th>
                    <th>Datum do</th>
                    <th>Osobe</th>
                    <th>Cijena</th>
                    <th>Kanal</th>
                    <th>Status</th>
                    <th>Akcije</th>
                </tr>
            </thead>
            <tbody>
                {filtered.map(r => (
                  <tr key={r.idRezervacija}>
                      <td>{r.gost.ime} {r.gost.prezime}</td>
                      <td>{r.apartman.naziv}</td>
                      <td>{r.datumOd}</td>
                      <td>{r.datumDo}</td>
                      <td>{r.brojOsoba}</td>
                      <td>{r.ukupnaCijena} €</td>
                      <td>{r.kanalRezervacije.naziv}</td>
                      <td>{r.statusRezervacije.naziv}</td>
                      <td>
                          <button onClick={() => navigate(`/rezervacije/${r.idRezervacija}`)}>Detalji</button>
                          <button className="edit" onClick={() => handleEdit(r)}>Uredi</button>
                          <button className="delete" onClick={() => handleDelete(r.idRezervacija)}>Obriši</button>
                      </td>
                  </tr>
                ))}
            </tbody>
        </table>
        {modalOpen && (
            <RezervacijaModal
                rezervacija={selectedRezervacija}
                onSave={handleSave}
                onClose={() => setModalOpen(false)}
            />
        )}
    </div>
    )
}

export default RezervacijePage
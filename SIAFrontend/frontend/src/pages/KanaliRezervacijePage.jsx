import { useState, useEffect } from 'react'
import {useNavigate } from 'react-router-dom'
import KanalRezervacijeModal from '../components/KanalRezervacijeModal'
import SearchBar from '../components/SearchBar'

function KanaliRezervacijePage() {
    const [kanaliRezervacije, setKanaliRezervacije] = useState([])
    const [loading, setLoading] = useState(true)
    const [modalOpen, setModalOpen] = useState(false)
    const [search, setSearch] = useState('')
    const [selectedKanalRezervacije, setSelectedKanalRezervacije] = useState(null)
    const navigate = useNavigate()

    const fetchKanaliRezervacije = async () => {
    const res = await fetch('http://localhost:8080/api/kanal-rezervacije')
    const data = await res.json()
    setKanaliRezervacije(data)
    setLoading(false)
    }

    useEffect(() => {
        fetchKanaliRezervacije()
    }, [])

    const handleAdd = () => {
        setSelectedKanalRezervacije(null)
        setModalOpen(true)
    }

    const handleEdit = (kanalRezervacije) => {
        setSelectedKanalRezervacije(kanalRezervacije)
        setModalOpen(true)
    }

    const handleDelete = async (id) => {
    if (!confirm('Obrisati kanal rezervacije?')) {
        return
    }
    await fetch(`http://localhost:8080/api/kanal-rezervacije/${id}`, { method: 'DELETE' })
    setKanaliRezervacije(prev => prev.filter(r => r.sifraKanala !== id))
    }

    const handleSave = async (formData) => {
        if (selectedKanalRezervacije) {
            await fetch(`http://localhost:8080/api/kanal-rezervacije/${selectedKanalRezervacije.sifraKanala}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
          })
        } else {
            await fetch('http://localhost:8080/api/kanal-rezervacije', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
          })
    }

        await fetchKanaliRezervacije()
        setModalOpen(false)
    }

    const filtered = kanaliRezervacije.filter(r =>
    `${r.naziv}`.toLowerCase().includes(search.toLowerCase())
    )

    if (loading) return <p>Učitavanje...</p>

    return (
    <div>
        <h2>Kanali rezervacje</h2>
        <SearchBar value={search} onChange={setSearch} placeholder="Pretraži po nazivu kanala rezervacije..."/>
        <button onClick={handleAdd}>+ Novi kanal rezervacije</button>

        <table>
            <thead>
                <tr>
                    <th>Šifra kanala rezervacije</th>
                    <th>Naziv</th>
                </tr>
            </thead>
            <tbody>
                {filtered.map(r => (
                    <tr key={r.sifraKanala}>
                      <td>{r.sifraKanala}</td>
                      <td>{r.naziv}</td>
                      <td>
                        <button onClick={() => handleEdit(r)}>Uredi</button>
                        <button onClick={() => handleDelete(r.sifraKanala)}>Obriši</button>
                      </td>
                    </tr>
                  ))}
            </tbody>
        </table>

        {modalOpen && (
            <KanalRezervacijeModal
              kanalRezervacije={selectedKanalRezervacije}
              onSave={handleSave}
              onClose={() => setModalOpen(false)}
            />
            )}
            </div>
        )
    }
export default KanaliRezervacijePage
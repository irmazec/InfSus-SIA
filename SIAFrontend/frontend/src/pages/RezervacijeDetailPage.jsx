import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import UplataModal from '../components/UplataModal'

function RezervacijaDetailPage() {
    const { id } = useParams()
    const navigate = useNavigate()

    const [rezervacija, setRezervacija] = useState(null)
    const [uplate, setUplate] = useState([])
    const [loading, setLoading] = useState(true)
    const [modalOpen, setModalOpen] = useState(false)
    const [selectedUplata, setSelectedUplata] = useState(null)

    const fetchRezervacija = async () => {
        const res = await fetch(`http://localhost:8080/api/rezervacija/${id}`)
        const data = await res.json()
        setRezervacija(data)
    }

    const fetchUplate = async () => {
        const res = await fetch(`http://localhost:8080/api/uplata/rezervacija/${id}`)
        const data = await res.json()
        setUplate(data)
    }

    useEffect(() => {
        Promise.all([fetchRezervacija(), fetchUplate()])
          .finally(() => setLoading(false))
    }, [id])

    const handleAdd = () => {
        setSelectedUplata(null)
        setModalOpen(true)
    }

    const handleEdit = (uplata) => {
        setSelectedUplata(uplata)
        setModalOpen(true)
    }

    const handleDelete = async (idUplata) => {
        if (!confirm('Obrisati uplatu?')) {
            return
        }
        await fetch(`http://localhost:8080/api/uplata/${idUplata}`, { method: 'DELETE' })
        setUplate(prev => prev.filter(u => u.idUplata !== idUplata))
    }

    const handleSave = async (formData) => {
        const payload = {
            iznos: formData.iznos,
            datumIzvrsenja: formData.datumIzvrsenja || null,
            napomena: formData.napomena || null,
            statusUplate: { sifraStatusaUplate: formData.sifraStatusaUplate },
            rezervacija: { idRezervacija: parseInt(id) }
        }
        if (selectedUplata) {
            await fetch(`http://localhost:8080/api/uplata/${selectedUplata.idUplata}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
        } else {
            await fetch('http://localhost:8080/api/uplata', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
        }
        await fetchUplate()
        setModalOpen(false)
    }

    if (loading) return <p>Učitavanje...</p>

    return (
        <div>
            <button onClick={() => navigate('/rezervacije')}>← Natrag</button>
            <h2>Rezervacija #{id}</h2>
            <table>
                <tbody>
                    <tr>
                        <td><strong>Gost</strong></td>
                        <td>{rezervacija.gost.ime} {rezervacija.gost.prezime}</td>
                    </tr>
                    <tr>
                        <td><strong>Apartman</strong></td>
                        <td>{rezervacija.apartman.naziv} — {rezervacija.apartman.adresa}</td>
                    </tr>
                    <tr>
                        <td><strong>Datum od</strong></td>
                        <td>{rezervacija.datumOd}</td>
                    </tr>
                    <tr>
                        <td><strong>Datum do</strong></td>
                        <td>{rezervacija.datumDo}</td>
                    </tr>
                    <tr>
                        <td><strong>Broj osoba</strong></td>
                        <td>{rezervacija.brojOsoba}</td>
                    </tr>
                    <tr>
                        <td><strong>Ukupna cijena</strong></td>
                        <td>{rezervacija.ukupnaCijena} €</td>
                    </tr>
                    <tr>
                        <td><strong>Kanal</strong></td>
                        <td>{rezervacija.kanalRezervacije.naziv}</td>
                    </tr>
                    <tr>
                        <td><strong>Status</strong></td>
                        <td>{rezervacija.statusRezervacije.naziv}</td>
                    </tr>
                    <tr>
                        <td><strong>Datum rezervacije</strong></td>
                        <td>{rezervacija.datumRezervacije}</td>
                    </tr>
                </tbody>
            </table>

            <hr />
            <h3>Uplate</h3>
            <button onClick={handleAdd}>+ Dodaj uplatu</button>

            {uplate.length === 0 && <p>Nema uplata za ovu rezervaciju.</p>}

            <table>
                <thead>
                    <tr>
                        <th>Iznos</th>
                        <th>Datum izvršenja</th>
                        <th>Status</th>
                        <th>Napomena</th>
                        <th>Akcije</th>
                    </tr>
                </thead>
                <tbody>
                    {uplate.map(u => (
                        <tr key={u.idUplata}>
                            <td>{u.iznos} €</td>
                            <td>{u.datumIzvrsenja ?? '/'}</td>
                            <td>{u.statusUplate.naziv}</td>
                            <td>{u.napomena ?? '/'}</td>
                            <td>
                            <button className="edit" onClick={() => handleEdit(u)}>Uredi</button>
                            <button className="delete" onClick={() => handleDelete(u.idUplata)}>Obriši</button>
                            </td>
                        </tr>
                        ))}
                </tbody>
            </table>

            {modalOpen && (
                <UplataModal
                uplata={selectedUplata}
                onSave={handleSave}
                onClose={() => setModalOpen(false)}
                />
            )}
        </div>
    )
}

export default RezervacijaDetailPage
import { useState, useEffect } from 'react'

function KanalRezervacijeModal({ kanalRezervacije, onSave, onClose }) {
    const [form, setForm] = useState({
        naziv: '',
    })
    const [kanaliRezervacije, setKanaliRezervacije] = useState('')
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/kanal-rezervacije')
            .then(r => r.json())
            .then(data => {
            setKanaliRezervacije(data)
            setLoading(false)
        })
    }, [])

    useEffect(() => {
        if (kanalRezervacije) {
            setForm({
            naziv: kanalRezervacije.naziv ?? ''
            })
        }
    }, [kanalRezervacije])

    const handleChange = (e) => {
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
    }

    const handleSubmit = () => {
        if (!form.naziv) {
            setError('Naziv je obavezan.')
            return
        }
        onSave(form)
    }

    if (loading) return (
        <div className="modal-overlay">
            <div className="modal"><p>Učitavanje...</p></div>
        </div>
    )

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={e => e.stopPropagation()}>
                <h3>{kanalRezervacije ? 'Uredi kanal rezervacije' : 'Novi kanal rezervacije'}</h3>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <label>Naziv kanala rezervacije</label>
                <input name="naziv" type="text" value={form.naziv} onChange={handleChange} />

                <div className="modal-actions">
                    <button onClick={onClose}>Odustani</button>
                    <button onClick={handleSubmit}>Spremi</button>
                </div>
            </div>
        </div>
    )
}

export default KanalRezervacijeModal
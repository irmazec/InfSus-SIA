import { useState, useEffect } from 'react'

function UplataModal({ uplata, onSave, onClose }) {
    const [form, setForm] = useState({
        iznos: '',
        datumIzvrsenja: '',
        napomena: '',
        sifraStatusaUplate: ''
    })
    const [statusi, setStatusi] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/status-uplate')
            .then(r => r.json())
            .then(data => {
            setStatusi(data)
            setLoading(false)
          })
    }, [])

    useEffect(() => {
        if (uplata) {
            setForm({
                iznos: uplata.iznos,
                datumIzvrsenja: uplata.datumIzvrsenja ?? '',
                napomena: uplata.napomena ?? '',
                sifraStatusaUplate: uplata.statusUplate.sifraStatusaUplate
            })
        }
    }, [uplata])

    const handleChange = (e) => {
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
    }

    const handleSubmit = () => {
        if (!form.iznos || !form.sifraStatusaUplate) {
            setError('Iznos i status su obavezni.')
            return
        }
        if (form.iznos < 0){
            setError('Iznos manji od 0!')
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
                <h3>{uplata ? 'Uredi uplatu' : 'Nova uplata'}</h3>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <label>Iznos (€)</label>
                <input name="iznos" type="number" value={form.iznos} onChange={handleChange} />

                <label>Datum izvršenja</label>
                <input name="datumIzvrsenja" type="date" value={form.datumIzvrsenja} onChange={handleChange} />

                <label>Status uplate</label>
                <select name="sifraStatusaUplate" value={form.sifraStatusaUplate} onChange={handleChange}>
                <option value="">-- Odaberi status --</option>
                {statusi.map(s => (
                    <option key={s.sifraStatusaUplate} value={s.sifraStatusaUplate}>
                        {s.naziv}
                    </option>
                ))}
                </select>

                <label>Napomena</label>
                <input name="napomena" value={form.napomena} onChange={handleChange} />

                <div className="modal-actions">
                    <button onClick={onClose}>Odustani</button>
                    <button onClick={handleSubmit}>Spremi</button>
                </div>
            </div>
        </div>
    )
}

export default UplataModal
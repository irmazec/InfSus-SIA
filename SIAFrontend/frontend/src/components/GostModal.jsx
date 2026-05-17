import { useState } from 'react'

function GostModal({ onSave, onClose }) {
    const [form, setForm] = useState({
        ime: '',
        prezime: '',
        email: '',
        brojTelefona: '',
        drzavljanstvo: '',
        datumRodenja: ''
    })
    const [error, setError] = useState(null)
    const [saving, setSaving] = useState(false)

    const handleChange = (e) => {
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
    }

    const createGost = async (data) => {
        const response = await fetch('http://localhost:8080/api/gost', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        if (!response.ok){
            console.log(response)
        }
        return response.json()
    }

    const handleSubmit = async () => {
        if (!form.ime || !form.prezime || !form.email || !form.brojTelefona || !form.drzavljanstvo || !form.datumRodenja) {
            setError('Sva polja su obavezna.')
            return
        }
        setSaving(true)
        try {
            const noviGost = await createGost(form)
            onSave(noviGost)
        } catch (e) {
            setError('Greška pri spremanju gosta.')
        } finally {
            setSaving(false)
        }
    }

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={e => e.stopPropagation()}>
                <h3>Novi gost</h3>

                {error && <p style={{ color: 'red' }}>{error}</p>}

                <label>Ime</label>
                <input name="ime" value={form.ime} onChange={handleChange} />

                <label>Prezime</label>
                <input name="prezime" value={form.prezime} onChange={handleChange} />

                <label>Email</label>
                <input name="email" type="email" value={form.email} onChange={handleChange} />

                <label>Broj telefona</label>
                <input name="brojTelefona" value={form.brojTelefona} onChange={handleChange} />

                <label>Drzavljanstvo</label>
                <input name="drzavljanstvo" value={form.drzavljanstvo} onChange={handleChange} />

                <label>Datum rođenja</label>
                <input name="datumRodenja" type="date" value={form.datumRodenja} onChange={handleChange} />

                <div className="modal-actions">
                    <button onClick={onClose} disabled={saving}>Odustani</button>
                    <button onClick={handleSubmit} disabled={saving}>
                    {saving ? 'Spremanje...' : 'Spremi'}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default GostModal
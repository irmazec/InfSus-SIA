import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getRezervacija } from '../services/rezervacijaService';
import { getUplateByRezervacija, addUplata, updateUplata, deleteUplata } from '../services/uplataService';

const emptyUplataForm = {
    iznos: '',
    datumIzvrsenja: '',
    napomena: '',
    sifraStatusaUplate: '',
};

function RezervacijaDetailPage() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [rezervacija, setRezervacija] = useState(null);
    const [uplate, setUplate] = useState([]);
    const [statusiUplate, setStatusiUplate] = useState([]);

    const [formData, setFormData] = useState(emptyUplataForm);
    const [selectedUplataId, setSelectedUplataId] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchAll();
    }, [id]);

    const fetchAll = async () => {
        const [rezRes, uplataRes] = await Promise.all([
            getRezervacija(id),
            getUplateByRezervacija(id),
        ]);
        setRezervacija(rezRes.data);
        setUplate(uplataRes.data);
        setLoading(false);
    };

    const handleNewUplata = () => {
        setSelectedUplataId(null);
        setFormData(emptyUplataForm);
        setShowForm(true);
    };

    const handleEditUplata = (u) => {
        setSelectedUplataId(u.idUplata);
        setFormData({
            iznos: u.iznos,
            datumIzvrsenja: u.datumIzvrsenja ?? '',
            napomena: u.napomena ?? '',
            sifraStatusaUplate: u.statusUplate.sifraStatusaUplate,
        });
        setShowForm(true);
    };

    const handleDeleteUplata = async (uplataId) => {
        await deleteUplata(uplataId);
        fetchAll();
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = { ...formData, idRezervacija: id };
        if (selectedUplataId) {
            await updateUplata(selectedUplataId, payload);
        } else {
            await addUplata(payload);
        }
        setShowForm(false);
        setFormData(emptyUplataForm);
        setSelectedUplataId(null);
        fetchAll();
    };

    const handleCancel = () => {
        setShowForm(false);
        setFormData(emptyUplataForm);
        setSelectedUplataId(null);
    };

    if (loading) return <p>Učitavanje...</p>;

    return (
        <div>
            <button onClick={() => navigate('/rezervacije')}>← Natrag</button>

            {/* MASTER — reservation details */}
            <h1>Rezervacija #{rezervacija.idRezervacija}</h1>
            <table>
                <tbody>
                    <tr><td>Gost</td><td>{rezervacija.gost?.ime} {rezervacija.gost?.prezime}</td></tr>
                    <tr><td>Apartman</td><td>{rezervacija.apartman?.naziv}</td></tr>
                    <tr><td>Datum od</td><td>{rezervacija.datumOd}</td></tr>
                    <tr><td>Datum do</td><td>{rezervacija.datumDo}</td></tr>
                    <tr><td>Broj osoba</td><td>{rezervacija.brojOsoba}</td></tr>
                    <tr><td>Ukupna cijena</td><td>€{rezervacija.ukupnaCijena}</td></tr>
                    <tr><td>Status</td><td>{rezervacija.statusRezervacije?.naziv}</td></tr>
                    <tr><td>Kanal</td><td>{rezervacija.kanalRezervacije?.naziv}</td></tr>
                    <tr><td>Datum rezervacije</td><td>{rezervacija.datumRezervacije}</td></tr>
                </tbody>
            </table>

            {/* DETAIL — payments list + CRUD */}
            <h2>Uplate</h2>
            <button onClick={handleNewUplata}>Nova uplata</button>

            {showForm && (
                <form onSubmit={handleSubmit}>
                    <h3>{selectedUplataId ? 'Uredi uplatu' : 'Nova uplata'}</h3>

                    <div>
                        <label>Iznos</label>
                        <input
                            type="number"
                            name="iznos"
                            value={formData.iznos}
                            onChange={handleChange}
                            min="0"
                            step="0.01"
                            required
                        />
                    </div>
                    <div>
                        <label>Datum izvršenja</label>
                        <input
                            type="date"
                            name="datumIzvrsenja"
                            value={formData.datumIzvrsenja}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label>Napomena</label>
                        <input
                            type="text"
                            name="napomena"
                            value={formData.napomena}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label>Status uplate</label>
                        <select
                            name="sifraStatusaUplate"
                            value={formData.sifraStatusaUplate}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Odaberi status</option>
                            {statusiUplate.map(s => (
                                <option key={s.sifraStatusaUplate} value={s.sifraStatusaUplate}>
                                    {s.naziv}
                                </option>
                            ))}
                        </select>
                    </div>

                    <button type="submit">{selectedUplataId ? 'Spremi' : 'Dodaj'}</button>
                    <button type="button" onClick={handleCancel}>Odustani</button>
                </form>
            )}

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
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
                            <td>{u.idUplata}</td>
                            <td>€{u.iznos}</td>
                            <td>{u.datumIzvrsenja ?? '—'}</td>
                            <td>{u.statusUplate?.naziv}</td>
                            <td>{u.napomena ?? '—'}</td>
                            <td>
                                <button onClick={() => handleEditUplata(u)}>Uredi</button>
                                <button onClick={() => handleDeleteUplata(u.idUplata)}>Obriši</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default RezervacijaDetailPage;
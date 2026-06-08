import { useState, useEffect } from "react";

export default function UplataProcesPage() {
    const [tasks, setTasks] = useState([]);
    const [processes, setProcesses] = useState([]);
    const [toast, setToast] = useState(null);

    const [rezervacijaId, setRezervacijaId] = useState("");
    const [gostEmail, setGostEmail] = useState("");
    const [cekanje, setCekanje] = useState(null);

    const [taskId, setTaskId] = useState("");
    const [iznos, setIznos] = useState("");
    const [podaciIspravni, setPodaciIspravni] = useState(false);
    const [uplataOtplacena, setUplataOtplacena] = useState(false);

    function showToast(msg, ok = true) {
        setToast({ msg, ok });
        setTimeout(() => setToast(null), 3000);
    }

    async function loadTasks() {
        try {
            const res = await fetch("http://localhost:8080/api/uplata-proces/zadaci");
            const data = await res.json();
            setTasks(data);
            setProcesses(
                data.map((t) => ({
                    instanceId: t.taskId,
                    rezervacijaId: t.vars?.rezervacijaId || "?",
                    guestEmail: t.vars?.guestEmail || "?",
                    taskName: t.taskName,
                    taskId: t.taskId,
                }))
            );
        } catch {
            showToast("Greška pri dohvaćanju zadataka", false);
        }
    }

    async function startProcess() {
        if (!rezervacijaId || !gostEmail) {
            showToast("Unesite rezervacija ID i email gosta", false);
         return;
        }
        try {
            const res = await fetch("http://localhost:8080/api/uplata-proces/start", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ rezervacijaId: parseInt(rezervacijaId), gostEmail }),
            });
            const instanceId = await res.text();
            showToast("Proces pokrenut! ID: " + instanceId);
            setRezervacijaId("");
            setGostEmail("");
            setTimeout(loadTasks, 500);
        } catch {
            showToast("Greška pri pokretanju procesa", false);
        }
    }

    async function evidentiraj() {
        if (!taskId) { showToast("Odaberite ili unesite Task ID", false); return; }
        if (!iznos || parseFloat(iznos) <= 0) { showToast("Iznos mora biti veći od 0", false); return; }
        try {
            await fetch("http://localhost:8080/api/uplata-proces/zadaci/" + taskId + "/provedi", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                taskId: taskId,
                iznos: parseFloat(iznos),
                podaciIspravni,
                uplataOtplacena
            }),
            });
            console.log(podaciIspravni, uplataOtplacena);
            if (podaciIspravni && !uplataOtplacena) {
                const task = tasks.find(t => t.taskId === taskId);
                setCekanje({
                    instanceId: taskId,
                    rezervacijaId: task.vars.rezervacijaId,
                    expiresAt: Date.now() + 30000
                });
                showToast("Podaci ispravni — čeka se uplata, imate 30 sekundi.");
            } else if (podaciIspravni && uplataOtplacena){
                showToast("Uplata evidentirana!");
                setTaskId("");
                setIznos("");
                setPodaciIspravni(false);
                setUplataOtplacena(false);
                setTimeout(loadTasks, 500);
            }
        } catch {
            showToast("Greška pri evidentiranju", false);
        }
    }

    async function registrirajUplatu() {
        try {
            await fetch("http://localhost:8080/api/uplata-proces/" + cekanje.instanceId + "/uplati", {
                method: "POST",
            });
            showToast("Uplata registrirana!");
            setCekanje(null);
            setTimeout(loadTasks, 500);
        } catch {
            showToast("Greška pri registraciji uplate", false);
        }
    }

    async function izbrisiRezervaciju() {
        try {
            await fetch("http://localhost:8080/api/uplata-proces/" + cekanje.instanceId + "/izbrisi", {
                method: "POST",
            });
            showToast("Rezervacija izbrisana");
            setCekanje(null);
            setTimeout(loadTasks, 500);
        } catch {
            showToast("Greška pri brisanju", false);
        }
    }

    function statusBadge(taskName) {
        if (!taskName) return <span className="badge badge-green">Završeno</span>;
        if (taskName.toLowerCase().includes("ispravi")) return <span className="badge badge-red">Treba ispravak</span>;
        if (taskName.toLowerCase().includes("čeka")) return <span className="badge badge-amber">Čeka uplatu</span>;
        return <span className="badge badge-blue">{taskName}</span>;
    }

    useEffect(() => { loadTasks(); }, []);

    return (
        <div className="page">
            {toast && (
                <div className={`toast ${toast.ok ? "toast-ok" : "toast-err"}`}>
                    {toast.msg}
                </div>
            )}

            <h1>Poslovni proces: uplata</h1>

            <div className="grid">
                <div className="col">
                    <div className="card">
                        <h2>Aktivni procesi</h2>
                        {processes.length === 0 ? (
                            <p className="empty">Nema aktivnih procesa</p>
                        ) : (
                            processes.map((p) => (
                                <div className="process-row" key={p.taskId}>
                                    <div>
                                        <div className="row-title">Rezervacija #{p.rezervacijaId}</div>
                                        <div className="row-sub">{p.instanceId}</div>
                                    </div>
                                    {statusBadge(p.taskName)}
                                </div>
                            ))
                        )}
                    </div>

                    <div className="card">
                        <h2>Aktivni zadaci</h2>
                        {tasks.length === 0 ? (
                            <p className="empty">Nema aktivnih zadataka</p>
                        ) : (
                            tasks.map((t) => (
                                <div className="task-row" key={t.taskId}>
                                    <div>
                                        <div className="row-title">{t.taskName}</div>
                                        <div className="row-sub">ID: {t.taskId}</div>
                                    </div>
                                    <button className="btn btn-sm" onClick={() => { setTaskId(t.taskId); showToast("Task ID odabran"); }}>
                                        Odaberi
                                    </button>
                                </div>
                            ))
                        )}
                        <button className="btn btn-sm" style={{ marginTop: "0.75rem" }} onClick={loadTasks}>
                            Osvježi
                        </button>
                    </div>
                </div>

                <div className="col">

                    <div className="card">
                        <h2>Novi proces</h2>
                        <div className="field">
                            <label>Rezervacija ID</label>
                            <input type="number" value={rezervacijaId} onChange={(e) => setRezervacijaId(e.target.value)} placeholder="npr. 42" />
                        </div>
                        <div className="field">
                            <label>Email gosta</label>
                            <input type="email" value={gostEmail} onChange={(e) => setGostEmail(e.target.value)} placeholder="gost@example.com" />
                        </div>
                        <button className="btn btn-primary" onClick={startProcess}>
                            Pokreni proces
                        </button>
                    </div>

                    <div className="card">
                        <h2>Evidentiraj uplatu</h2>
                        <div className="field">
                            <label>Task ID</label>
                            <input type="text" value={taskId} onChange={(e) => setTaskId(e.target.value)} placeholder="odaberi zadatak ili unesi ručno" />
                        </div>
                        <div className="field">
                            <label>Iznos (€)</label>
                            <input type="number" value={iznos} onChange={(e) => setIznos(e.target.value) } placeholder="0.00" step="0.01" min="0" />
                        </div>
                        <div className="checks">
                            <label>
                                <input
                                  type="checkbox"
                                  checked={podaciIspravni}
                                  onChange={(e) => {
                                      if (e.target.checked && parseFloat(iznos) <= 0) {
                                          showToast("Iznos mora biti veći od 0", false);
                                          setPodaciIspravni(false);
                                      }
                                      setPodaciIspravni(e.target.checked);
                                  }}
                                />
                                Podaci ispravni
                            </label>
                            <label>
                                <input type="checkbox" checked={uplataOtplacena} onChange={(e) => setUplataOtplacena(e.target.checked)} />
                                Uplata otplaćena
                            </label>
                        </div>
                        <button className="btn btn-primary" onClick={evidentiraj}>
                            Evidentiraj
                        </button>
                        {cekanje && (
                            <div className="card">
                                <h2>Čeka se uplata</h2>
                                <p>Rezervacija #{cekanje.rezervacijaId}</p>
                                <button
                                    className="btn btn-primary"
                                    onClick={registrirajUplatu}
                                >
                                    Registriraj uplatu
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <div className="modelImg">
                <img src="../public/ModelProcesa.png" alt="Izgled poslovnog modela" width="700" height="300"/>
            </div>
        </div>
    );
}
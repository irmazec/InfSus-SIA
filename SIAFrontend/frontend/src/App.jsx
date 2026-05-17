import { BrowserRouter, Routes, Route } from 'react-router-dom';
import RezervacijePage from './pages/RezervacijePage';
import KanaliRezervacijePage from './pages/KanaliRezervacijePage';
import RezervacijaDetailPage from './pages/RezervacijaDetailPage';
import NavBar from './components/NavBar'

function App() {
    return (
        <NavBar></NavBar>
        <BrowserRouter>
            <Routes>
                <Route path="/rezervacije" element={<RezervacijePage />} />
                 <Route path="/rezervacije/:id" element={<RezervacijaDetailPage />} />
                <Route path="/kanali-rezervacije" element={<KanaliRezervacijePage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
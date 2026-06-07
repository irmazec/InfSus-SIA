import {Routes, Route } from 'react-router-dom';
import {BrowserRouter} from 'react-router';
import RezervacijePage from './pages/RezervacijePage';
import KanaliRezervacijePage from './pages/KanaliRezervacijePage';
import RezervacijeDetailPage from './pages/RezervacijeDetailPage';
import UplataProcessPage from './pages/UplataProcessPage';
import NavBar from './components/NavBar'

function App() {
    return (
        <BrowserRouter>
            <NavBar />
            <Routes>
                <Route path="/rezervacije" element={<RezervacijePage />} />
                <Route path="/rezervacije/:id" element={<RezervacijeDetailPage />} />
                <Route path="/kanali-rezervacije" element={<KanaliRezervacijePage />} />
                <Route path="/uplata-proces" element={<UplataProcessPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
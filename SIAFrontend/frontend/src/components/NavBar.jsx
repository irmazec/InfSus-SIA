import { NavLink } from 'react-router-dom';

function Navbar() {
    return (
        <nav>
            <NavLink to="/rezervacije">Rezervacije</NavLink>
            <NavLink to="/kanali-rezervacije">Kanali rezervacije</NavLink>
        </nav>
    );
}

export default Navbar;
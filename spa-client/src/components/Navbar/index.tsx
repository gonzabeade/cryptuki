import './styles.css';

import icons from "../../assets";
import { Link } from 'react-router-dom';

const Navbar = () => {

    return (
        <nav className="navbar">
            <Link to="/">
                <img src={icons.logo} alt="cryptuki logo"/>
            </Link>
            <div className="navbar--options">
                <Link to="/">
                    <h1>Mercado P2P</h1>
                </Link>
                <Link to="/contact">
                    <h1>Contáctate</h1>
                </Link>
                <Link to="/login">
                    <button className="gray bold">Inicia sesión</button>
                </Link>
                <Link to="/register">
                    <button className="dark bold">Regístrate</button>
                </Link>
            </div>
        </nav>
    )

}; 

export default Navbar; 
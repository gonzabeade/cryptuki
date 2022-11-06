import './styles.css';

import icons from "../../assets";

const Navbar = () => {

    return (
        <nav className="navbar">
            <img src={icons.logo} alt="cryptuki logo"/>
            <div className="navbar--options">
                <h1>Mercado P2P</h1>
                <h1>Contáctate</h1>
                <button className="gray bold">Inicia sesión</button>
                <button className="dark bold">Regístrate</button>
            </div>
        </nav>
    )

}; 

export default Navbar; 
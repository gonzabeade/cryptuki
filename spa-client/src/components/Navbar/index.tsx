import './styles.css';

import icons from "../../assets";

const Navbar = () => {

    return (
        <nav className="navbar">
            <img src={icons.logo} alt="cryptuki logo"/>
            <div className="navbar--options">
                <h1>Mercado P2P</h1>
                <h1>Contáctate</h1>
                <h2 className="button gray">Inicia sesión</h2>
                <h2 className="button dark">Regístrate</h2>
            </div>
        </nav>
    )

}; 

export default Navbar; 
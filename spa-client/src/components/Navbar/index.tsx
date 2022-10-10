import React from "react";
import './styles.css';

const logo = require("../../assets/logo.png"); 

const Navbar = () => {

    return (
        <nav className="navbar">
            <img src={logo} alt="cryptuki logo"/>
            <div className="navbar--options">
                <h1>Mercado P2P</h1>
                <h1>Contáctate</h1>
                <h2>Inicia sesión</h2>
                <h2>Regístrate</h2>
            </div>
        </nav>
    )

}; 

export default Navbar; 
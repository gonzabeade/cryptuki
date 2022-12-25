import './styles.css';

import icons from "../../assets";
import { Link } from 'react-router-dom';
import useUserService from '../../hooks/useUserService';
import { useState } from 'react';

const Navbar = () => {

    const userService = useUserService(); 

    const [username, setUsername] = useState<string | null>(userService.getLoggedInUser()); 

    return (
        <nav className="navbar">
            <Link to="/">
                <img src={icons.logo} alt="cryptuki logo"/>
            </Link>
            <div className="navbar--options">
                <Link to="/" className="hover:cursor-pointer">
                    <h1>Mercado P2P</h1>
                </Link>
                <Link to="/contact" className="hover:cursor-pointer">
                    <h1>Contáctate</h1>
                </Link>
                {
                    !username && 
                    <div className='user-holder'>
                            <Link to="/login">
                                <button className="gray bold">Inicia sesión</button>
                            </Link>
                            <Link to="/register">
                                <button className="dark bold">Regístrate</button>
                            </Link>
                    </div> 
                }
                {
                    username && 
                    <div className='user-holder'>
                        <Link to="/seller">
                            <button className="gray bold">Panel de vendedor</button>
                        </Link>
                        <Link onClick={ () => {setUsername(userService.getLoggedInUser())}} to="/buyer">
                            <div className="profile-icon">
                                <img src={icons.profile} alt="profile icon"/>
                                <p>{username}</p>
                            </div>
                        </Link>
                        <img className="logout" onClick={() => {localStorage.removeItem("refreshToken"); localStorage.removeItem("accessToken"); setUsername(null); } } src={icons.logout} alt="logout"/>
                    </div> 
                }
            </div>
        </nav>
    )

}; 

export default Navbar; 
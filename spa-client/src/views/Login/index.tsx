import { useState, FormEvent, useEffect, useContext } from "react";
import {Link, useNavigate, useLocation} from "react-router-dom"; 
import { paths } from "../../common/constants";
import { withBasicAuthorization } from "../../hooks/useAxios";

const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = paths.USERS;

const Login = () => {

    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');

    const location = useLocation(); 
    const from = location.state?.from?.pathname || "/"; 

    const navigate = useNavigate();

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        withBasicAuthorization(user, pwd); 
        navigate(from, {replace: true}); 
    }

    return (
        <section>
            <form onSubmit={handleSubmit}>
                <h1>Inicia sesión</h1>
                <label htmlFor="username">Usuario:</label>
                <input
                    placeholder="e.g. miusuario"
                    type="text"
                    id="username"
                    autoComplete="off"
                    onChange={(e) => setUser(e.target.value)}
                    value={user}
                    required
                />
                <label htmlFor="password">Contraseña:</label>
                <input
                    placeholder="micontraseña"
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                />
                
                <button type="submit" className="green">Inicia sesión</button>
                <p>
                    ¿Aún o tienes una cuenta?<br />
                    <span className="line">
                        {/*put router link here*/}
                        <Link to="/register">Regístrate</Link>
                    </span>
                </p>
            </form>
        </section>
    )
}

export default Login; 
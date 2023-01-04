import { useState, FormEvent } from "react";
import {Link, useNavigate, useLocation} from "react-router-dom"; 

import { withBasicAuthorization } from "../../hooks/useAxios";

// const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
// const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
// const REGISTER_URL = paths.USERS;

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
        <div className=" w-full flex justify-center">
            <form onSubmit={handleSubmit} className="flex
            flex-col mx-auto mt-24 w-[600px]
            bg-whitesmoke shadow-lg rounded-lg px-24 pt-10  pb-16
            border-frostdr border-t-8">
                <h1 className="font-sans font-roboto font-bold text-xl mx-auto text-polar">Sign in </h1>
                <h4 className="text-center font-lato text-black/[.4] text-sm mb-4">Welcome to Cryptuki</h4>
                <input
                    placeholder="Username"
                    type="text"
                    id="username"
                    autoComplete="off"
                    onChange={(e) => setUser(e.target.value)}
                    value={user}
                    required
                    className="p-2 m-2 rounded-lg"
                />
                <input
                    placeholder="Password"
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                    className="p-2 m-2 rounded-lg"
                />
                
                <button type="submit" className="bg-frostdr text-white mx-auto mb-auto mt-6 py-2 px-4 rounded-lg font-lato font-bold hover:bg-polar">Sign in</button>
                <p className="font-lato font-light mx-auto text-xs mt-2 text-black/[.4]">No account? No problem<br/></p>
                <p className=" hover:cursor-pointer text-polar hover:text-blue-400 font-bold font-frostdr  mx-auto text-xs font-lato">{/*put router link here*/}<Link
                    to="/register">Create your account</Link>
                </p>

            </form>
        </div>
    )
}

export default Login; 
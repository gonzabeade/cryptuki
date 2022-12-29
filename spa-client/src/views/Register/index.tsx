import { useRef, useState, useEffect, FormEvent } from "react";
import { paths } from "../../common/constants";
import {Link} from "react-router-dom"; 

const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = paths.USERS;

const Register = () => {

    const [user, setUser] = useState('');
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        setValidName(USER_REGEX.test(user));
    }, [user])

    useEffect(() => {
        setValidPwd(PWD_REGEX.test(pwd));
        setValidMatch(pwd === matchPwd);
    }, [pwd, matchPwd])

    useEffect(() => {
        setErrMsg('');
    }, [user, pwd, matchPwd])

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // if button enabled with JS hack
        const v1 = USER_REGEX.test(user);
        const v2 = PWD_REGEX.test(pwd);
        if (!v1 || !v2) {
            setErrMsg("Invalid Entry");
            return;
        }
        try {
            // const response = await axios.post(
            //     REGISTER_URL,
            //     JSON.stringify({ user, pwd }), // Mock data in the future, when testing axios 
            //     {
            //         headers: { 'Content-Type': 'application/json' },
            //         withCredentials: true
            //     }
            // );
            // console.log(response?.data);
            // console.log(response?.accessToken);
            // console.log(JSON.stringify(response))
            setSuccess(true);
            //clear state and controlled inputs
            //need value attrib on inputs for this
            setUser('');
            setPwd('');
            setMatchPwd('');
        } catch (err) {
            // Errors in the POST 
        }
    }

    return (
        <div  className=" w-full flex justify-center">
            <form onSubmit={handleSubmit} className="flex
            flex-col mx-auto mt-24 w-[600px]
            bg-whitesmoke shadow-lg rounded-lg px-24 pt-10  pb-14
            border-frostdr border-t-8">
                <h1 className="font-sans font-roboto font-bold text-xl mx-auto text-polar mb-4">Create your account</h1>
                <input
                    placeholder="Username"
                    type="text"
                    id="username"
                    autoComplete="off"
                    onChange={(e) => setUser(e.target.value)}
                    value={user}
                    required
                    aria-invalid={validName ? "false" : "true"}
                    aria-describedby="uidnote"
                    onFocus={() => setUserFocus(true)}
                    onBlur={() => setUserFocus(false)}
                    className="p-2 m-2 rounded-lg"
                />
                <input
                    placeholder="Password"
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                    aria-invalid={validPwd ? "false" : "true"}
                    aria-describedby="pwdnote"
                    onFocus={() => setPwdFocus(true)}
                    onBlur={() => setPwdFocus(false)}
                    className="p-2 m-2 rounded-lg"
                />
                <input
                    type="password"
                    id="confirm_pwd"
                    placeholder="Repeat new password"
                    onChange={(e) => setMatchPwd(e.target.value)}
                    value={matchPwd}
                    required
                    aria-invalid={validMatch ? "false" : "true"}
                    aria-describedby="confirmnote"
                    onFocus={() => setMatchFocus(true)}
                    onBlur={() => setMatchFocus(false)}
                    className="p-2 m-2 rounded-lg"
                />
                <button  className="bg-frostdr text-white mx-auto mb-auto mt-8 py-2 px-4 rounded-lg font-lato font-bold hover:bg-blue-700">Sign Up</button>
                <p className="font-lato font-light mx-auto text-xs mt-2 text-black/[.4]">Already registered?</p>
                    <p className=" hover:cursor-pointer  hover:text-blue-400 font-bold text-polar mx-auto text-xs font-lato">
                        <Link to="/login">Sign in</Link>
                    </p>
            </form>
        </div>
    )
}

export default Register; 
import {Link, useNavigate} from "react-router-dom";
import {paths, sleep} from "../../common/constants";
import {withBasicAuthorization} from "../../hooks/useAxios";
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";


const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;

type LoginFormValues = {
    username: string;
    password: string;
}

const Login = () => {

    const { register, handleSubmit, formState: { errors } } = useForm<LoginFormValues>();
    const userService = useUserService();
    const navigate = useNavigate();

    async function onSubmit(data:LoginFormValues){
        withBasicAuthorization(data.username, data.password);
        try{
            // dummy call to get the token
            await userService.getUser(data.username);
            toast.success("Successfully logged in!");

            await sleep(1000);
        }catch (e){
            toast.error("Invalid credentials");
        }

    }

    return (
        <div className=" w-full flex justify-center">
            <form onSubmit={handleSubmit(onSubmit)} className="flex
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
                    {...register("username", {required: true, pattern: {value:USER_REGEX, message: "Invalid username"}})}
                    className="p-2 m-2 rounded-lg"
                />
                {errors && errors.username && <span className="text-red-500">{errors.username.message}</span>}
                <input
                    placeholder="Password"
                    type="password"
                    id="password"
                    {...register("password",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"
                        }})}
                    className="p-2 m-2 rounded-lg"
                />
                {errors && errors.password && <span className="text-red-500">{errors.password.message}</span>}

                
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
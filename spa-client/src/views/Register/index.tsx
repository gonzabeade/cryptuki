import { useState, useEffect, FormEvent } from "react";
import {paths, sleep} from "../../common/constants";
import {Link, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";

const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;

type RegisterFormValues = {
    username: string;
    password: string;
    repeatPassword: string;
    phoneNumber: string;
    email: string;
}
const Register = () => {

    const { register, handleSubmit, formState: { errors } } = useForm<RegisterFormValues>();
    const userService = useUserService();
    const navigate = useNavigate();

    async function onSubmit(data:RegisterFormValues){
        try{
            const resp = userService.register(data.username, data.password, data.repeatPassword, data.phoneNumber, data.email);
            toast.success("Successfully registered!");
            await sleep(1000);
            navigate('/verify?user='+data.username);
        }catch (e) {
            toast.error("Connection error. Please try again later");
        }

    }

    return (
        <div  className=" w-full flex justify-center">
            <form onSubmit={handleSubmit(onSubmit)} className="flex
            flex-col mx-auto mt-24 w-[600px]
            bg-whitesmoke shadow-lg rounded-lg px-24 pt-10  pb-14
            border-frostdr border-t-8">
                <h1 className="font-sans font-roboto font-bold text-xl mx-auto text-polar mb-4">Create your account</h1>
                <input
                    placeholder="Username"
                    type="text"
                    id="username"
                    autoComplete="off"
                    required
                    className="p-2 m-2 rounded-lg"
                    {...register("username", {required: true, pattern: {value:USER_REGEX, message: "Invalid username"}})}
                />
                {errors && errors.username && <span className="text-red-500">{errors.username.message}</span>}
                <input
                    placeholder="Password"
                    type="password"
                    id="password"
                    className="p-2 m-2 rounded-lg"
                    {...register("password",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                />
                {errors && errors.password && <span className="text-red-500">{errors.password.message}</span>}
                <input
                    type="password"
                    id="confirm_pwd"
                    placeholder="Repeat new password"
                    className="p-2 m-2 rounded-lg"
                    {...register("repeatPassword",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                />
                {errors && errors.repeatPassword && <span className="text-red-500">{errors.repeatPassword.message}</span>}
                <input
                    type="email"
                    id="email"
                    placeholder="Email"
                    className="p-2 m-2 rounded-lg"
                    {...register("email",{required: true})}
                />
                {errors && errors.email && <span className="text-red-500">{errors.email.message}</span>}
                <input
                    type="number"
                    id="phoneNumber"
                    placeholder="Phone number"
                    className="p-2 m-2 rounded-lg"
                    {...register("phoneNumber",{required: "Phone number is required"})}
                />
                {errors && errors.phoneNumber && <span className="text-red-500">{errors.phoneNumber.message}</span>}
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
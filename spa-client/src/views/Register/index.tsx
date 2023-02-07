
import {Link, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
import i18n from "../../i18n";
import React from "react";
import {AxiosError} from "axios";

const USER_REGEX = /^[A-z][A-z0-9-_]{6,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;

type RegisterFormValues = {
    username: string;
    password: string;
    repeatPassword: string;
    phoneNumber: string;
    email: string;
}
const Register = () => {

    const { register, handleSubmit, formState: { errors }, getValues } = useForm<RegisterFormValues>();
    const userService = useUserService();
    const navigate = useNavigate();

    async function onSubmit(data:RegisterFormValues){
        try{
            await userService.register(data.username, data.password, data.repeatPassword, data.phoneNumber, data.email);
            toast.success(i18n.t('successfullyRegistered'));
            navigate('/verify?username='+data.username);
        }catch (e) {
            const error: AxiosError =  e as AxiosError;
            if(error.response?.data){
                const errors = error.response.data as { message:string, path:string }[];
                console.log(errors)
                errors.map((error) =>
                    toast.error(i18n.t('errorForm')+ i18n.t(`${error.path}`))
                );
            }else{
                toast.error(i18n.t(`${error.code}`))
            }
        }

    }

    return (
        <div  className=" w-full flex justify-center">
            <form onSubmit={handleSubmit(onSubmit)} className="flex
            flex-col mx-auto mt-24 w-[600px]
            bg-whitesmoke shadow-lg rounded-lg px-24 pt-10  pb-14
            border-frostdr border-t-8">
                <h1 className="font-sans font-roboto font-bold text-xl mx-auto text-polar mb-4">{i18n.t('createAccount')}</h1>
                <input
                    placeholder={i18n.t('username')!}
                    type="text"
                    id="username"
                    autoComplete="off"
                    required
                    className="p-2 m-2 rounded-lg"
                    {...register("username", {required: true, pattern: {value:USER_REGEX,
                            message: "Invalid username. Must have more than 6 characters "}})}
                />
                {errors && errors.username && <span className="text-red-500">{errors.username.message}</span>}
                <input
                    placeholder={i18n.t('password')!}
                    type="password"
                    id="password"
                    className="p-2 m-2 rounded-lg"
                    {...register("password",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                />
                {errors && errors.password && <span className="text-red-500">{errors.password.message}</span>}
                <input
                    type="password"
                    id="confirm_pwd"
                    placeholder={i18n.t('repeatPassword')!}
                    className="p-2 m-2 rounded-lg"
                    {...register("repeatPassword",{required: true, validate:checkEquals, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                />
                {errors && errors.repeatPassword && <span className="text-red-500">{errors.repeatPassword.message}</span>}
                {errors && errors.repeatPassword?.type === "validate" && <span className="text-red-500">{i18n.t('passwordDontMatch')}</span>}
                <input
                    type="email"
                    id="email"
                    placeholder={i18n.t('email')!}
                    className="p-2 m-2 rounded-lg"
                    {...register("email",{required: true})}
                />
                {errors && errors.email && <span className="text-red-500">{errors.email.message}</span>}
                <input
                    type="number"
                    id="phoneNumber"
                    placeholder={i18n.t('phoneNumber')!}
                    className="p-2 m-2 rounded-lg"
                    {...register("phoneNumber",{required:"Phone number is required"})}
                />
                {errors && errors.phoneNumber && <span className="text-red-500">{errors.phoneNumber.message}</span>}
                <button  className="bg-frostdr text-white mx-auto mb-auto mt-8 py-2 px-4 rounded-lg font-lato font-bold hover:bg-blue-700">{i18n.t('signup')}</button>
                <p className="font-lato font-light mx-auto text-xs mt-2 text-black/[.4]">{i18n.t('alreadyRegistered')}</p>
                    <p className=" hover:cursor-pointer  hover:text-blue-400 font-bold text-polar mx-auto text-xs font-lato">
                        <Link to="/login">{i18n.t('signin')}</Link>
                    </p>
            </form>
        </div>
    )
    function checkEquals(repeatPassword:string){
        return repeatPassword === getValues().password ;
    }
}

export default Register; 
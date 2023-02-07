import React, {useEffect, useState} from 'react';
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {useLocation, useNavigate, useSearchParams} from "react-router-dom";
import i18n from "../../i18n";
import {withBasicAuthorization, withBasicAuthorizationWithCode} from "../../hooks/useAxios";
import {toast} from "react-toastify";
import {sleep} from "../../common/constants";
import useTradeService from "../../hooks/useTradeService";
import {useAuth} from "../../contexts/AuthContext";

// const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z]).{8,24}$/;

export type changePasswordForm = {
    password: string;
    repeatPassword: string;
}


const ChangePassword = () => {
    const { register, handleSubmit, formState: { errors }, getValues } = useForm<changePasswordForm>();
    const userService = useUserService();
    const [username, setUsername] = useState<string|null>();
    const [code, setCode] = useState<string|null>();
    const navigate = useNavigate();
    const [searchParams]= useSearchParams();
    const tradeService = useTradeService();
    const {signin} = useAuth();
    const location = useLocation();


    async function onSubmit(data:changePasswordForm){
        if(!username){
            toast.error(i18n.t('failedToUpdatePassword'));
            return;
        }
        try{
            if(username && code){
                withBasicAuthorizationWithCode(username, code)
            }
            await userService.changePassword(data,username)
            toast.success(i18n.t('passwordUpdated'))
            if(username && code) {
                await login(username,data.password);
                navigate("/");
            }else navigate(-1);
        }catch (e) {
            toast.error(i18n.t('failedToUpdatePassword'))
        }
    }

    async function login(username:string, password:string){
        withBasicAuthorization(username, password);
        try{
            // dummy call to get the token
            const resp = await tradeService.getLastTransactions(username);
            signin(await userService.getUser(username), ()=>{
                if(location.state && location.state.url) {
                    navigate(location.state.url);
                }else if( userService.getRole() === "ROLE_ADMIN"){
                    navigate("/admin")
                }else{
                    navigate('/')
                }
            });
            await sleep(500);
        }catch (e){
            toast.error(i18n.t('unableToSignIn'))
        }
    }


    useEffect(()=>{
        const username = searchParams.get("username") !== null ? searchParams.get("username"):userService.getLoggedInUser();
        setUsername(username)
        if(searchParams.get("code") && searchParams.get("code") !== null)
            setCode(searchParams.get("code"))
    },[])

    return (
        <div className="flex mt-10 mb-10">
            <form id="changePasswordForm" onSubmit={handleSubmit(onSubmit)}
                  className=" py-12 px-36 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard"
                  >

                <h2 className="text-center text-4xl font-semibold font-sans text-polar">{i18n.t('changePassword')}</h2>

                <div className="flex flex-col mt-3">
                    <label htmlFor="password"
                           className="text-center text-xl font-bold font-sans text-polar my-2">{i18n.t('password')}</label>

                    <div className="flex flex-col">
                        <input
                            placeholder={i18n.t('password')!}
                            type="password"
                            id="password"
                            className="rounded-lg p-3 w-full"
                            {...register("password",{required: true, pattern: {value: PWD_REGEX, message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}})}
                        />
                        {errors && errors.password && <span className="text-red-500">{errors.password.message}</span>}
                    </div>
                </div>
                <div className="flex flex-col mt-3">
                    <label htmlFor="repeatPassword" className="text-center text-xl font-bold font-sans text-polar my-2">{i18n.t('repeatPassword')}</label>

                    <div className="flex flex-col">
                        <input
                            type="password"
                            id="confirm_pwd"
                            placeholder={i18n.t('repeatPassword')!}
                            className="rounded-lg p-3  w-full"
                            {...register("repeatPassword",
                                {required: true,
                                    validate:checkEquals,
                                    pattern: {value: PWD_REGEX,
                                        message: "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"}
                                    })}
                        />
                        {errors && errors.repeatPassword && <span className="text-red-500">{errors.repeatPassword.message}</span>}
                        {errors && errors.repeatPassword?.type === "validate" && <span className="text-red-500">{i18n.t('passwordDontMatch')}</span>}
                    </div>

                </div>


                <div className="flex flex-row justify-center mt-6">
                    <button type="submit"
                           className="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg">
                        {i18n.t('confirm')}
                    </button>
                </div>
            </form>
        </div>
    );
    function checkEquals(repeatPassword:string){
        return repeatPassword === getValues().password ;
    }

};

export default ChangePassword;
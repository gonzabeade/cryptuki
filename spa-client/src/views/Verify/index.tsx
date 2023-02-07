import React, {useEffect} from 'react';
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {useNavigate, useSearchParams} from "react-router-dom";
import {toast} from "react-toastify";
import i18n from "../../i18n";
import {useAuth} from "../../contexts/AuthContext";
import {AxiosError} from "axios";

type VerifyFormValues ={
    code:number
}

const Verify = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<VerifyFormValues>();
    const userService = useUserService();
    const [searchParams]= useSearchParams();
    const navigate = useNavigate();
    const {signin} = useAuth();

    useEffect(()=>{
        automaticPost();
    }, [])

    async function automaticPost(){
        if(!searchParams.get("username") || !searchParams.get("code"))
            return;
        try{
            if(searchParams.get("code") && searchParams.get("username")){
                await userService.verifyUser(Number(searchParams.get("code")), searchParams.get("username")!);
                toast.success(i18n.t('successfullyVerified'));
                navigate("/");
            }
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined))
            {
                if(e.response.status === 400 )
                    toast.error(i18n.t('userOrCodeInvalid'));
            }else{
                toast.error(i18n.t('connectionError') );
            }
        }
    }

    async function onSubmit(data:VerifyFormValues){
        if(!searchParams.get("username"))
            return;
        try{
            await userService.verifyUser(data.code, searchParams.get("username")!);
            toast.success(i18n.t('successfullyVerified'));
            navigate("/");
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined))
            {
                if(e.response.status === 400 )
                    toast.error(i18n.t('userOrCodeInvalid'));
            }else{
                toast.error(i18n.t('connectionError') );
            }
        }

    }
    return (

        <div className="flex mt-20 mb-10">
            <form id="codeForm" className="flex flex-col mx-auto" onSubmit={handleSubmit(onSubmit)}>
                <h1 className="text-center text-4xl font-semibold font-sans text-polar">{i18n.t('verifyYourAccount')}</h1>
                <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">{i18n.t('weSentYourCode')}</h3>

                <div className="flex flex-col">
                    <label htmlFor="code"
                           className="text-center text-xl font-bold font-sans text-polar my-2">{i18n.t('code')}</label>
                    <input id="code" className="rounded-lg p-3 mx-auto" type="number"
                           {...register("code", {required: i18n.t('codeRequired')!})}
                    />
                    {errors && errors.code && <p className="text-red-600 mx-auto my-2">{errors.code.message} </p>}
                </div>
                <div className="flex mx-auto mt-10">
                    <button type="submit"
                            className="rounded-lg bg-frost py-3 px-5 text-white font-bold cursor-pointer shadow-lg">{i18n.t('verify')}
                    </button>
                </div>

            </form>
        </div>
    );
};

export default Verify;
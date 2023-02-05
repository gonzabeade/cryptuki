import React, {useEffect, useState} from 'react';
import i18n from "../../i18n";
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {attendError} from "../../common/utils/utils";
import {Link, useNavigate, useSearchParams} from "react-router-dom";
import {toast} from "react-toastify";

export type recoverPasswordForm={
    email:string
}


const Index = () => {

    const { register, handleSubmit, formState: { errors } , reset} = useForm<recoverPasswordForm>();
    const useService = useUserService();
    const [sent,setSent] = useState<boolean>(false);
    const navigate = useNavigate();

    async function onSubmit(data:recoverPasswordForm){
        try {
            await useService.recoverPassword(data)
            setSent(true)
        }catch (e){
            console.log(e)
            // attendError("Could not sent recover code.",e);
       }
    }

    return (<>
        {!sent?
            <div
                className="w-1/3 mx-auto mt-20 rounded-lg bg-stormd/[0.9] flex flex-col justify-center border-2 border-polard">
                <h1 className="text-center text-4xl font-semibold font-sans text-polar mt-10">{i18n.t('WillSendMail')}</h1>


                <form id="emailForm" onSubmit={handleSubmit(onSubmit)}>
                    <div className="flex flex-col items-center justify-center mt-5">
                        <div className="flex flex-col mb-10 items-center justify-center">

                            <div className="mb-5">
                                <label htmlFor="email" className="text-center text-2xl font-bold font-sans text-polar my-2">{i18n.t('EmailInput')}</label>
                            </div>
                            <div>
                                <input
                                    type="email"
                                    id="email"
                                    placeholder={i18n.t('email')!}
                                    className="p-2 m-2 rounded-lg"
                                    {...register("email",{required: true})}
                                />
                                {errors && errors.email && <span className="text-red-500">{errors.email.message}</span>}
                            </div>
                        </div>
                        <div>
                            <button className="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg">
                                {i18n.t('continue')}
                            </button>
                        </div>
                    </div>
                    <div>
                    </div>
                </form>
            </div>
            :
            <div
                className="w-1/3 mx-auto mt-20 rounded-lg bg-stormd/[0.9] flex flex-col justify-center border-2 border-polard">
                <h1 className="mt-10 text-center text-4xl font-semibold font-sans text-polar">
                    {i18n.t('CheckEmail')}</h1>
                <h2 className="mt-10 text-center text-xl font-sans text-polar">{i18n.t('InstructionsSent')}</h2>

                <Link className="flex w-full justify-center mt-5 mb-10" to={'/'}>
                    <div
                        className="w-1/7 rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
                        {i18n.t('home')}
                    </div>
                </Link>
            </div>
        }
    </>

    );
};

export default Index;
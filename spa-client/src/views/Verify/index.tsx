import React from 'react';
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {useSearchParams} from "react-router-dom";
import {toast} from "react-toastify";
import {attendError} from "../../common/utils/utils";
import i18n from "../../i18n";
type VerifyFormValues ={
    code:number
}

const Verify = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<VerifyFormValues>();
    const userService = useUserService();
    const [searchParams]= useSearchParams();


    async function onSubmit(data:VerifyFormValues){
        try{
            await userService.verifyUser(data.code, searchParams.get("user")!);
            toast.success("Successfully verified!");
        }catch (e){
            toast.error("Connection error. Please try again later "+ e);
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
                           {...register("code", {required: true})}
                    />
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
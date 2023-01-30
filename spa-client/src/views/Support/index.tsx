import React from 'react';
import {useForm} from "react-hook-form";
import useComplainService from "../../hooks/useComplainService";
import {useParams} from "react-router-dom";
import i18n from "../../i18n";

export interface CreateComplainForm {
    email: string,
    tradeId:number,
    message:string
}

type ContactFormProps = {
    tradeId: number
}

const Support= ({tradeId}:ContactFormProps) => {

    const { register, handleSubmit, formState: { errors } } = useForm<CreateComplainForm>( );
    const complainService = useComplainService();
    const params = useParams();


    function onSubmit(data:CreateComplainForm){
        complainService.createComplain(data);
    }

    return (
        <>
            <div className=" flex  flex-col justify-center mx-10">
                <div className="flex flex-col mt-10 mb-10 ">
                    <h1 className="text-center text-4xl font-semibold font-sans text-polar">
                        {i18n.t('needHelp')}
                    </h1>
                    <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                        {i18n.t('contactUs')}
                    </h3>
                </div>
            </div>
            <div className="flex justify-center">
                <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                    <input type="hidden" value={params.id} {...register("tradeId")}/>
                    <div className="flex flex-col p-5 justify-center">
                        <div className="flex-row justify-center">
                            <input type="email" className="min-w-full h-10 justify-center rounded-lg p-2" placeholder="Email" {...register("email", {required:"Email is required."})}/>
                            {errors && errors.email && <span className="text-red-500">{errors.email.message}</span>}
                        </div>
                    </div>
                    <div className="flex flex-col p-5 ">
                        <div className="flex-row justify-center">
                            <textarea className="min-w-full h-32 rounded-lg mx-auto p-5"  placeholder="Message" {...register("message", {required:"Message is required."})}/>
                            {errors && errors.message && <span className="text-red-500">{errors.message.message}</span>}
                        </div>
                    </div>
                    <div className="flex flex-row p-5">
                        <button type="submit" className=" font-bold bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                            {i18n.t('send')}
                        </button>
                    </div>
                </form>
            </div>
        </>


);
};

export default Support;
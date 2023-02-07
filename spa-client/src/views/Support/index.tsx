import React, {useState} from 'react';
import {useForm} from "react-hook-form";
import useComplainService from "../../hooks/useComplainService";
import i18n from "../../i18n";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {AxiosError} from "axios";

export interface CreateComplainForm {
    email: string,
    tradeId:number,
    message:string
}

type ContactFormProps = {
    tradeId: number
}

const Support= ({tradeId}:ContactFormProps) => {

    const { register, handleSubmit, formState: { errors } } = useForm<CreateComplainForm>();
    const complainService = useComplainService();
    const params = useParams();
    const [backButton, setBackButton] = useState<boolean>(false);
    const navigate = useNavigate();

    async function onSubmit(data:CreateComplainForm){
        try {
            await complainService.createComplain(data);
            toast("Your complaint was saved. As short as possible, we will have an answer.")
            setBackButton(true);
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error("Connection error");
        }
    }

    return (
        <>
            {!backButton && <div className=" flex  flex-col justify-center mx-10">
                <div className="flex flex-col mt-10 mb-10 ">
                    <h1 className="text-center text-4xl font-semibold font-sans text-polar">
                        {i18n.t('needHelp')}
                    </h1>
                    <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                        {i18n.t('contactUs')}
                    </h3>
                </div>
            </div>}
            <div className="flex justify-center">
                {!backButton && <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                    <input type="hidden" value={params.id} {...register("tradeId")}/>
                    <div className="flex flex-col p-5 ">
                        <div className="flex-row justify-center">
                            <textarea className="min-w-full h-32 rounded-lg mx-auto p-5"  placeholder={i18n.t('message')!} {...register("message", {required:"Message is required."})}/>
                            {errors && errors.message && <span className="text-red-500">{errors.message.message}</span>}
                        </div>
                    </div>
                    <div className={"flex flex-row mx-auto"}>
                        <div className="flex flex-row p-5">
                            <button onClick={()=>navigate(-1)} className=" font-bold bg-gray-500 text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                                {i18n.t('back')}
                            </button>
                        </div>
                        <div className="flex flex-row p-5">
                            <button type="submit" className=" font-bold bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                                {i18n.t('send')}
                            </button>
                        </div>

                    </div>

                </form>}
                {backButton && <div>
                    <div className="flex flex-col mt-10 mb-10 ">
                        <h1 className="text-center text-4xl font-semibold font-sans text-polar">
                            {i18n.t('teamWorking')}
                        </h1>
                        <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                            {i18n.t('RememberSeller')}
                        </h3>
                        <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                            {i18n.t('DontWorry')}
                        </h3>
                    </div>
                    <div className="flex flex-row p-5">
                        <button onClick={()=>navigate(-1)} className=" font-bold bg-gray-500 text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                            {i18n.t('back')}
                        </button>
                    </div>
                </div>}
            </div>
        </>


);

};

export default Support;
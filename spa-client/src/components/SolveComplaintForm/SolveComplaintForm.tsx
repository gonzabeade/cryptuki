import React from 'react';
import {useForm} from "react-hook-form";
import { InformationCircleIcon} from "@heroicons/react/24/outline";
import useComplainService from "../../hooks/useComplainService";
import {useNavigate} from "react-router-dom"
import i18n from "../../i18n";
import {toast} from "react-toastify";
import {AxiosError} from "axios";

export interface SolveComplaintFormModel {
    comments:string,
    resolution: string
    complainId: number
}

type props = {
    other:string,
    resolution:string,
    complainId: number
}


const SolveComplaintForm = ({other,resolution,complainId}:props) => {
    const complainService = useComplainService();
    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm<SolveComplaintFormModel>();


    async function onSubmit(data:SolveComplaintFormModel) {
        try {
            await complainService.createComplainResolution(data);
            toast.success("The complaint was solved.");
            navigate("/admin");
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
        <div className="w-full flex flex-col mt-5">
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="flex flex-row bg-white shadow rounded-lg p-3 font-sans font-bold">
                    <InformationCircleIcon className="w-5 h-5 mr-4 my-auto "/>
                    <p>
                        {resolution==="DISMISS" && ` Estás por desestimar la denuncia de ${other}. `}
                        {resolution==="KICK" && ` Estás por banear a ${other}. ` }
                        {i18n.t('irreversibleAction')} </p>
                </div>
                <input type="string" className="min-w-full h-32 rounded-lg mx-auto p-5 mt-5"
                       step=".01"
                       {...register("comments", {required:"You must comment your resolution"})}
                />
                {errors && errors.comments && <p className="text-red-600 mx-auto mt-2">{errors.comments.message}</p> }
                <input type="hidden"  value={resolution} {...register("resolution")} />
                <input type="hidden"  value={complainId} {...register("complainId")} />
                <button type="submit" className="mt-3 w-1/5 mx-auto bg-frost rounded-lg text-white p-3">
                    {i18n.t('send')}
                </button>
            </form>
        </div>
    );
};

export default SolveComplaintForm;
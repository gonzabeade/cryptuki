import React, {useState} from 'react';
import useKycService from "../../hooks/useKycService";
import {useForm} from "react-hook-form";
import {SolveKycForm} from "../KycAdminInformation/KycInformation";
import {useNavigate} from "react-router-dom";

import i18n from "../../i18n";

import {toast} from "react-toastify";
import {AxiosError} from "axios";


type SolveKycFormProps = {
    username:string,
}



const RejectKycForm = ({username}:SolveKycFormProps) => {
    const kycService = useKycService();
    const { register, handleSubmit , formState:{errors}} = useForm<SolveKycForm>();
    const [showPopup, setShowPopup] = useState<boolean>(true);
    const navigate = useNavigate();

    async function onSubmit(data:SolveKycForm) {
        try {
           await kycService.solveKyc(data,username);
           toast.success("This request was rejected.")
           navigate("/admin/kyc")
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError'));
        }
    }


return (
    <div className="bg-white flex flex-col justify-center">
        {showPopup && <div>
            <h2 className="text-2xl font-sans font-bold"> {i18n.t('rejectionMotif')} </h2>

        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="flex flex-col">
                    <textarea {...register("comments",{required:i18n.t('commentRequired')!})  }
                              className="border-2 border-gray-400 mt-10 h-[14rem] p-2" />
                {errors && errors.comments && <span className="text-red-500">{errors.comments.message}</span>}
                <input type="hidden" value="REJ" {...register("status")}/>
                <div className="flex flex-row mx-auto justify-center">
                    <button className="bg-frostdr rounded-lg text-white p-3 mr-10" type="submit"> {i18n.t('send')}
                    </button>
                    <p onClick={()=>setShowPopup(false)} className="bg-gray-400 rounded-lg text-white p-3 cursor-pointer"
                    > {i18n.t('cancel')}</p>
                </div>
            </div>
            <div>
            </div>
        </form></div>}
    </div>
);
};

export default RejectKycForm;
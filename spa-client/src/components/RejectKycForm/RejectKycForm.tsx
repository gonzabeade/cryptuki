import React, {useState} from 'react';
import useKycService from "../../hooks/useKycService";
import {useForm} from "react-hook-form";
import register from "../../views/Register";
import {SolveKycForm} from "../KycAdminInformation/KycInformation";


type SolveKycFormProps = {
    username:string,
}



const RejectKycForm = ({username}:SolveKycFormProps) => {
    const kycService = useKycService();
    const { register, handleSubmit , formState:{errors}} = useForm<SolveKycForm>();
    const [showPopup, setShowPopup] = useState<boolean>(true);

    function onSubmit(data:SolveKycForm) {
        kycService.solveKyc(data,username);
    }


return (
    <div className="bg-white flex flex-col justify-center">
        {showPopup && <div>
            <h2 className="text-2xl font-sans font-bold"> Escribí el motivo del rechazo </h2>

        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="flex flex-col">
                    <textarea {...register("comments",{required:"You must provide a comment"})  }
                              className="border-2 border-gray-400 mt-10 h-[14rem] p-2" />
                {errors && errors.comments && <span className="text-red-500">{errors.comments.message}</span>}
                <input type="hidden" value="REJ" {...register("status")}/>
                <div className="flex flex-row mx-auto justify-center">
                    <button className="bg-frostdr rounded-lg text-white p-3 mr-10" type="submit"> Enviar
                    </button>
                    <p onClick={()=>setShowPopup(false)} className="bg-gray-400 rounded-lg text-white p-3 cursor-pointer"
                    > Cancelar</p>
                </div>
            </div>
            <div>
            </div>
        </form></div>}
    </div>
);
};

export default RejectKycForm;
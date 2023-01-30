import React, {useState} from 'react';
import {KycInformationModel} from "../../types/KycInformationModel";
import {useForm} from "react-hook-form";
import useKycService from "../../hooks/useKycService";
import Popup from "reactjs-popup";
import {QuestionMarkCircleIcon} from "@heroicons/react/24/outline";
import AdviceOnP2P from "../AdviceOnP2P";
import RejectKycForm from "../RejectKycForm/RejectKycForm";
import {useNavigate} from "react-router-dom";
import {attendError} from "../../common/utils/utils";
import {toast} from "react-toastify";

type KycCardProp = {
    kyc:KycInformationModel,
    username: string
}
export type SolveKycForm = {
    comments: string,
    status:string
}


const KycInformation = ({kyc,username}:KycCardProp) => {
    const kycService = useKycService();
    const navigate = useNavigate();

    async function acceptKyc() {
        try {
            await kycService.solveKyc(
                {status:"APR",comments:"Bienvenido a cryptuki."}
                ,username);
            toast.success("Kyc request was successfully attended")
            navigate("/admin/kyc")
        }catch (e ){
            attendError("Error! You cannot attend this kyc request.",e)
        }

    }

    return (
        <div className="flex flex-col w-1/3 mx-auto mt-10">
            <div
                className="flex flex-col shadow-xl rounded-lg py-10 bg-[#FAFCFF] px-5 flex justify-center mx-auto w-3/4">
                <div className="flex flex-col">
                    <h3 className="font-sans font-bold text-lg mb-10 text-xl">Información provista por el usuario</h3>
                    <div className="flex flex-row">
                        <h4 className="font-sans font-bold mr-2">Nombre(s) dado(s)</h4>
                        <h3>{kyc.givenNames} </h3>
                    </div>
                    <div className="flex flex-row ">
                        <h4 className="font-sans font-bold mr-2">Apellido(s) </h4>
                        <h3>{kyc.surnames}</h3>
                    </div>
                    <div className="flex flex-row ">
                        <h4 className="font-sans font-bold mr-2">País de emisión</h4>
                        <h3>{kyc.emissionCountry}</h3>
                    </div>
                    <div className="flex flex-row">
                        <h4 className="font-sans font-bold mr-2"> Número de documento: </h4>
                        <h3>{kyc.idCode}</h3>
                    </div>
                    <div className="flex flex-row">
                        <h4 className="font-sans font-bold mr-2">Tipo de documento </h4>
                        <h3 className="mr-2">{kyc.idType}</h3>
                    </div>

                </div>
            </div>
            <div className="flex flex-row mx-auto mt-10">
                    <div>
                        <button className="bg-ngreen rounded-lg text-white p-3 mr-10" onClick={()=>acceptKyc()}>Aprobar</button>
                        <div>
                        </div>
                    </div>
                        <Popup  contentStyle={{borderRadius: "0.5rem" , padding:"1rem"}} trigger={<button className="bg-nred rounded-lg text-white p-3">
                            <p>Rechazar solicitud.</p>
                        </button>} position="center center" modal>
                            <RejectKycForm username={username}/>
                        </Popup>
         </div>
        </div>
    );
};

export default KycInformation;
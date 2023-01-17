import React, {useState} from 'react';
import {KycInformationModel} from "../../types/KycInformationModel";
import {useForm} from "react-hook-form";
import useKycService from "../../hooks/useKycService";

type KycCardProp = {
    kyc:KycInformationModel,
    username: string
}

export interface SolveKycForm {
    status: string
}

const KycInformation = ({kyc,username}:KycCardProp) => {
    const { register, handleSubmit } = useForm<SolveKycForm>();
    const kycService = useKycService();
    const [rejected, setRejected] = useState<boolean | null>(null);

    function onSubmit(data:SolveKycForm) {
        kycService.solveKyc(data,username);
    }



    return (
        <div className="flex flex-col w-1/3 mx-auto">
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
            {/*<div className="flex flex-row mx-auto mt-10">*/}
            {/*    {rejected==true && <h1>You are rejecting</h1>}*/}
            {/*    {rejected==false && <h1>You are approving</h1>}*/}
            {/*        <div>*/}
            {/*            <button className="bg-ngreen rounded-lg text-white p-3 mr-10" onClick={()=>setRejected(false)}>Aprobar</button>*/}
            {/*            <div>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*        <div>*/}
            {/*            <button className="bg-nred rounded-lg text-white p-3" onClick={()=>setRejected(true)}>Rechazar</button>*/}
            {/*            <div>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*        <form onSubmit={handleSubmit(onSubmit)}>*/}
            {/*            {rejected==true && <input type="hidden" value={"REJ"} {...register("status")}/>}*/}
            {/*            {rejected==false && <input type="hidden" value={"APR"} {...register("status")}/>}*/}
            {/*            <button className="mt-3 w-1/5 mx-auto bg-frost rounded-lg text-white p-3" type="submit">Confirmar</button>*/}
            {/*        </form>*/}
         {/*</div>*/}
        </div>
    );
};

export default KycInformation;
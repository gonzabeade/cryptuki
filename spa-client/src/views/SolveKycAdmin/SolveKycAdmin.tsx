import React, {useEffect, useState} from 'react';
import useKycService from "../../hooks/useKycService";
import {KycInformationModel} from "../../types/KycInformationModel";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
import KycInformation from "../../components/KycAdminInformation/KycInformation";

const SolveKycAdmin = () => {
    const kycService = useKycService();
    const [kyc,setKyc] = useState<KycInformationModel | null>();
    const params = useParams();
    const [idPhoto, setIdPhoto] = useState<boolean|null>(true);
    const [activeIdPhoto, setActiveIdPhoto ] = useState<string>("idPhoto");


    useEffect(()=>{
        if(params.username)
            getKycInformation(params.username)
    },[])

    async function getKycInformation(username:string){
        try{
            const apiCall = await kycService?.getKycInformation(username);
            setKyc(apiCall);
        }catch (e){
            toast.error("Connection error. Failed to fetch kyc information")
        }
    }

    return (
        <div className="flex w-full my-10 z-10">
            <div className="flex flex-col w-3/5">
                <div className="flex flex-row w-full h-[60px]">
                    <div className="w-1/2 h-full mx-2 shadow-l rounded-lg bg-[#FAFCFF] hover:bg-gray-300 cursor-pointer"
                         onClick={()=>{setIdPhoto(true); }}>
                        <h2 id="idphotoText" onClick={()=>setActiveIdPhoto("idPhoto")}
                            className={`underline-offset-2 font-sans text-xl font-bold text-center mt-4 ${activeIdPhoto === "idPhoto" ? " underline ":""}`} >Foto
                            del frente del documento </h2>
                    </div>
                    <div className="underline-offset-2 w-1/2 h-full mx-2 shadow-l rounded-lg bg-[#FAFCFF] hover:bg-gray-300 cursor-pointer"
                         onClick={()=>{setIdPhoto(false)}}>
                        <h2 id="validationphotoText" onClick={()=>setActiveIdPhoto("validationPhoto")}
                            className={`underline-offset-2 font-sans text-xl font-bold text-center mt-4 ${activeIdPhoto === "validationPhoto" ? " underline ":""}`}>Foto validatoria
                            con el documento </h2>
                    </div>
                </div>
                {kyc && <div className="w-full h-4/5 mt-5">
                    {idPhoto && <div id="idphoto" className="border-2 border-gray-400">
                        <img src={kyc.idPhoto} className=" w-full mx-auto"/>
                    </div> }
                    {!idPhoto && <div id="validationphoto" className="border-2 border-gray-400 hidden">
                        <img src={kyc.validationPhoto} className="w-full mx-auto"/>
                    </div>}
                </div> }
            </div>
            {kyc && params.username && <KycInformation kyc={kyc} username={params.username}/>}
        </div>

    );
};

export default SolveKycAdmin;
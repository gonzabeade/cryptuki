import React, {useEffect, useState} from 'react';
import KycPreview from "../../components/KycPreview/KycPreview";
import {toast} from "react-toastify";
import useKycService from "../../hooks/useKycService";
import UserModel from "../../types/UserModel";
import Paginator from "../../components/Paginator";
import {AttendError} from "../../common/utils/utils";

const KycLanding = () => {
    const kycService = useKycService();
    const [pendingKyc, setPendingKyc] = useState<UserModel[]|null>();
    const [actualPage, setActualPage] = useState<number>(1);
    const [totalPages, setTotalPages] = useState<number>(1);

    async function getPendingKycRequests(page?:number, pageSize?:number){
        try{
            const apiCall = await kycService?.getPendingKycInformation(page, pageSize);
            setPendingKyc(apiCall);
        }catch (e){
            AttendError("Connection error. Failed to fetch pending kyc requests",e)
        }

    }

    useEffect(  ()=>{
        getPendingKycRequests();
    }, []);


    return (
        <div className="flex flex-col ml-80 h-screen w-screen">
            <h1 className="font-sans text-4xl font-bold">Validar identidades</h1>
            <div className="flex flex-wrap w-full mt-3">
                <div className="flex flex-col bg-white shadow rounded-lg p-3 m-5 font-sans font-bold">
                      {pendingKyc && pendingKyc.map((user => <KycPreview username={user.username} last_login={user.lastLogin}/>))}
                      {/*{pendingKyc && pendingKyc.length > 0 &&  <Paginator totalPages={totalPages} actualPage={actualPage} callback={() => console.log("called")}/>}*/}
                      {/*TODO PAGINATOR*/}
                      {!pendingKyc && <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> No hay peticiones pendientes.</h1>}
                </div>
            </div>
        </div>
    );
};

export default KycLanding;
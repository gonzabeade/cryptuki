import React, {useEffect, useState} from 'react';
import KycPreview from "../../components/KycPreview/KycPreview";
import {toast} from "react-toastify";
import useKycService from "../../hooks/useKycService";
import UserModel from "../../types/UserModel";
import Paginator from "../../components/Paginator";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import Loader from "../../components/Loader";
import {attendError} from "../../common/utils/utils";

const KycLanding = () => {
    const kycService = useKycService();
    const [pendingKyc, setPendingKyc] = useState<UserModel[]|null>();
    const [loading, setLoading] = useState<boolean>(true);
    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
            actualPage: 0,
            totalPages: 0,
            nextUri:'',
            prevUri:''
        }
    );

    async function getPendingKycRequests(){
        try{
            const apiCall = await kycService?.getPendingKycInformation();
            setPendingKyc(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setLoading(false);
        }catch (e){
            attendError("Connection error. Failed to fetch pending kyc requests",e)
        }

    }
    async function getPaginatedKyc(url:string){
        try{
            setLoading(true);
            const apiCall = await kycService?.getPendingKycInformationByUrl(url);
            setPendingKyc(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setLoading(false);
        }catch (e){
            toast.error("Connection error. Failed to fetch pending kyc requests")
        }
    }


    useEffect(  ()=>{
        getPendingKycRequests();
    }, []);


    return ( <>
        <div>
            {loading ?
                <div className="flex flex-col w-2/3 mt-10">
                    <Loader/>
                </div> :
        <div className="flex flex-col ml-80 h-screen w-screen">
            <h1 className="font-sans text-3xl font-bold text-polar mt-10">Validar identidades</h1>
            <div className="flex flex-wrap w-full mt-3">
                <div className="flex flex-col bg-white shadow rounded-lg p-3 m-5 font-sans font-bold">
                      {pendingKyc && pendingKyc.map((user => <KycPreview key={user.userId} username={user.username} last_login={user.lastLogin}/>))}
                      {pendingKyc && pendingKyc.length > 0 &&  <Paginator paginatorProps={paginatorProps} callback={getPaginatedKyc}/>}
                      {!pendingKyc && <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> No hay peticiones pendientes.</h1>}
                </div>
            </div>
                </div>}
        </div>
        </>
    );
};

export default KycLanding;
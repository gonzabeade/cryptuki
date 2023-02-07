import React, {useEffect, useState} from 'react';
import {ComplainModel} from "../../types/ComplainModel";
import useComplainService from "../../hooks/useComplainService";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";
import ComplainCard from "../../components/ComplainCard";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import Paginator from "../../components/Paginator";
import i18n from "../../i18n";
import {AxiosError} from "axios";
import {useNavigate} from "react-router-dom";

const ComplaintHub = () => {
    const [complaints, setComplaints] = useState<ComplainModel[]|null>();
    const complainService = useComplainService();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
            actualPage: 0,
            totalPages: 0,
            nextUri:'',
            prevUri:''
        }
    );
    const navigate = useNavigate();
    async function getComplaints(){
        try{
            const apiCall = await complainService?.getComplaints();
            setComplaints(apiCall.items);
            if(apiCall.paginatorProps)
            setPaginatorProps(apiCall.paginatorProps!);
            setIsLoading(false);

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

    useEffect(()=>{
        getComplaints()
    },[]);

    return (<>
        <div className={"ml-72"}>
            <h1 className="font-sans text-3xl font-bold text-polar pt-10 pl-10 ">{i18n.t('pendingClaims')}</h1>
            {!isLoading ? <>

                <div className="flex flex-col divide-x h-full ">
                        {complaints &&
                            <div className="p-10 flex flex-wrap ">
                              {complaints.map(complaints=><ComplainCard complain={complaints} key={complaints.complainId}></ComplainCard>)}
                            </div>}
                        {complaints && complaints.length > 0 &&
                            <div className="flex flex-row mx-40 justify-center ">
                                <Paginator paginatorProps={paginatorProps} callback={getPaginatedComplaints}/>

                            </div>}
                        {(!complaints || complaints.length === 0) && <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> {i18n.t('noComplaints')}</h1>}
                </div>
            </>:
            <div className="flex flex-col ml-56">
                <Loader/>
            </div>
            }
        </div>
        </>
    );

    //Callback from Paginator component
    async function getPaginatedComplaints(uri:string){
        try{
            setIsLoading(true);

            const apiCall = await complainService?.getComplaintsByUrl(uri);
            setComplaints(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setIsLoading(false);

        }catch (e){
            toast.error(i18n.t('connectionError'));
        }
    }
};

export default ComplaintHub;
import React, {useEffect, useState} from 'react';
import {ComplainModel} from "../../types/ComplainModel";
import useComplainService from "../../hooks/useComplainService";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";
import ComplainCard from "../../components/ComplainCard";
import Paginator from "../../components/Paginator";

const complaintHub = () => {
    const [complaints, setComplaints] = useState<ComplainModel[]|null>();
    const complainService = useComplainService();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [actualPage, setActualPage] = useState<number>(1);
    const [totalPages, setTotalPages] = useState<number>(1);

    async function getComplaints(page?:number, pageSize?:number){
        try{
            const apiCall = await complainService?.getComplaints(page);
            setComplaints(apiCall);
            setIsLoading(false);
        }catch (e){
            toast.error("Connection error. Failed to fetch complaints")
        }
    }

    useEffect(()=>{
        getComplaints()
    },[]);

    return (<>
        <div>
            {!isLoading ? <>
            <div>
                {complaints && complaints.map(complaints=><ComplainCard complain={complaints}></ComplainCard>)}
                {complaints && complaints.length > 0 &&  <Paginator totalPages={totalPages} actualPage={actualPage} callback={() => console.log("called")}/>}
                {!complaints && <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> There are no pending complaints.</h1>}
            </div>
            </>:
            <div className="flex flex-col w-2/3 mt-10">
                <Loader/>
            </div>
            }
        </div>
        </>
    );
};

export default complaintHub;
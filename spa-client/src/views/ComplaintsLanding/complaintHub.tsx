import React, {useEffect, useState} from 'react';
import {ComplainModel} from "../../types/ComplainModel";
import useComplainService from "../../hooks/useComplainService";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";
import ComplainCard from "../../components/ComplainCard";
import Paginator from "../../components/Paginator";

const ComplaintHub = () => {
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
            console.log(e);
            toast.error("Connection error. Failed to fetch complaints")
        }
    }

    useEffect(()=>{
        getComplaints()
    },[]);

    return (<>
        <div className={"ml-72"}>
            <h1 className="font-sans text-3xl font-bold text-polar pt-10 pl-10 ">Atender reclamos</h1>
            {!isLoading ? <>

                <div className="flex flex-col divide-x h-full">
                        {complaints &&
                            <div className="p-10 flex flex-wrap ">
                              {complaints.map(complaints=><ComplainCard complain={complaints} key={complaints.complainId}></ComplainCard>)}
                            </div>}
                        {complaints && complaints.length > 0 &&
                            <div className="flex flex-row mx-40 justify-center ">
                                {/*<Paginator totalPages={totalPages} actualPage={actualPage} callback={() => console.log("called")}/>*/}
                                {/*TODO PAGINATOR*/}
                            </div>}
                        {!complaints && <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> There are no pending complaints.</h1>}
                </div>
            </>:
            <div className="flex flex-col ml-56">
                <Loader/>
            </div>
            }
        </div>
        </>
    );
};

export default ComplaintHub;
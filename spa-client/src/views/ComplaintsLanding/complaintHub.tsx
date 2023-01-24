import React, {useEffect, useState} from 'react';
import {ComplainModel} from "../../types/ComplainModel";
import useComplainService from "../../hooks/useComplainService";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";
import ComplainCard from "../../components/ComplainCard";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import Paginator from "../../components/Paginator";
import paginator from "../../components/Paginator";

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

    async function getComplaints(){
        try{
            const apiCall = await complainService?.getComplaints();
            setComplaints(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
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

                <div className="flex flex-col divide-x h-full">
                        {complaints &&
                            <div className="p-10 flex flex-wrap ">
                              {complaints.map(complaints=><ComplainCard complain={complaints} key={complaints.complainId}></ComplainCard>)}
                            </div>}
                        {complaints && complaints.length > 0 &&
                            <div className="flex flex-row mx-40 justify-center ">
                                <Paginator paginatorProps={paginatorProps} callback={getPaginatedComplaints}/>

                            </div>}
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

    //Callback from Paginator component
    async function getPaginatedComplaints(uri:string){
        try{
            setIsLoading(true);

            const apiCall = await complainService?.getComplaintsByUrl(uri);
            setComplaints(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setIsLoading(false);

        }catch (e){
            toast.error("Connection error. Failed to fetch paginated offers")
        }
    }
};

export default ComplaintHub;
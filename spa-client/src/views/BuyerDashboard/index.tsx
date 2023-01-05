import React, {useEffect, useState} from 'react';
import UserProfileCards from "../../components/UserProfileCards";
import StatusCards from "../../components/StatusCards/StatusCards";
import Paginator from "../../components/Paginator";
import TransactionModel from "../../types/TransactionModel";
import useTradeService from "../../hooks/useTradeService";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";

const BuyerDashboard = () => {
    const [trades, setTrades] = useState<TransactionModel[]>([]);
    const tradeService = useTradeService();
    const userService= useUserService();


    async function fetchTradesBuyerProfile(){
        try {
            const resp = await tradeService.getRelatedTrades(userService.getLoggedInUser(), 'all');
            if(resp.statusCode === 200){
                setTrades(resp.getData())
            }
        }catch (e){
            toast.error("Connection error. Failed to fetch trades");
        }


    }
    useEffect(()=>{fetchTradesBuyerProfile()});

    return (
        <div className="flex h-full w-full px-20 my-10">
            <div className="flex flex-col h-full mx-20 w-1/5">
                  <UserProfileCards username={"mdedeu"} phoneNumber={1234566} email={"mdedeu@itba.edu.ar"} rating={4.32} tradeQuantity={4}/>
            </div>
            {/*//  Middle Panel: trade */}
            <div className="flex flex-col h-full mr-20 w-3/5">
                <div
                    className="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-2xl font-bold font-sans text-polar">Trade Proposals</h1>
                </div>
                <StatusCards active={"pending"} base_url={"/buyer/mdedeu"} callback={() => console.log("a")}/>
                <div className="flex flex-col justify-center w-full mx-auto mt-10">
                    {trades.length >0 && trades.map((trade)=>{
                        return (
                            <></>
                        );
                    })}
                </div>
                {trades.length === 0 &&
                    <h2 className="text-center text-xl font-semibold font-sans text-polar mt-4">No transactions
                        available</h2>}
                {trades.length !== 0 &&
                    <div className="flex flex-col mt-3">
                        <Paginator totalPages={10} actualPage={1} callback={() => console.log("change page")}/>
                    </div>}

            </div>

    </div>
    );
};

export default BuyerDashboard;
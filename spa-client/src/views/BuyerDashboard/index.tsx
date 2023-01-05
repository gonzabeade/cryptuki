import React, {useEffect, useState} from 'react';
import UserProfileCards from "../../components/UserProfileCards";
import StatusCards from "../../components/StatusCards/StatusCards";
import Paginator from "../../components/Paginator";
import TransactionModel from "../../types/TransactionModel";
import useTradeService from "../../hooks/useTradeService";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
import TradeBuyerCard from "../../components/TradeBuyerCard";

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
                {/*TODO Evaluar esto con lo de User model*/}
                  <UserProfileCards username={"mdedeu"} phoneNumber={1234566} email={"mdedeu@itba.edu.ar"} rating={4.32} tradeQuantity={4}/>
            </div>
            {/*//  Middle Panel: trade */}
            <div className="flex flex-col h-full mr-20 w-3/5">
                <div
                    className="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-2xl font-bold font-sans text-polar">Trade Proposals</h1>
                </div>
                {/*TODO hacer el get de los trades con el estado filtrao*/}
                <StatusCards active={"pending"} base_url={"/buyer/"} callback={() => console.log("a")}/>
                <div className="flex flex-col justify-center w-full mx-auto mt-10">
                    {trades.length >0 && trades.map((trade)=>{
                        return (
                            //TODO unseenMessages
                            <TradeBuyerCard trade={trade} unSeenMessages={1}/>
                        );
                    })}
                </div>
                {trades.length === 0 &&
                    <h2 className="text-center text-xl font-semibold font-sans text-polar mt-4">No transactions
                        available</h2>}
                {trades.length !== 0 &&
                    <div className="flex flex-col mt-3">
                        {/*TODO aca ver de hacer el fetch de pages available y actual page, tiene que volver de la query de get*/}
                        <Paginator totalPages={10} actualPage={1} callback={() => console.log("change page")}/>
                    </div>}

            </div>

    </div>
    );
};

export default BuyerDashboard;
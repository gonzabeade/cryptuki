import React, {useEffect, useState} from 'react';
import UserProfileCards from "../../components/UserProfileCards";
import StatusCards from "../../components/StatusCards/StatusCards";
import Paginator from "../../components/Paginator";
import TransactionModel from "../../types/TransactionModel";
import useTradeService from "../../hooks/useTradeService";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
import TradeBuyerCard from "../../components/TradeBuyerCard";
import UserModel from "../../types/UserModel";

const BuyerDashboard = () => {
    const [trades, setTrades] = useState<TransactionModel[]>([]);
    const tradeService = useTradeService();
    const userService= useUserService();
    const [user, setUser] = useState<UserModel>();
    const [actualPage, setActualPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    async function fetchTradesBuyerProfile(){
        try {
            const resp = await tradeService.getRelatedTrades(userService.getLoggedInUser());
            setTrades(resp);
        }catch (e){
            toast.error("Connection error. Failed to fetch trades");
        }
    }
    async function fetchTradesWithStatus(status:string){
        try {
            const resp = await tradeService.getTradesWithStatus(status, user?.username!);
            setTrades(resp);
        }catch (e) {
            toast.error("Couldn't fethc trades with status " + status);
        }
    }

    useEffect(()=>{
        fetchTradesBuyerProfile();
    },[]);

    async function fetchUserData(){
        try{
            const resp = await userService.getUser(userService.getLoggedInUser()!);
            setUser(resp);
        }catch (e) {
            toast.error("Connection error. Failed to fetch user data");
        }
    }

    useEffect(()=>{
        fetchUserData();
    },[]);

    return (
        <div className="flex h-full w-full px-20 my-10">
            <div className="flex flex-col h-full mx-20 w-1/5">
                  <UserProfileCards username={user? user.username: "Loading"} phoneNumber={user? user.phoneNumber : "Loading"} email={user? user.email:"loading"} rating={user? user.rating: 0} tradeQuantity={user? user.ratingCount:0}/>
            </div>
            {/*//  Middle Panel: trade */}
            <div className="flex flex-col h-full mr-20 w-3/5">
                <div
                    className="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-2xl font-bold font-sans text-polar">Trade Proposals</h1>
                </div>
                <StatusCards active={"PENDING"}  callback={fetchTradesWithStatus}/>
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
                        {/*TODO callback*/}
                        <Paginator totalPages={totalPages} actualPage={actualPage} callback={() => console.log("change page")}/>
                    </div>}

            </div>

    </div>
    );
};

export default BuyerDashboard;
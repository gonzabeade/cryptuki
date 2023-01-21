import React, {useEffect, useState} from 'react';
import ChatSnippet from "../../components/ChatSnippet";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";
import {Link, useParams} from "react-router-dom";
import useTradeService from "../../hooks/useTradeService";
import TransactionModel from "../../types/TransactionModel";
import useUserService from "../../hooks/useUserService";
import UserModel from "../../types/UserModel";
import {toast} from "react-toastify";

const SellerTrade = () => {
    const tradeService = useTradeService();
    const userService = useUserService();

    const params = useParams();
    const [trade, setTrade] = useState<TransactionModel>();
    const [counterPart, setCounterPart] = useState<UserModel>();

    async function fetchTrade(){
        const tradeId = params.id;
        try{
            const resp = await tradeService.getTradeInformation(Number(tradeId));
            setTrade(resp);
        }catch (e) {
            toast.error("Connection error. Couldn't fetch trade");
        }
    }
    async function fetchCounterPart(){
        if(trade){
            try{
                const resp = await userService.getUser(userService.getUsernameFromURI(trade.buyer));
                setCounterPart(resp);

            }catch (e) {
                toast.error("Connection error. Couldn't fetch counterpart");
            }
        }
    }

    useEffect(()=>{
        fetchTrade();
    },[])

    useEffect(()=>{
        fetchCounterPart();
    },[trade])

    return (
        <div className="flex flex-row mt-7">
            <ChatSnippet  counterPart={counterPart} tradeId={trade?.tradeId!}/>
            <div className="flex flex-col justify-center px-10">
                <div className="flex flex-col justify-center px-10">
                    <div
                        className="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between w-full mb-3 text-polar">
                        Offer Information
                    </div>
                    <OfferInformationForSeller trade={trade!} chat={false}/>
                </div>

            <div className="mx-auto">
                <Link to="/" className=" cursor-pointer  font-bold bg-frost px-6 py-3  rounded-lg text-white">
                    Home
                </Link>
            </div>
            </div>
        </div>

    );
};

export default SellerTrade;
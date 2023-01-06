import React, {useEffect, useState} from 'react';
import ChatSnippet from "../../components/ChatSnippet";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";
import {Link, useParams} from "react-router-dom";
import useTradeService from "../../hooks/useTradeService";
import TransactionModel from "../../types/TransactionModel";
import useUserService from "../../hooks/useUserService";
import UserModel from "../../types/UserModel";

const SellerTrade = () => {
    const tradeService = useTradeService();
    const userService = useUserService();

    const params = useParams();
    const [trade, setTrade] = useState<TransactionModel>();
    const [counterPart, setCounterPart] = useState<UserModel>();

    async function fetchTrade(){
        const tradeId = params.id;
        //TODO FETCH
    }
    async function fetchCounterPart(){
        //TODO FETCH
    }

    useEffect(()=>{
        fetchTrade();
    },[])

    useEffect(()=>{
        fetchCounterPart();
    },[])

    return (
        <div className="flex flex-row mt-7">
            <ChatSnippet  counterPart={counterPart} tradeId={trade?.tradeId!}/>
            <div className="flex flex-col justify-center px-10">
            <OfferInformationForSeller trade={trade!} chat={false}/>
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
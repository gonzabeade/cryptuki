import React from 'react';
import UserInfo from "../../components/UserInfo/index";
import Message from "../../components/Message";
import TradeStatusAlert from "../../components/TradeStatusAlert";
import {InformationCircleIcon} from "@heroicons/react/24/outline";
import OfferStatusCard from "../../components/OfferStatusCard/OfferStatusCard";

const BuyOffer = () => {
    return (
        <>
            <UserInfo username={"mdedeu"}
                      email={"mdedeu@itba.edu.ar"}
                      phone_number={"1234141"}
                      last_login={"ayer"}
                      trades_completed={3}
                      rating={4.9}
            />
            <Message left={false} content={"messi"}/>
            <div className="flex">
                <TradeStatusAlert icon={<InformationCircleIcon className={`h-10 w-10 text-[#816327]`}/>} color={"#EBCB8B"} accentColor={"#816327"} title={"Your trade proposal was sent to the seller!"} subtitle={"Wait for the seller to accept your trade proposal"}/>
            </div>
            <OfferStatusCard color={"#EBCB8B"} message={"Pending"} active={true}/>
        </>
    );
};

export default BuyOffer;
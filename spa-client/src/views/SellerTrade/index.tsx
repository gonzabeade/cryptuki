import React from 'react';
import ChatSnippet from "../../components/ChatSnippet";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";
import {Link} from "react-router-dom";

const SellerTrade = () => {
    // TODO fetch de la counterpart, trade Info from param
    return (
        <div className="flex flex-row mt-7">
            {/*//TODO Counterpart*/}
            {/*<ChatSnippet  counterPart={} tradeId={}/>*/}
            <div className="flex flex-col justify-center px-10">
            <OfferInformationForSeller trade={
                {
                    tradeId:1,
                    status:"APR",
                    buyingQuantity:1,
                    lastModified:new Date(),
                    buyer:"uri",
                    seller:"uri",
                    offer:"uri",
                    messages:"uri",
                    self:"uri"
                }
            } chat={false}/>
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
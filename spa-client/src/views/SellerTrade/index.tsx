import React from 'react';
import ChatSnippet from "../../components/ChatSnippet";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";
import {Link} from "react-router-dom";

const SellerTrade = () => {
    return (
        <div className="flex flex-row mt-7">
            <ChatSnippet  counterPart={{
                accessToken: "",
                refreshToken: "string",
                admin: false,
                email:"mdedeu@itba.edu.ar",
                phoneNumber:"1245311",
                username:"mdedeu",
                lastLogin:"online",
                trades_completed:1,
                rating:1.3,
                image_url:"/"
            }} tradeId={4}/>
            <div className="flex flex-col justify-center px-10">
            <OfferInformationForSeller trade={{
                status:'pending',
                buyer:{
                    accessToken: "",
                    refreshToken: "string",
                    admin: false,
                    email:"mdedeu@itba.edu.ar",
                    phoneNumber:"1245311",
                    username:"mdedeu",
                    lastLogin:"online",
                    trades_completed:1,
                    rating:1.3,
                    image_url:"/"
                },
                offer: {
                    cryptoCode:"BTC",
                    date:new Date(),
                    location:"Balvanera",
                    maxInCrypto:2,
                    minInCrypto:0.001,
                    offerId:1,
                    offerStatus:"PENDING",
                    unitPrice:1000000,
                    url:"/offer/1",
                    seller: {
                        accessToken: "",
                        refreshToken: "string",
                        admin: false,
                        email:"mdedeu@itba.edu.ar",
                        phoneNumber:"1245311",
                        username:"mdedeu",
                        lastLogin:"online",
                        trades_completed:1,
                        rating:1.3,
                        image_url:"/"
                    }
                },
                amount: 1000,
                id:1,
                date: new Date()
            }} chat={false}/>
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
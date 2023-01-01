import React from 'react';
import ChatSnippet from "../../components/ChatSnippet";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";

const SellerTrade = () => {
    return (
        <div className="flex flex-row mt-7">
            <ChatSnippet otherUserActive={true}/>
            {/*<OfferInformationForSeller trade={{ status:'pending', icon:<></>,*/}
            {/*    buyer:"mdedeu",*/}
            {/*    seller:"mdedeuS",*/}
            {/*    offer: {*/}
            {/*        cryptoCode:"BTC",*/}
            {/*        date:new Date(),*/}
            {/*        location:"Balvanera",*/}
            {/*        maxInCrypto:2,*/}
            {/*        minInCrypto:0.001,*/}
            {/*        offerId:1,*/}
            {/*        offerStatus:"pending",*/}
            {/*        unitPrice:1000000,*/}
            {/*        url:"/offer/1",*/}
            {/*        seller:"marquitos",*/}
            {/*        trades:"1"*/}
            {/*    }}} chat={false}/>*/}
        </div>
    );
};

export default SellerTrade;
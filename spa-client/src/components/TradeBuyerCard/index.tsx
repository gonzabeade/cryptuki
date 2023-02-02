import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import {toast} from "react-toastify";
import {TRADE_STATUS} from "../../common/constants";
import useUserService from "../../hooks/useUserService";
import {attendError} from "../../common/utils/utils";
import i18n from "../../i18n";

type TradeCardProp = {
    trade:TransactionModel
}

const TradeBuyerCard = ({trade}:TradeCardProp) => {

    const [offer, setOffer] = useState<OfferModel>();
    const offerService = useOfferService();
    const [unseenMessages, setUnseenMessages] = useState<number>();
    const userService = useUserService();
    useEffect(()=>{
        if(trade){
            if(userService.getUsernameFromURI(trade.buyer) === userService.getLoggedInUser()){
                setUnseenMessages(trade.qUnseenMessagesBuyer);
            }else{
                setUnseenMessages(trade.qUnseenMessagesSeller);
            }
        }
    },[trade]);

    async function fetchOffer(){
        //fetch with uri
        try{
            if(trade){
                const offerId = offerService.getOfferIdFromURI(trade?.offer);
                const resp = await offerService.getOfferInformation(Number(offerId));
                setOffer(resp);
            }
        }catch (e) {
            toast.error("Connection error. Couldn't fetch offer");
        }

    }
    useEffect(()=>{
        fetchOffer();
    },[trade])

    return (
        <div className="shadow-xl flex rounded-lg  py-5 px-12 bg-[#FAFCFF] mt-3 justify-start">
            <div className="w-3/4 flex flex-row">
                <div className="flex flex-col font-sans my-auto mr-5">
                    {trade.status === "PENDING" &&
                        <div className="bg-nyellow  w-40 text-white  text-center p-2">{i18n.t('PENDING')}</div>}
                    {trade.status === "REJECTED" &&
                        <div className="bg-nred/[0.6] w-40 text-white  text-center p-2">{i18n.t('REJECTED')}</div>}
                    {trade.status === "ACCEPTED" &&  <div className="bg-ngreen w-40 text-white text-center p-2">{i18n.t('ACCEPTED')}</div>
                    }
                    {
                        trade.status === "SOLD" && <div className="bg-gray-400 w-40 text-white text-center p-2">{i18n.t('SOLD')} </div>
                    }
                    {
                        trade.status === "DELETED" &&     <div className="bg-blue-400 w-40 text-white text-center p-2">{i18n.t('DELETED')}</div>
                    }
                </div>

                <div className=" flex flex-col font-sans justify-center mr-5">
                    {
                        trade.status === "SOLD" &&  <h1 className="font-sans">{i18n.t('youBoughtFor')}: </h1>
                    }
                    {
                        trade.status !== "SOLD" &&  <h1 className="font-sans">{i18n.t('youWouldPay')}: </h1>
                    }
                    <h3 className="font-sans font-semibold">{trade.buyingQuantity * (offer? offer.unitPrice: 1)} ARS</h3>
                </div>

                <div className="flex flex-col font-sans justify-center ">
                    <h1 className="font-sans">{i18n.t('onExchangeOf')}: </h1>
                    <div className="flex">
                        <h1 className="font-sans font-semibold mr-2">{parseFloat(String(trade.buyingQuantity / (offer ? offer.unitPrice : 1))).toFixed(2)} </h1>
                        <h1 className="font-sans font-semibold">{offer?.cryptoCode}</h1>
                        <img src={'/images/'+offer?.cryptoCode+'.png'} alt={offer?.cryptoCode} className="w-6 h-6 mx-auto"/>
                    </div>
                </div>

            </div>

            <div className="w-1/4 flex flex-row">
                <div className="flex my-auto ml-1">
                    {trade.status != 'SOLD' && trade.status != 'REJECTED' && trade.status !== 'DELETED' &&
                        <a className=" cursor-pointer bg-gray-200 text-polard hover:border-polard hover: border-2 p-2 h-8 justify-center rounded-md font-sans text-center w-36 my-auto" href={"/trade/"+trade.tradeId}>
                            {i18n.t('resumeTrade')}
                        </a>
                    }
                    {trade.status === "SOLD" &&
                        <a className=" cursor-pointer bg-gray-200 text-polard hover:border-polard hover: border-2 p-2 h-8 justify-center rounded-md font-sans text-center w-36 my-auto" href={"/trade/"+ trade.tradeId
                        +"/receipt"}>
                            {i18n.t('seeMore')}
                        </a>
                    }
            </div>

            <div className="ml-4 flex flex-row  align-middle my-auto font-sans">
                {unseenMessages !== 0 && trade.status !== TRADE_STATUS.Sold &&
                    <>
                        <a href={"/trade/" + trade.tradeId} className="flex flex-row cursor-pointer">
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 mx-auto" fill="none"
                                 viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                <path strokeLinecap="round" strokeLinejoin="round"
                                      d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>
                            </svg>
                            <div className="text-frost align-middle bg-frost rounded-full w-2 h-2"></div>
                        </a>
                        <div
                            className="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">
                            <p className="text-xs">
                                {unseenMessages}
                            </p>
                        </div>
                    </>

                }
        </div>
</div>

</div>
);
};

export default TradeBuyerCard;
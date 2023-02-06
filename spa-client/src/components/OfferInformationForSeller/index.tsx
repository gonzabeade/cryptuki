import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import RatingStars from "../RatingStars";
import ChatButton from "../ChatButton";
import OfferModel from "../../types/OfferModel";
import UserModel from "../../types/UserModel";
import useOfferService from "../../hooks/useOfferService";
import {set} from "react-hook-form";
import {toast} from "react-toastify";
import useUserService from "../../hooks/useUserService";
import useTradeService from "../../hooks/useTradeService";
import {TRADE_STATUS} from "../../common/constants";
import i18n from "../../i18n";
import {Link} from "react-router-dom";

type OfferInformationForSellerProps = {
    trade:TransactionModel,
    chat:boolean,
    callback?:Function
}
const OfferInformationForSeller: React.FC<OfferInformationForSellerProps>= ({trade, chat, callback}) => {
    const [tradeStatus, setTradeStatus] = useState<string>();
    const [offer, setOffer] = useState<OfferModel>();
    const [buyer, setBuyer] = useState<UserModel>();
    const offerService = useOfferService();
    const userService = useUserService();
    const tradeService = useTradeService();
    const [unSeenMessages, setUnseenMessages]= useState<number>(0);

    async function fetchBuyer(){
        try{
            if(trade){
                const resp = await userService.getUser(userService.getUsernameFromURI(trade?.buyer));
                setBuyer(resp);
            }
        }catch (e) {
            toast.error("Connection error. Couldn't fetch buyer " + e);
        }
    }

    useEffect(()=>{
        fetchBuyer();
    },[trade])

    async function fetchOffer(){
        if(trade){
            const offerId = offerService.getOfferIdFromURI(trade?.offer);
            try{
                const resp = await offerService.getOfferInformation(Number(offerId));
                setOffer(resp);
            }catch (e) {
                toast.error("Connection error. Couldn't fetch offer "+e);
            }
        }
    }
    async function getUnseenMessages(){
        if(trade){
            if(userService.getUsernameFromURI(trade.buyer) === userService.getLoggedInUser()){
                setUnseenMessages(trade.qUnseenMessagesBuyer);
            }else{
                setUnseenMessages(trade.qUnseenMessagesSeller)
            }
        }
    }
    useEffect(()=>{
        fetchOffer();
        getUnseenMessages();
        setTradeStatus(trade?.status);
    },[trade])



    async function changeStatus(status:string, tradeId:number){
        try{
            const resp = await tradeService.changeTradeStatus(tradeId, status);
            setTradeStatus(status);
            if(callback){
                callback(status);
            }
        }catch (e) {
            toast.error("Connection error. Couldn't change trade status" + e);
        }
    }

    return (


            <div className="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mb-12  mx-2">
                <div className="flex font-sans h-fit w-full mt-2">
                    {
                        tradeStatus === TRADE_STATUS.Sold &&
                        <div className="font-semibold bg-gray-400 w-full text-white text-center p-2 rounded-lg">
                            {i18n.t('SOLD')}
                        </div>
                    }
                    {
                        tradeStatus === TRADE_STATUS.Pending &&
                        <div className=" font-semibold bg-nyellow  w-full text-white text-center p-2 rounded-lg">
                            {i18n.t('PENDING')}
                        </div>
                    }
                    {
                        tradeStatus === TRADE_STATUS.Rejected &&
                        <div className=" font-semibold bg-nred/[0.6] w-full text-white  text-center p-2 rounded-lg">
                            {i18n.t('REJECTED')}
                        </div>
                    }
                    {
                        tradeStatus === TRADE_STATUS.Accepted &&
                        <div className=" font-semibold bg-ngreen  w-full text-white  text-center p-2 rounded-lg">
                            {i18n.t('ACCEPTED')}
                        </div>
                    }
                    {
                        tradeStatus === TRADE_STATUS.Deleted &&
                        <div className=" font-semibold bg-blue-400  w-full text-white  text-center p-2 rounded-lg">
                            {i18n.t('DELETED')}
                        </div>
                    }
                </div>

                <div className="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                    <h1 className="mx-auto my-auto flex flex-row justify-around">
                        {offer &&  <p className={"text-polar text-lg font-bold"}>   {(trade?.buyingQuantity / offer?.unitPrice) + ' ' + offer?.cryptoCode}</p>}
                        <p className={"mx-2"}>⟶</p>
                        <p  className={"text-polar text-lg font-bold"}>  {trade?.buyingQuantity} ARS</p>

                    </h1>
                </div>

                <div className="flex flex-col my-2">
                    <h1 className="font-bold font-roboto text-polar mx-auto text-center">{i18n.t('buyer')}:</h1>
                    <div className="flex mx-auto">
                        <h1 className=" text-lg font-sans text-center text-polar">
                            {buyer?.username}
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="font-sans text-center text-gray-400 text-md">
                            {buyer?.email}
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="text-xs text-gray-400 font-sans font-semibold text-center">
                            {buyer?.phoneNumber}
                        </h1>
                    </div>
                    { buyer && buyer.ratingCount > 0 &&  <RatingStars rating={buyer? buyer.rating/2: 0}/>}

                </div>

                {tradeStatus === TRADE_STATUS.Sold &&
                    <Link className="mx-auto bg-gray-200  font-bold cursor-pointer text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40"
                       to={"/trade/" + trade.tradeId + "/receipt"}>
                        {i18n.t('seeMore')}
                    </Link>
                }
                {tradeStatus === TRADE_STATUS.Pending &&
                    <div className="flex flex-row">
                        <div className="flex justify-center mx-auto my-3">
                            <button
                                    className="font-bold bg-red-400 text-white p-3  rounded-lg font-sans mr-4" onClick={()=>changeStatus(TRADE_STATUS.Rejected, trade?.tradeId!)}>
                                {i18n.t('rejectTrade')}
                            </button>
                        </div>

                        <div className="flex justify-center mx-auto my-3">
                            <button
                                    className="font-bold bg-ngreen text-white p-3 rounded-lg font-sans " onClick={()=>changeStatus(TRADE_STATUS.Accepted, trade?.tradeId!)}>
                                {i18n.t('acceptTrade')}
                            </button>
                        </div>
                    </div>
                }

                {
                    tradeStatus === TRADE_STATUS.Accepted &&


                    <form className="flex justify-center mx-auto my-3">
                        <button type="submit"
                                className="font-bold w-fit bg-gray-500 text-white p-3 rounded-lg font-sans mx-auto" onClick={()=>changeStatus(TRADE_STATUS.Sold, trade?.tradeId)}>
                            {i18n.t('markAsSold')}
                        </button>
                    </form>
                }
                {
                    tradeStatus !== TRADE_STATUS.Accepted && tradeStatus !== TRADE_STATUS.Pending &&
                    <div className="flex h-2/5 my-2"/>
                }
                {
                    chat && <ChatButton tradeId={trade?.tradeId} unSeenMessages={unSeenMessages} />
                }
            </div>

    );
};

export default OfferInformationForSeller;
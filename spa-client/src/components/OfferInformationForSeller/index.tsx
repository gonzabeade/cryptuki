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

type OfferInformationForSellerProps = {
    trade:TransactionModel,
    chat:boolean
}
const OfferInformationForSeller: React.FC<OfferInformationForSellerProps>= ({trade, chat}) => {
    const [tradeStatus, setTradeStatus] = useState<string>(trade.status);
    const [offer, setOffer] = useState<OfferModel>();
    const [buyer, setBuyer] = useState<UserModel>();
    const offerService = useOfferService();
    const userService = useUserService();
    const tradeService = useTradeService();

    async function fetchBuyer(){
        try{
            console.log(trade.buyer);
            const resp = await userService.getUser(userService.getUsernameFromURI(trade.buyer));
            setBuyer(resp);
        }catch (e) {
            toast.error("Connection error. Couldn't fetch buyer");
        }
    }

    useEffect(()=>{
        fetchBuyer();
    },[])

    async function fetchOffer(){
        //fetch offer from offer? . Split to get offer id or get directly
        console.log(trade.offer);
        const offerId = trade.offer.split("/")[4];
        try{
            const resp = await offerService.getOfferInformation(Number(offerId));
            setOffer(resp);
        }catch (e) {
            toast.error("Connection error. Couldn't fetch offer");
        }

    }

    useEffect(()=>{
        fetchOffer();
    },[])


    async function changeStatus(status:string, tradeId:number){
        try{
            const resp = await tradeService.changeTradeStatus(tradeId, status);
            setTradeStatus(status);
        }catch (e) {
            toast.error("Connection error. Couldn't change trade status");
        }
    }

    return (
        <div className="flex flex-col justify-center px-10">
            <div
                className="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between w-full mb-3 text-polar">
               Offer Information
            </div>
            <div className="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mb-12 ">
                <div className="flex font-sans h-fit w-full mt-2">
                    {
                        tradeStatus === 'SOLD' &&
                        <div className="font-semibold bg-gray-400 w-full text-white text-center p-2 rounded-lg">
                           Sold
                        </div>
                    }
                    {
                        tradeStatus === 'PENDING' &&
                        <div className=" font-semibold bg-nyellow  w-full text-white text-center p-2 rounded-lg">
                            Pending
                        </div>
                    }
                    {
                        tradeStatus === 'REJECTED' &&
                        <div className=" font-semibold bg-nred/[0.6] w-full text-white  text-center p-2 rounded-lg">
                            Rejected
                        </div>
                    }
                    {
                        tradeStatus === 'ACCEPTED' &&
                        <div className=" font-semibold bg-ngreen  w-full text-white  text-center p-2 rounded-lg">
                            Accepted
                        </div>
                    }
                </div>

                <div className="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                    <h1 className="mx-auto">
                        {trade.buyingQuantity + ' ' + offer?.cryptoCode}
                        ⟶ {trade.buyingQuantity * (offer? offer.unitPrice: 1)} ARS
                    </h1>
                </div>

                <div className="flex flex-col my-2">
                    <h1 className="font-bold font-roboto text-polar mx-auto text-center">Buyer:</h1>
                    <div className="flex mx-auto">
                        <h1 className=" text-lg font-sans font-semibold text-center">
                            {buyer?.username}
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="font-sans font-semibold text-center text-gray-400">
                            {buyer?.email}
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="text-xs text-gray-400 font-sans font-semibold text-center">
                            {buyer?.phoneNumber}
                        </h1>
                    </div>
                    <RatingStars rating={buyer? buyer.rating/2: 0}/>
                </div>

                {tradeStatus === 'SOLD' &&
                    <a className="mx-auto bg-gray-200  font-bold cursor-pointer text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40"
                       href="/support">
                        Help
                    </a>
                }
                {tradeStatus === 'PENDING' &&
                    <div className="flex flex-row">
                        <form method="post" className="flex justify-center mx-auto my-3">
                            <button type="submit"
                                    className="font-bold bg-red-400 text-white p-3  rounded-lg font-sans mr-4" onClick={()=>changeStatus('REJECTED', trade.tradeId)}>
                                Reject
                            </button>
                        </form>

                        <form className="flex justify-center mx-auto my-3">
                            <button type="submit"
                                    className="font-bold bg-ngreen text-white p-3 rounded-lg font-sans " onClick={()=>changeStatus('ACCEPTED', trade.tradeId)}>
                                Accept
                            </button>
                        </form>
                    </div>
                }

                {
                    tradeStatus === 'ACCEPTED' &&


                    <form className="flex justify-center mx-auto my-3">
                        <button type="submit"
                                className="font-bold w-fit bg-gray-500 text-white p-3 rounded-lg font-sans mx-auto" onClick={()=>changeStatus('SOLD', trade.tradeId)}>
                            Mark as sold
                        </button>
                    </form>
                }
                {
                    tradeStatus !== 'ACCEPTED' && tradeStatus !== 'PENDING' &&
                    <div className="flex h-2/5 my-2"/>
                }
                {
                    chat && <ChatButton tradeId={trade.tradeId} />
                }
            </div>
        </div>

    );
};

export default OfferInformationForSeller;
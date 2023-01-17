import React, {useEffect, useState} from 'react';
import OfferModel from "../../types/OfferModel";
import {TradeModel} from "../../types/TradeModel";
import {ComplainModel} from "../../types/ComplainModel";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
import useOfferService from "../../hooks/useOfferService";
import useComplainService from "../../hooks/useComplainService";
import useTradeService from "../../hooks/useTradeService";
import TradeAndComplaintInformation from "../../components/TradeAndComplaitInformation/TradeAndComplaintInformation";
import ChatMessagesForAdmin from "../../components/ChatMessagesForAdmin/ChatMessagesForAdmin";
import UserModel from "../../types/UserModel";
import useUserService from "../../hooks/useUserService";
import SolveComplaintForm from "../../components/SolveComplaintForm/SolveComplaintForm";
import {XCircleIcon} from "@heroicons/react/24/outline";


const SolveComplaint = () => {
    const params = useParams();
    const [offer,setOffer] = useState<OfferModel|null>();
    const [trade,setTrade] = useState<TradeModel|null>();
    const [complaint,setComplaint] = useState<ComplainModel|null>();
    const [seller,setSeller] = useState<UserModel|null>();
    const [complainer,setComplainer] = useState<UserModel|null>();
    const [other,setOther] = useState<UserModel|null>();
    const [buyer,setBuyer] = useState<UserModel|null>();
    const offerService = useOfferService();
    const complainService = useComplainService();
    const tradeService = useTradeService();
    const userService = useUserService();
    const [banning,setBanning] = useState<boolean|null>(false);
    const [dismissing, setDismissing] = useState<boolean|null>(false);


    async function getTradeMembers(){
        try{
            if(trade && complaint) {
                const buyer = await userService?.getUserByUrl(trade.buyer);
                setBuyer(buyer);
                const seller = await userService?.getUserByUrl(trade.seller);
                setSeller(seller);
                const complainer = await userService?.getUserByUrl(complaint.complainer);
                setComplainer(complainer);
                if(seller.username === complainer.username)
                    setOther(buyer);
                else setOther(seller);
            }
        }catch (e){
            toast.error("Connection error. Failed to fetch Trade members")
        }
    }


    async function getOffer(offerUrl: string){
        try{
                const resp = await offerService.getOfferInformationByUrl(offerUrl);
                setOffer(resp);
                return resp;
        }catch (e){
            toast.error("Connection error. Failed to fetch offer");
        }

    }

    async function getComplaint(complainId:number){
        try{
            const resp = await complainService.getComplaintById(complainId);
            setComplaint(resp);
            return resp;
        }catch (e){
            toast.error("Connection error. Failed to fetch complaint");
        }

    }
    async function getTrade(tradeUrl: string){
        try{
            const resp = await tradeService.getTradeInformationByUrl(tradeUrl);
            setTrade(resp);
            return resp;
        }catch (e){
            toast.error("Connection error. Failed to fetch Trade");
        }

    }

    async function getSeller(sellerUrl:string){
        try{
            const seller = await userService?.getUserByUrl(sellerUrl);
            setSeller(seller);
        }catch (e){
            toast.error("Connection error. Failed to fetch seller")
        }
    }

    useEffect(()=>{
        if(trade)
            getTradeMembers();
        },[complaint,trade])

    useEffect(()=>{
        if(params.id)
            getComplaint(Number(params.id));

    },[])

    useEffect(()=>{
        if(complaint)
            getTrade(complaint?.trade);
    },[complaint])

    useEffect(()=>{
        if(trade)
            getOffer(trade.offer);

    },[complaint,trade])

    useEffect(()=>{
        if(trade)
            getSeller(trade.seller);
    },[complaint,trade]);
    return (
        <div>
            <div className="flex flex-col  ml-72 mr-20">
                <div className="flex">
                    <div className="flex flex-col mt-10">
                        <h2 className="font-sans text-4xl font-boldfont-sans font-semibold text-5xl">
                            Reclamo # {complaint && complaint.complainId}
                        </h2>
                        <h2 className="font-sans font-medium text-polard text-2xl">
                            Efectuado el: {complaint && complaint.date.toString()}
                        </h2>
                    </div>
                </div>
            </div>
            <div className="ml-72 flex flex-row mt-10">
                {offer && trade && complaint && buyer && seller && complainer &&
                    <TradeAndComplaintInformation offer={offer} trade={trade} complain={complaint} buyer={buyer} complainer={complainer} seller={seller} />
                }
                {seller && trade && trade.tradeId && buyer &&
                        <ChatMessagesForAdmin seller={seller} tradeId={trade.tradeId} buyer={buyer}/>
                  }
                <div className="flex flex-col w-1/3 h-full justify-center ">
                    <div className="w-full rounded-lg bg-[#FAFCFF] mx-auto py-3 mb-5 text-center border-2 border-polard">
                        <p className="font-sans font-semibold font-polard text-xl">
                            Responde al reclamo de {complainer?.username}
                        </p>
                    </div>
                    <div className="flex flex-row mx-auto w-full text-center justify-around ">
                        <button id="dismissButton" onClick={()=>{setDismissing(true); setBanning(false);}} className="bg-ngreen rounded-lg text-white p-3"> Desestimar Reclamo</button>
                        <button id="kickoutButton" onClick={()=>{setBanning(true); setDismissing(false);}} className="bg-nred rounded-lg text-white p-3"> Banear a {other?.username}</button>
                    </div>
                    {banning && <div>
                        <div className="w-full flex justify-end py-2 cursor-pointer">
                            <XCircleIcon className="w-5 h-5 my-auto align-end" onClick={()=>setBanning(false)}/>
                        </div>
                        {other && other.username && complaint && <SolveComplaintForm other={other?.username} resolution={"KICK"} complainId={complaint.complainId}/>}
                    </div>   }
                    {dismissing && <div>
                        <div className="w-full flex justify-end py-2 cursor-pointer">
                            <XCircleIcon className="w-5 h-5 my-auto align-end" onClick={()=>setDismissing(false)}/>
                        </div>
                        {complainer && complainer.username && complaint && <SolveComplaintForm other={complainer?.username} resolution={"DISMISS"} complainId={complaint.complainId}/>}
                    </div>   }
                </div>
            </div>

        </div>
    );

};

export default SolveComplaint;
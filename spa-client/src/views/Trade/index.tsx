import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import ChatSnippet from "../../components/ChatSnippet";
import TradeStatusAlert from "../../components/TradeStatusAlert";
import { QuestionMarkCircleIcon} from "@heroicons/react/24/outline";
import Stepper from "../../components/Stepper";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import Popup from 'reactjs-popup';
import AdviceOnP2P from "../../components/AdviceOnP2P";
import 'reactjs-popup/dist/index.css';
import useTradeService from "../../hooks/useTradeService";
import {toast} from "react-toastify";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import UserModel from "../../types/UserModel";
import useUserService from "../../hooks/useUserService";

const Trade =  () => {

    const navigate = useNavigate();
    const params= useParams();
    const [trade, setTrade] = useState<TransactionModel>();
    const [offer, setOffer] = useState<OfferModel>();
    const [seller, setSeller] = useState<UserModel>();
    const tradeService = useTradeService();
    const offerService = useOfferService();
    const userService = useUserService();

    useEffect(()=>{
        // fetchSeller();
    },[])



    useEffect(()=>{
        fetchTrade(Number(params.id));
    },[])

    useEffect(()=>{
        fetchOffer();
    },[trade])

    async function fetchTrade(tradeId: number | null) {
        try{
            if(tradeId){
                const resp = await tradeService.getTradeInformation(tradeId);

                if(resp.status === 'SOLD'){
                    navigate('/trade/'+ resp.tradeId+ '/receipt');
                }
                setTrade(resp);
            }
        }catch (e) {
            toast.error("Connection error. Failed to fetch trade");
        }

    }
    async function takeBackProposal(){
        try{
            const resp = await tradeService.changeTradeStatus(trade?.tradeId!,"DELETED" );
            setTrade(resp);
        }catch (e) {
            toast.error("Connection error. Failed to take back proposal");
        }
    }

    async function fetchSeller(){
        try{
            const resp  = await  userService.getUser(trade?.seller!);
            setSeller(resp);
        }catch (e) {
                toast.error("Connection error. Failed to fetch seller")
        }

    }

    async function fetchOffer(){
        try{
            if(trade){
                const resp = await offerService.getOfferInformation(Number(offerService.getOfferIdFromURI(trade.offer)));
                setOffer(resp);
            }
        }catch (e) {
            console.log(e)
            toast.error("Connection error. Could not fetch offer");
        }
    }



    return (
        <div className="flex flex-row mx-5">
            <div className="w-3/5 flex flex-col justify-center">
                <TradeStatusAlert status={trade?.status!}/>
                <h1 className="text-polar text-xl text-center mx-auto my-1 font-lato font-bold">Buying process</h1>
                <Stepper active={0}/>
                <hr className="mt-4 mb-2"/>
                { trade && trade.status === 'ACCEPTED' && <Popup  contentStyle={{borderRadius: "0.5rem" , padding:"1rem"}} trigger={<button className="p-3 bg-frostdr text-white font-roboto font-bold mx-auto rounded-lg my-2 flex flex-row">
                    <QuestionMarkCircleIcon className={"text-white h-6 w-6 my-auto mr-1"}/>
                    <p>Advice on P2P trading</p>
                </button>} position="center center" modal>
                    <AdviceOnP2P/>
                </Popup>}

                <div className="flex flex-col shadow-2xl p-3 rounded-r-lg border-frostdr border-l-8 ">
                    <h1 className="text-xl text-polar mx-auto font-bold font-lato">Offer Information</h1>
                    <h1 className="text-lg text-polar mx-auto font-bold font-lato">Location</h1>
                    <h3 className="text-polar text-md mx-auto">{offer?.location}</h3>

                    <div className="flex flex-row justify-center mt-3 mx-auto my-auto">
                        <div className="flex flex-col mx-10 order-1" id="left">
                            <h1 className="text-center text-lg">
                               You pay
                            </h1>
                            <h1 className="text-center text-xl font-semibold text-polar">{trade?.buyingQuantity + ' '} ARS</h1>
                        </div>
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-10 w-10 my-5 order-2 mx-10" fill="none"
                             viewBox="0 0 24 24" stroke="black" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                        </svg>
                        <div className="flex flex-col mx-10 order-3" id="right">
                            <h1 className="text-center text-lg">
                                You receive
                            </h1>
                            <h1 className="text-center text-xl font-semibold text-polar">
                                { trade && offer ? trade.buyingQuantity / (offer.unitPrice) + ' '  + offer.cryptoCode: 'Loading' }
                            </h1>
                        </div>
                    </div>
                    <div className="flex justify-center mt-5">
                        <button className="h-fit bg-frost text-white p-3 font-sans rounded-lg w-40 text-center hover:bg-frostdr font-bold"  onClick={()=>navigate('/')}>Return to Home</button>

                        <button className="bg-gray-200 hover:bg-gray-300 text-polard p-3 font-sans rounded-lg mx-2 font-bold" onClick={()=>navigate('/support')}>I had a problem</button>
                        {
                            trade?.status === 'PENDING' &&
                            <form className="flex">
                                <button type="submit" className=" bg-nred hover:bg-nredd  text-white  p-3 h-12 justify-center rounded-lg font-sans text-center font-bold" onClick={takeBackProposal}>
                                    Take back trade proposal
                                </button>
                            </form>
                        }

                    </div>
                </div>
            </div>
            <div className="w-2/5">
                {/*//TODO disociate username from URI*/}
                <ChatSnippet counterPart={seller} tradeId={trade? trade.tradeId: 0}/>
            </div>

        </div>
    );
};

export default Trade;
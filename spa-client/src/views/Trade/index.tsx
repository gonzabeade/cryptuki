import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import ChatSnippet from "../../components/ChatSnippet";
import TradeStatusAlert from "../../components/TradeStatusAlert";
import {InformationCircleIcon} from "@heroicons/react/24/outline";
import Stepper from "../../components/Stepper";
import {useNavigate, useSearchParams} from "react-router-dom";
import Popup from 'reactjs-popup';
import AdviceOnP2P from "../../components/AdviceOnP2P";
import 'reactjs-popup/dist/index.css';
import useTradeService from "../../hooks/useTradeService";
import toast from "react-hot-toast";

const Trade =  () => {

    const navigate = useNavigate();
    const searchParams = useSearchParams();
    const [trade, setTrade] = useState<TransactionModel>();
    const tradeService = useTradeService();

    async function fetchTrade(tradeId: number) {
        const resp = await tradeService.getTradeInformation(tradeId);
        if(resp.statusCode === 200){
            setTrade(resp.getData);
        }else{
            toast.error("Error fetching trade")
        }
    }
    async function takeBackProposal(){
        //post to take back
    }

    useEffect(()=>{
       // const tradeId:string = searchParams.tradeId;
        fetchTrade(1);
    }, [trade])


    return (
        <div className="flex flex-row mx-5">
            <div className="w-3/5 flex flex-col justify-center">
                <TradeStatusAlert status={trade?.status}/>
                <h1 className="text-polar text-xl text-center mx-auto my-2 font-lato font-bold">Buying process</h1>
                <Stepper active={0}/>
                <hr className="mt-4 mb-2"/>
                { trade && trade.status === 'accepted' && <AdviceOnP2P/>}
                <Popup  contentStyle={{borderRadius: "0.5rem"}} trigger={<button className="p-3 bg-frostdr text-white font-roboto font-bold mx-auto rounded-lg my-2">Advice on P2P trading</button>} position="center center" modal>
                    <AdviceOnP2P/>
                </Popup>
                <div className="flex flex-col shadow-2xl p-3 rounded-r-lg border-frostdr border-l-8 ">
                    <h1 className="text-xl text-polar mx-auto font-bold font-lato">Offer Information</h1>
                    <h1 className="text-lg text-polar mx-auto font-bold font-lato">Location</h1>
                    <h3 className="text-polar text-md mx-auto">{trade?.offer.location}</h3>

                    <div className="flex flex-row justify-center mt-3 mx-auto my-auto">
                        <div className="flex flex-col mx-10 order-1" id="left">
                            <h1 className="text-center text-lg">
                               You pay
                            </h1>
                            <h1 className="text-center text-xl font-semibold text-polar">${trade?.amount} ARS</h1>
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
                                { trade && trade?.amount / trade?.offer.unitPrice}
                                {trade?.offer.cryptoCode}
                            </h1>
                        </div>
                    </div>
                    <div className="flex justify-center mt-5">
                        <button className="h-fit bg-frost text-white p-3 font-sans rounded-lg w-40 text-center hover:bg-frostdr"  onClick={()=>navigate('/')}>Return to Home</button>

                        <button className="bg-gray-200 hover:bg-gray-300 text-polard p-3 font-sans rounded-lg mx-2" onClick={()=>navigate('/contact')}>I had a problem</button>
                        {
                            trade?.status === 'pending' &&
                            <form className="flex">
                                <button type="submit" className=" bg-nred hover:bg-nredd  text-white  p-3 h-12 justify-center rounded-lg font-sans text-center" onClick={takeBackProposal}>
                                    Take back trade proposal
                                </button>
                            </form>
                        }

                    </div>
                </div>
            </div>
            <div className="w-2/5">
                <ChatSnippet otherUserActive={trade?.offer.seller.lastLogin === 'online'} counterPart={trade?.offer.seller} tradeId={trade? trade.id: 0}/>
            </div>

        </div>
    );
};

export default Trade;
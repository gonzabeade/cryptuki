import React from 'react';
import TransactionModel from "../../types/TransactionModel";
import ChatSnippet from "../../components/ChatSnippet";
import TradeStatusAlert from "../../components/TradeStatusAlert";
import {InformationCircleIcon} from "@heroicons/react/24/outline";
import Stepper from "../../components/Stepper";

type TradeProps = {
    trade:TransactionModel;
}
const Trade:React.FC<TradeProps> =  ({trade}) => {
    return (
        <div className="flex flex-row mx-5">
            <div className="w-3/5 flex flex-col justify-center">
                <TradeStatusAlert status={'pending'}/>
                <h1 className="text-polar text-xl text-center mx-auto my-2 font-lato font-bold">Buying process</h1>
                <Stepper active={0}/>

                <div className="flex flex-col shadow-lg p-3 mt-10 rounded-lg border-2 border-frostdr">
                    <h1 className="text-xl text-polar mx-auto font-bold font-lato">Offer Information</h1>
                    <h1 className="text-lg text-polar mx-auto font-bold font-lato">Location</h1>
                    <h3 className="text-polar text-md mx-auto">Balvanera</h3>

                    <div className="flex flex-row justify-center mt-3">
                        <div className="flex flex-col mx-10 order-1" id="left">
                            <h1 className="text-center text-lg">
                               You pay
                            </h1>
                            <h1 className="text-center text-xl font-semibold text-polar">10.000 ARS</h1>
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
                                quantity/price
                                BTC
                            </h1>
                        </div>
                    </div>
                    <div className="flex justify-center mt-5">
                        <button className="h-fit bg-frost text-white p-3 font-sans rounded-lg w-40 text-center hover:bg-frostdr" >Return to Home</button>

                        <button className="bg-gray-200 hover:bg-gray-300 text-polard p-3 font-sans rounded-lg mx-2" >I had a problem</button>

                        <form className="flex">
                            <button type="submit" className=" bg-nred hover:bg-nredd  text-white  p-3 h-12 justify-center rounded-lg font-sans text-center">
                                Take back trade proposal
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div className="w-2/5">
                <ChatSnippet/>
            </div>

        </div>
    );
};

export default Trade;
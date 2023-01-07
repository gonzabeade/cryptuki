import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import {Link, useParams} from "react-router-dom";
import RateYourCounterPart from "../../components/RateYourCounterPart";
import UserInfo from "../../components/UserInfo";
import useTradeService from "../../hooks/useTradeService";
import { toast } from 'react-toastify';


const Receipt = () => {

    const [trade, setTrade] = useState<TransactionModel>();
    const params = useParams();
    const tradeService = useTradeService();

    async function fetchTrade(tradeId:number){

        try{
            const resp = await tradeService.getTradeInformation(tradeId);
            setTrade(resp);
        }catch (e){
            toast.error("Error fetching trade");
            console.log("here")
        }
    }

    useEffect(()=>{
        fetchTrade(Number(params.id));
    },[])


    return (
        <>
            <div className="flex flex-row divide-x-2 divide-polard mt-5">
                <div className="flex flex-col w-3/5 h-screen">
                    <div className="flex flex-col mx-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" className=" mx-auto h-20 w-20" fill="none"
                             viewBox="0 0 24 24" stroke="#A3BE8C" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                        <h1 className="p-4 text-polard font-bold text-2xl font-sans mx-5 text-center ">
                            The exchange was successful.
                        </h1>
                        <h3 className="mx-auto text-polard text-center text-lg text-gray-500">
                            Thanks for using Cryptuki
                        </h3>
                    </div>
                    <div className="flex flex-col">
                        <h1 className="mx-auto text-polard font-bold text-xl mt-10 mb-5">
                            Transaction Data
                        </h1>
                        {/*TODO ver como switcheamos esto dependiendo del usuario que ve*/}
                        <div
                            className="py-5 px-14 mx-auto rounded-lg bg-white shadow flex  flex-col">
                            <div className="flex flex-row px-30">
                                <div className="flex flex-col">
                                    <h1 className=" text-center text-lg  font-lato text-polar font-bold text-lh">
                                        You paid
                                    </h1>

                                    <div className="flex flex-row">
                                        <h2 className="text-lg  font-lato text-polar text-left my-auto">110000.34 ARS
                                        </h2>
                                    </div>
                                </div>
                                <div className="mx-10 my-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none"
                                         viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                                    </svg>
                                </div>
                                <div className="flex flex-col">
                                    <h1 className="text-polar font-lato font-bold text-lg text-center">
                                        You received
                                    </h1>

                                    <div className="flex flex-row">
                                        <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                            0000.2
                                        </h2>
                                        <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                            BTC
                                        </h1>
                                    </div>
                                </div>
                            </div>
                            <div className="flex flex-col my-10 px-30">
                                <h4 className="text-md text-polar font-bold mx-auto">
                                    Transaction confirmation time
                                </h4>
                                <h2 className="text-lg font-roboto text-polar text-center my-auto ">
                                    {trade? trade.lastModified.toString(): 'No date provided'}
                                </h2>
                            </div>
                        </div>
                    </div>


                    <div className="flex flex-row mt-10">


                        <Link className="cursor-pointer font-semibold bg-frost text-white p-3 font-sans rounded-lg mx-auto  w-40 text-center"
                           to="/">
                            Home
                        </Link>
                        <Link className=" cursor-pointer font-semibold bg-nred text-white p-3 font-sans rounded-lg mx-auto w-40 text-center"
                           to="/support">
                            I had a problem
                        </Link>
                    </div>
                </div>

            <div className="flex flex-col w-2/5">
                <div className="flex flex-col mx-10 items-center">
                    {/*TODO ver como switcheamos con usuarios */}
                    <UserInfo username={"mdedeu"} email={"mdedeu@itba.edu.ar"} phone_number={"1245432"} last_login={"ayer"} trades_completed={12} rating={4.5}/>
                    <div className="flex flex-col mx-auto mt-10">
                        <RateYourCounterPart usernameRater={"mdedeu"} usernameRated={"messi"} tradeId={21}/>
                    </div>
                </div>
            </div>
            </div>
        </>
    )
};

export default Receipt;
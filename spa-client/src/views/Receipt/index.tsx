import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import {Link, useParams} from "react-router-dom";
import RateYourCounterPart from "../../components/RateYourCounterPart";
import UserInfo from "../../components/UserInfo";
import useTradeService from "../../hooks/useTradeService";
import { toast } from 'react-toastify';
import useUserService from "../../hooks/useUserService";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import UserModel from "../../types/UserModel";


const Receipt = () => {

    const [trade, setTrade] = useState<TransactionModel>();
    const params = useParams();
    const tradeService = useTradeService();
    const userService = useUserService();
    const [isBuyer, setIsBuyer] = useState<boolean>(true);
    const [offer, setOffer] = useState<OfferModel>();
    const offerService = useOfferService();
    const [counterPart, setCounterPart] = useState<UserModel>();

    async function fetchTrade(tradeId:number){
        try{
            const resp = await tradeService.getTradeInformation(tradeId);
            setTrade(resp);
        }catch (e){
            toast.error("Error fetching trade");
        }
    }

    useEffect(()=>{
        fetchTrade(Number(params.id));
    },[]);
    async function fetchBuyerOrSeller(){
        try{
            if(trade){
                //trade.buyer get URI
                if(trade.buyer === userService.getLoggedInUser()){
                    // fetch seller
                    // setCounterPart(trade.seller);
                    setIsBuyer(true);
                }else{
                    //fetch buyer
                    setIsBuyer(false);
                }
            }
        }catch (e){
            toast.error("Error fetching buyer or seller");
        }

    }

    useEffect(()=>{
        fetchBuyerOrSeller();
    }, [])

    async function getOffer(){
        if (trade) {
            //TODO get ID from trade.offer
            const resp =  await offerService.getOfferInformation(Number(trade.offer));
            setOffer(resp);
        }
    }

    useEffect(()=>{
        getOffer();
    }, [])


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
                        <div
                            className="py-5 px-14 mx-auto rounded-lg bg-white shadow flex  flex-col">
                            <div className="flex flex-row px-30">
                                <div className="flex flex-col">
                                    <h1 className=" text-center text-lg  font-lato text-polar font-bold text-lh">
                                        You paid
                                    </h1>

                                    <div className="flex flex-row">
                                        {isBuyer ?
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {offer?.unitPrice! * trade?.buyingQuantity!}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    ARS
                                                </h1>
                                            </>
                                            :
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {trade?.buyingQuantity}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    {offer?.cryptoCode}
                                                </h1>
                                            </>
                                           }
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
                                        {isBuyer ?
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {trade?.buyingQuantity}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    {offer?.cryptoCode}
                                                </h1>
                                            </>
                                            :
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {offer?.unitPrice! * trade?.buyingQuantity!}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    ARS
                                                </h1>
                                            </>
                                        }
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
                    <UserInfo username={counterPart?.username!} email={counterPart?.email!} phone_number={counterPart?.phoneNumber!} last_login={counterPart?.lastLogin.toString()!} trades_completed={counterPart?.ratingCount!} rating={counterPart?.rating!}/>
                    <div className="flex flex-col mx-auto mt-10">
                        <RateYourCounterPart usernameRater={userService.getLoggedInUser()!} usernameRated={counterPart?.username!} tradeId={trade?.tradeId!}/>
                    </div>
                </div>
            </div>
            </div>
        </>
    )
};

export default Receipt;
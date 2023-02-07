import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import {Link, useNavigate, useParams} from "react-router-dom";
import RateYourCounterPart from "../../components/RateYourCounterPart";
import UserInfo from "../../components/UserInfo";
import useTradeService from "../../hooks/useTradeService";
import useUserService from "../../hooks/useUserService";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import UserModel from "../../types/UserModel";
import {useAuth} from "../../contexts/AuthContext";
import i18n from "../../i18n";
import {AxiosError} from "axios";
import {toast} from "react-toastify";


const Receipt = () => {

    const [trade, setTrade] = useState<TransactionModel>();
    const params = useParams();
    const tradeService = useTradeService();
    const userService = useUserService();
    const [isBuyer, setIsBuyer] = useState<boolean>();
    const [offer, setOffer] = useState<OfferModel>();
    const offerService = useOfferService();
    const [counterPart, setCounterPart] = useState<UserModel>();
    const {user} = useAuth();
    const navigate = useNavigate();

    async function fetchTrade(tradeId:number){
        try{
            const resp = await tradeService.getTradeInformation(tradeId);
            setTrade(resp);
            if(resp.status !== 'SOLD')
                navigate("/trade/"+resp.tradeId)
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError'));
        }
    }

    useEffect(()=>{
        fetchTrade(Number(params.id));
    },[]);

    async function fetchBuyerOrSeller(){
        try{
            if(trade){
                let username:string;
                if(userService.getUsernameFromURI(trade.buyer) === user?.username){
                    username = userService.getUsernameFromURI(trade.seller);
                    setIsBuyer(true);
                }else{
                   username = userService.getUsernameFromURI(trade.buyer);
                   setIsBuyer(false);
                }

                const resp = await userService.getUser(username);
                setCounterPart(resp);
            }
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                if(e.status === 404)
                    navigate('/404');
                else toast.error(i18n.t("connectionError") + i18n.t('failedToFetch') + i18n.t('seller'));
            }
        }
    }

    useEffect(()=>{
        fetchBuyerOrSeller();
    }, [trade])

    async function getOffer(){
        if (trade) {
            const offerId = offerService.getOfferIdFromURI(trade.offer);
            const resp =  await offerService.getOfferInformation(Number(offerId));
            setOffer(resp);
        }
    }

    useEffect(()=>{
        getOffer();
    }, [trade])


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
                            {i18n.t("exchangeSuccessful")}
                        </h1>
                        <h3 className="mx-auto text-polard text-center text-lg text-gray-500">
                            {i18n.t('thankForUsingCryptuki')}
                        </h3>
                    </div>
                    <div className="flex flex-col">
                        <h1 className="mx-auto text-polard font-bold text-xl mt-10 mb-5">
                            {i18n.t('tradeDetails')}
                        </h1>
                        <div
                            className="py-5 px-14 mx-auto rounded-lg bg-white shadow flex  flex-col">
                            <div className="flex flex-row px-30">
                                <div className="flex flex-col">
                                    <h1 className=" text-center text-lg  font-lato text-polar font-bold text-lh">
                                        {i18n.t('youPaid')}
                                    </h1>

                                    <div className="flex flex-row">
                                        {isBuyer ?
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {offer && trade && (trade?.buyingQuantity!).toFixed(2)}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    ARS
                                                </h1>
                                            </>
                                            :
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {trade && offer && (trade?.buyingQuantity/offer.unitPrice).toFixed(14)}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    {offer && offer?.cryptoCode}
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
                                        {i18n.t('youReceived')}
                                    </h1>

                                    <div className="flex flex-row">
                                        {isBuyer ?
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    {trade && (trade?.buyingQuantity / offer?.unitPrice!).toFixed(14)}
                                                </h2>
                                                <h1 className="text-lg  font-lato text-polar text-left my-auto ml-2">
                                                    {offer && offer?.cryptoCode}
                                                </h1>
                                            </>
                                            :
                                            <>
                                                <h2 className="text-lg  font-lato text-polar text-left my-auto">
                                                    { offer && trade && (trade?.buyingQuantity!).toFixed(2)}
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
                                    {i18n.t('transactionDate')}
                                </h4>
                                <h2 className="text-lg font-roboto text-polar text-center my-auto ">
                                    {trade? trade.lastModified.toString().substring(0,10): 'No date provided'}
                                </h2>
                            </div>
                        </div>
                    </div>


                    <div className="flex flex-row mt-10">


                        <div className="cursor-pointer font-semibold bg-frost text-white p-3 font-sans rounded-lg mx-auto  w-40 text-center"
                           onClick={()=>navigate(-1)}
                              >
                            {i18n.t('back')}
                        </div>
                        <Link className=" cursor-pointer font-semibold bg-nred text-white p-3 font-sans rounded-lg mx-auto w-40 text-center"
                           to={"/trade/"+trade?.tradeId+"/support"}>
                            {i18n.t('iHadAProblema')}
                        </Link>
                    </div>
                </div>

            <div className="flex flex-col w-2/5">
                <div className="flex flex-col mx-10 items-center">
                    {counterPart &&  <UserInfo username={counterPart.username} email={counterPart.email} phone_number={counterPart.phoneNumber} last_login={counterPart.lastLogin.toString()} trades_completed={counterPart.ratingCount} rating={counterPart.rating}/>}
                    <div className="flex flex-col mx-auto mt-10">
                        <RateYourCounterPart usernameRated={counterPart?.username!} tradeId={trade?.tradeId!} isBuyer={isBuyer!}/>
                    </div>
                </div>
            </div>
            </div>
        </>
    )
};

export default Receipt;
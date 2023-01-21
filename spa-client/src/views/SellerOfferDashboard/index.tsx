import React, {useEffect, useState} from 'react';
import OfferCardProfile from "../../components/OfferCardProfile";
import OfferModel from "../../types/OfferModel";
import TransactionModel from "../../types/TransactionModel";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";
import useTradeService from "../../hooks/useTradeService";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import useOfferService from "../../hooks/useOfferService";
import {TRADE_STATUS} from "../../common/constants";
import Paginator from "../../components/Paginator";
import {PaginatorPropsValues} from "../../types/PaginatedResults";

const SellerOfferDashboard = () => {

    const [selectedStatus, setSelectedStatus] = useState<string>(TRADE_STATUS.All);
    const [trades, setTrades] = useState<TransactionModel[]>([]);
    const [offer, setOffer] = useState<OfferModel>();
    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
        actualPage: 0,
        totalPages: 0,
        nextUri:'',
        prevUri:''
    })
    const params = useParams();
    const tradeService = useTradeService();
    const offerService = useOfferService();
    const navigate = useNavigate();


    async function fetchTradesAssociatedWithOfferWithStatus(status:TRADE_STATUS, page?:number){
        try{
            const resp = await tradeService.getTradesWithOfferId(Number(params.id), status, page);
            setTrades(resp.items);
            setPaginatorProps(resp.paginatorProps!)
            setSelectedStatus(status);
        }catch (e){
            toast.error("Connection error. Couldn't fetch trades");
        }
    }

    async function fetchTradesAssociatedWithOffer(){
        try {
            if (params.id) {
                const resp = await tradeService.getTradesWithOfferId(Number(params.id));
                setTrades(resp.items);
                const offerResp = await offerService.getOfferInformation(Number(params.id));
                setOffer(offerResp);
            } else {
                toast.error("No offer ID!");
            }

        } catch (e) {
            toast.error("Connection error. Failed to fetch trades");
        }
    }


    useEffect(()=>{
        //fetch trade. If no results, fetch offer. Else, setOffer trade[0].offer
        fetchTradesAssociatedWithOffer();
    }, [])



    return (<>
            <div className="flex flex-row h-full w-full px-20 my-10">
                <div className="flex flex-col h-3/5 w-2/5 pr-2 ">
                    <div className="flex flex-col py-3 rounded-lg px-5 pt-4 rounded-lg bg-[#FAFCFF] w-60 mx-auto ">
                        <h1 className="font-sans w-full mx-auto text-center text-xl font-bold text-polar">
                            Trade proposals received
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        {offer && <OfferCardProfile offer={offer} renewOffers={fetchTradesAssociatedWithOffer}/>}
                    </div>

                    <button onClick={()=>navigate(-1)}
                       className=" mt-2 font-bold rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg mx-auto">
                        Back
                    </button>
                </div>
                <div className="flex flex-col w-4/5">
                    <div className="flex flex-row  rounded-lg px-5 rounded-lg  justify-between">
                        <div
                            className="flex mr-5 bg-white rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button  className="my-auto mx-auto w-full " onClick={()=>fetchTradesAssociatedWithOfferWithStatus(TRADE_STATUS.All)}>
                                <p className={`py-2 px-4 font-bold text-polar text-center ${selectedStatus === TRADE_STATUS.All ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    All
                                </p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-nyellow rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>fetchTradesAssociatedWithOfferWithStatus(TRADE_STATUS.Pending)} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === TRADE_STATUS.Pending? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Pending</p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-ngreen rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>fetchTradesAssociatedWithOfferWithStatus(TRADE_STATUS.Accepted)} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === TRADE_STATUS.Accepted ? 'decoration-frostdr underline underline-offset-8' : 'text-l'}`}>
                                    Ongoing</p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-nred rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>fetchTradesAssociatedWithOfferWithStatus(TRADE_STATUS.Rejected)} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === TRADE_STATUS.Rejected ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Rejected by you</p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-gray-200 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>fetchTradesAssociatedWithOfferWithStatus(TRADE_STATUS.Sold)} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === TRADE_STATUS.Sold ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Closed </p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-blue-400 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>fetchTradesAssociatedWithOfferWithStatus(TRADE_STATUS.Deleted)} className="my-auto mx-auto">
                                <p className={`py-2  px-4 font-bold text-polar text-center ${selectedStatus === TRADE_STATUS.Deleted? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Deleted by buyer</p>
                            </button>
                        </div>
                    </div>
                    <div className="flex flex-col">
                        <div className="flex flex-wrap pl-3 mt-10 mx-auto">

                            {trades && trades.map((trade)=>{
                                return(
                                        <OfferInformationForSeller trade={trade} chat={true} key={trade.tradeId}/>
                                );
                            })}
                            {!trades || trades.length === 0 && <div className="flex flex-col mx-auto">
                                <h1 className="font-sans text-center text-xl font-bold text-polar">No trades available.</h1>
                            </div>}
                        </div>

                        <h1 className="mx-auto">
                            {/*//todo callback update trades*/}
                            {trades && trades.length > 0 ?  <Paginator paginatorProps={paginatorProps}  callback={()=>{console.log("a")}}/>: <h1 className="text-polar font-bold"> No trade proposals related to this offer</h1>}

                        </h1>
                    </div>
                </div>
            </div>
        </>
    );
};

export default SellerOfferDashboard;
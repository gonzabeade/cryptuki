import React, {useEffect, useState} from 'react';
import UserProfileCards from "../../components/UserProfileCards";
import StatusCards from "../../components/StatusCards/StatusCards";
import Paginator from "../../components/Paginator";
import TransactionModel from "../../types/TransactionModel";
import useTradeService from "../../hooks/useTradeService";
import TradeBuyerCard from "../../components/TradeBuyerCard";
import {useAuth} from "../../contexts/AuthContext";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import {TRADE_STATUS} from "../../common/constants";
import {attendError} from "../../common/utils/utils";
import i18n from "../../i18n";

const BuyerDashboard = () => {
    const [trades, setTrades] = useState<TransactionModel[]>([]);
    const tradeService = useTradeService();
    const {user} = useAuth();
    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
            actualPage: 0,
            totalPages: 0,
            nextUri:'',
            prevUri:''
        }
    );

    async function fetchTradesBuyerProfile(){
        try {
            if(user){
                const resp = await tradeService.getRelatedTrades(user?.username!, TRADE_STATUS.Pending);
                if(resp.paginatorProps){
                    setPaginatorProps(resp.paginatorProps);
                }
                setTrades(resp.items);
            }
        }catch (e){
            attendError("Connection error. Failed to fetch trades",e);
        }
    }

    async function fetchTradesWithStatus(status:string){
        try {
            const resp = await tradeService.getRelatedTrades( user?.username!, status);
            if(resp.paginatorProps){
                setPaginatorProps(resp.paginatorProps);
            }
            setTrades(resp.items);
        }catch (e) {
            attendError("Couldn't fethc trades with status " + status,e);
        }
    }
    async function fetchPage(uri:string){
        try{
            const resp = await tradeService.getPaginatedTrades(uri);
            if(resp.paginatorProps){
                setPaginatorProps(resp.paginatorProps);
            }
            setTrades(resp.items);
        }catch (e) {
            attendError("Connection error. Couldn't fetch trades",e)
        }
    }

    useEffect(()=>{
        fetchTradesBuyerProfile();
    },[user]);

    return (
        <div className="flex h-full w-full px-20 my-10">
            <div className="flex flex-col h-full mx-20 w-1/5">
                  <UserProfileCards username={user? user.username: "Loading"} phoneNumber={user? user.phoneNumber : "Loading"} email={user? user.email:"loading"} rating={user? user.rating: 0} tradeQuantity={user? user.ratingCount:0} picture={user?.picture!}/>
            </div>
            {/*//  Middle Panel: trade */}
            <div className="flex flex-col h-full mr-20 w-3/5">
                <div
                    className="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-2xl font-bold font-sans text-polar">{i18n.t('buyOrdersCreated')}</h1>
                </div>
                <StatusCards active={"PENDING"}  callback={fetchTradesWithStatus}/>
                <div className="flex flex-col justify-center w-full mx-auto mt-10">
                    {trades.length >0 && trades.map((trade)=>{
                        return (
                            <TradeBuyerCard trade={trade}  key={trade.tradeId}/>
                        );
                    })}
                </div>
                {trades.length === 0 &&
                    <h2 className="text-center text-xl font-semibold font-sans text-polar mt-4">{i18n.t('noResults')}</h2>}
                {trades.length !== 0 &&
                    <div className="flex flex-col mt-3">
                        <Paginator paginatorProps={paginatorProps} callback={fetchPage}/>
                    </div>}

            </div>

    </div>
    );
};

export default BuyerDashboard;
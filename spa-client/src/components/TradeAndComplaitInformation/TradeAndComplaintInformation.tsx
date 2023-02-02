import React from 'react';
import OfferModel from "../../types/OfferModel";
import {ComplainModel} from "../../types/ComplainModel";
import UserModel from "../../types/UserModel";
import TransactionModel from "../../types/TransactionModel";
import i18n from "../../i18n";


type TradeAndComplaintProps = {
    offer:OfferModel,
    trade:TransactionModel,
    complain: ComplainModel
    buyer: UserModel,
    complainer:UserModel,
    seller: UserModel
}


const TradeAndComplaintInformation = ({trade,offer,complain,buyer,seller,complainer}:TradeAndComplaintProps) => {

    return (
        <div>
            <div className="flex flex-col">
                <div
                    className="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard">
                    <h1 className="font-sans font-semibold font-polard text-2xl text-start ">
                        {i18n.t('tradeDetails')}
                    </h1>
                    <h1 className="w-full font-sans font-medium text-polard text-xl text-start ">
                        {i18n.t('trade')} #
                        {trade.tradeId}
                    </h1>
                    <h1 className="w-full font-sans font-medium text-polard text-xl text-start ">
                        {i18n.t('carriedOutOverOffer')}
                        #
                        {offer.offerId}
                    </h1>
                    <div className="w-full flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            {i18n.t('cryptocurrency')}
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {offer.cryptoCode}
                        </h3>
                    </div>
                    <div className="w-full flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            {i18n.t('location')}
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {offer.location}
                        </h3>
                    </div>
                    <div className="w-full  flex flex-col mx-auto mt-5">
                        <h2 className="font-sans font-polard font-semibold text-xl mb-3 text-start">
                            {i18n.t('participants')}
                        </h2>
                        <li className="font-sans font-polard"><b>
                            {i18n.t('buyer')} :
                        </b>
                            {buyer?.username}
                        </li>
                        <li className="font-sans font-polard"><b>
                            {i18n.t('seller')} :
                        </b>
                            {seller?.username}
                        </li>
                    </div>
                    <div className="w-full flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            {i18n.t('offeredAmount')}
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {trade?.buyingQuantity}
                            ARS (
                            {i18n.t('of')}
                            {offer.unitPrice}
                            ARS)
                        </h3>
                    </div>
                    <div className="w-full  flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            {i18n.t('tradeState')}
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {trade.status}
                        </h3>
                    </div>
                </div>
            </div>
            <div className="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard mt-5">
                <h1 className="font-sans font-semibold font-polard text-2xl text-start ">
                    {i18n.t('claimDescription')}
                </h1>
                <p className="font-sans text-start mt-2 italic">
                    {complain.comments}
                </p>
            </div>
            <div className="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard mt-5">
                <h1 className="font-sans font-semibold font-polard text-2xl text-start ">
                    {i18n.t('claimUser')}
                </h1>
                {(complainer && complainer.username) ?
                   <div>
                       <h1 className="font-sans font-medium text-polard text-xl text-start">
                        {complainer.username}
                        </h1>
                        <p className="rounded-lg text-gray-400">
                            {i18n.t('lastTimeActive')}
                        : {complainer.lastLogin.toString().substring(0,10)}</p>
                   </div>
                    :
                    <p className="rounded-lg text-lg">
                        {complainer?.email}
                    </p>                }
            </div>
        </div>
    );
};

export default TradeAndComplaintInformation;
import React from 'react';
import OfferModel from "../../types/OfferModel";
import {ComplainModel} from "../../types/ComplainModel";
import UserModel from "../../types/UserModel";
import TransactionModel from "../../types/TransactionModel";


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
        <div className="w-1/4">
            <div className="flex flex-col">
                <div
                    className="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard">
                    <h1 className="font-sans font-semibold font-polard text-2xl text-start ">
                        Detalles del trade
                    </h1>
                    <h1 className="w-full font-sans font-medium text-polard text-xl text-start ">
                        Trade #
                        {trade.tradeId}
                    </h1>
                    <h1 className="w-full font-sans font-medium text-polard text-xl text-start ">
                        Asociado al anuncio
                        #
                        {offer.offerId}
                    </h1>
                    <div className="w-full flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                           Criptomoneda
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {offer.cryptoCode}
                        </h3>
                    </div>
                    <div className="w-full flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            Ubicaciòn
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {offer.location}
                        </h3>
                    </div>
                    <div className="w-full  flex flex-col mx-auto mt-5">
                        <h2 className="font-sans font-polard font-semibold text-xl mb-3 text-start">
                            Participantes
                        </h2>
                        <li className="font-sans font-polard"><b>
                            Comprador :
                        </b>
                            {buyer?.username}
                        </li>
                        <li className="font-sans font-polard"><b>
                            Vendedor :
                        </b>
                            {seller?.username}
                        </li>
                    </div>
                    <div className="w-full flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            Monto
                            :
                        </h2>
                        <h3 className="font-sans font-medium text-polard text-xl text-start ">
                            {trade?.buyingQuantity}
                            ARS (
                            Por cada moneda
                            {offer.unitPrice}
                            ARS)
                        </h3>
                    </div>
                    <div className="w-full  flex flex-row mx-auto mt-3">
                        <h2 className="font-sans font-semibold font-polard text-xl text-start mr-3">
                            Estado del trade
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
                    Descripcion del reclamo
                </h1>
                <p className="font-sans text-start mt-2 italic">
                    {complain.comments}
                </p>
            </div>
            <div className="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard mt-5">
                <h1 className="font-sans font-semibold font-polard text-2xl text-start ">
                    Usuario del reclamo
                </h1>
                {(complainer && complainer.username) ?
                   <div>
                       <h1 className="font-sans font-medium text-polard text-xl text-start">
                        {complainer.username}
                        </h1>
                        <p className="rounded-lg text-gray-400">
                        Ultima vez activo
                        : {complainer.lastLogin.toString()}</p>
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
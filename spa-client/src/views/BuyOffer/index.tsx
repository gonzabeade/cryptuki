import React from 'react';
import UserInfo from "../../components/UserInfo/index";
import Message from "../../components/Message";
import TradeStatusAlert from "../../components/TradeStatusAlert";
import {InformationCircleIcon} from "@heroicons/react/24/outline";
import StatusCards from "../../components/StatusCards/StatusCards";
import Paginator from "../../components/Paginator";

const BuyOffer = () => {
    return (
        <div className="flex flex-wrap divide-x-2 divide-polard mt-10">
            <div className="flex flex-col w-3/5">
                <div className="flex">
                    <div className="flex flex-col mx-auto mt-10">
                        <h2 className="font-sans font-semibold text-polard text-2xl text-center">
                            You are about to buy
                        </h2>
                        <img src="/public/images/${offer.crypto.code}.png" alt="
                        ${offer.crypto.commercialName}" className="w-20 h-20 mx-auto"/>
                        <h1 className="text-center text-4xl font-bold">
                            BTC
                        </h1>
                        <h2 className="font-sans font-medium text-polard text-2xl text-center">

                            100.000
                            ARS
                        </h2>
                        <div className="flex flex-row mt-3 font-sans ">
                            <h2 className="font-sans mx-2"><b>
                                Minimum acceptable offer
                                :</b>10.000
                                ARS
                            </h2>
                            <h2 className="font-sans"><b>
                                Maximum acceptable offer
                                :</b>14.000
                                ARS
                            </h2>
                        </div>
                        <h2 className="pt-2 font-sans text-center"><b>
                            Location
                            :</b>
                            Lomas
                        </h2>
                    </div>
                </div>
                <div>
                    {/*form*/}
                </div>
            </div>
            <div className="flex flex-col w-2/5 items-center">
            <UserInfo username={"salva"} email={"salva.castagnino@gmail.com"} phone_number={"12345678"} last_login={"Ayer"} trades_completed={1} rating={4.32}/>

                    <h1 className="font-sans font-bold text-3xl mx-auto">
                        Location
                    </h1>

                    <iframe
                        className="mx-auto mb-10"
                        width="450"
                        height="250"
                        frameBorder="0"
                        referrerPolicy="no-referrer-when-downgrade"
                        src="https://www.google.com/maps/embed/v1/search?key=${apiKey}&q=${offer.location}"
                        allowFullScreen>
                    </iframe>
            </div>
            <Paginator totalPages={2} actualPage={1}/>
        </div>
    );
};

export default BuyOffer;
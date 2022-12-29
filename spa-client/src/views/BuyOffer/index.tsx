import React from 'react';
import UserInfo from "../../components/UserInfo/index";
const BuyOffer = () => {
    return (
        <div className="flex flex-wrap mt-10 mx-5">
            <div className="flex flex-col w-3/5 shadow rounded-lg bg-white">
                <div className="flex">
                    <div className="flex flex-col mx-auto mt-10">
                        <h2 className="font-sans font-semibold text-polard text-2xl text-center">
                            You are about to buy
                        </h2>
                        <img src={`/public/images/crypto.png`} alt="cryptoCommercialName" className="w-20 h-20 mx-auto"/>
                        <h1 className="text-center text-3xl font-bold text-polar">
                            BTC
                        </h1>
                        <h2 className="font-sans font-medium  text-xl text-center text-polar">

                            100.000
                            ARS
                        </h2>
                        <div className="flex flex-row mt-3 font-sans ">
                            <h2 className="font-sans mx-2"><b className="text-polar">
                                Minimum acceptable offer:</b> 10.000
                                ARS
                            </h2>
                            <h2 className="font-sans"><b className="text-polar">
                                Maximum acceptable offer: </b>14.000
                                ARS
                            </h2>
                        </div>
                        <h2 className="pt-2 font-sans text-center"><b className="text-polar">
                            Location: </b>
                            Lomas
                        </h2>
                    </div>
                </div>
                <form className="flex flex-col mt-3">
                    <input className="p-2 m-2 rounded-lg shadow mx-auto" placeholder="Amount in ARS"/>
                    <p className="mx-auto">or</p>
                    <input className="p-2 m-2 rounded-lg shadow mx-auto" placeholder={`Amount in CRYPTO `}/>
                    <div className="flex flex-row justify-evenly mt-3">
                        <button className="p-3 w-48 bg-polarlr/[0.6] text-white font-roboto rounded-lg">Cancel</button>
                        <button type="submit" className=" w-48 p-3 bg-frostdr text-white font-roboto rounded-lg">Make trade proposal</button>
                    </div>
                </form>
            </div>
            <div className="flex flex-col w-2/5 items-center">
            <UserInfo username={"salva"} email={"salva.castagnino@gmail.com"} phone_number={"12345678"} last_login={"Ayer"} trades_completed={1} rating={4.32}/>

                    <h1 className="font-sans font-bold text-2xl mx-auto text-polar mt-6 mb-2">
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
        </div>
    );
};

export default BuyOffer;
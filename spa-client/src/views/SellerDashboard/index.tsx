import React, {useState} from 'react';
import {KycModel} from "../../types/KycModel";
import TransactionList from "../../components/TransactionList";
import TransactionModel from "../../types/TransactionModel";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";
import UserProfileCards from "../../components/UserProfileCards";
import StatusCardsSeller from "../../components/StatusCardsSeller";
import OfferCardProfile from "../../components/OfferCardProfile";
import Loader from "../../components/Loader";


const SellerDashboard = () => {
    const [kyc, setKyc] = useState<KycModel>({status:'APR'});

    const [lastTransactions, setLastTransactions] = useState<TransactionModel[]>([
        {
            status:'sold',
            buyer:{
                accessToken: "",
                refreshToken: "string",
                admin: false,
                email:"mdedeu@itba.edu.ar",
                phoneNumber:"1245311",
                username:"mdedeu",
                lastLogin:"online",
                trades_completed:1,
                rating:1.3,
                image_url:"/"
            },
            offer: {
                cryptoCode:"BTC",
                date:new Date(),
                location:"Balvanera",
                maxInCrypto:2,
                minInCrypto:0.001,
                offerId:1,
                offerStatus:"PENDING",
                unitPrice:1000000,
                seller: {
                    accessToken: "",
                    refreshToken: "string",
                    admin: false,
                    email:"mdedeu@itba.edu.ar",
                    phoneNumber:"1245311",
                    username:"mdedeu",
                    lastLogin:"online",
                    trades_completed:1,
                    rating:1.3,
                    image_url:"/"
                }
            },
            amount: 1000,
            id:1,
            date: new Date()
        },
        {
            status:'rejected',
            buyer:{
                accessToken: "",
                refreshToken: "string",
                admin: false,
                email:"mdedeu@itba.edu.ar",
                phoneNumber:"1245311",
                username:"mdedeu",
                lastLogin:"online",
                trades_completed:1,
                rating:1.3,
                image_url:"/"
            },
            offer: {
                cryptoCode:"BTC",
                date:new Date(),
                location:"Balvanera",
                maxInCrypto:2,
                minInCrypto:0.001,
                offerId:1,
                offerStatus:"PENDING",
                unitPrice:1000000,
                seller: {
                    accessToken: "",
                    refreshToken: "string",
                    admin: false,
                    email:"mdedeu@itba.edu.ar",
                    phoneNumber:"1245311",
                    username:"mdedeu",
                    lastLogin:"online",
                    trades_completed:1,
                    rating:1.3,
                    image_url:"/"
                }
            },
            amount: 1000,
            id:1,
            date: new Date()
        }
    ]);
    const [offers, setOffers] = useState<OfferModel[]>([{
            cryptoCode:"BTC",
            date:new Date(),
            location:"Balvanera",
            maxInCrypto:2,
            minInCrypto:0.001,
            offerId:1,
            offerStatus:"SOL",
            unitPrice:1000000,
            seller: {
                accessToken: "",
                refreshToken: "string",
                admin: false,
                email:"mdedeu@itba.edu.ar",
                phoneNumber:"1245311",
                username:"mdedeu",
                lastLogin:"online",
                trades_completed:1,
                rating:1.3,
                image_url:"/"
            }
        }]);

    return (
        <div className="flex h-full w-full px-10 my-10">

            {/*// Left Panel: chat and seller stats*/}
            <div className="flex flex-col h-full mx-10 px-10 w-1/3">
                <div>
                  <UserProfileCards username={"mdedeu"} phoneNumber={123457} email={"mdedeu@itba.edu.ar"} rating={4.5} tradeQuantity={2}/>
                </div>
                {!kyc && <>
                    <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-6 font-sans font-bold">
                        <img className="w-5 h-5 mr-4 my-auto " src="attention"/>
                        <p>
                            Validate your identity
                        </p>
                    </div>
                    <div className="mx-auto mt-8">
                        <a href="/kyc"
                           className="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto">
                            Start KYC
                        </a>
                    </div>
                </>}
                {
                    kyc.status !== 'APR' ?
                        <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-3 font-sans font-bold">
                            <img className="w-5 h-5 mr-4 my-auto " src="attention"/>
                            <p>Validation of identity submitted. Please wait </p>
                        </div> : <TransactionList transactions={lastTransactions}/>

                    //if not empty, render last transactions
                }

                <div className="mx-auto mt-5">
                    <a href="/offer/upload"
                       className="py-2 pr-4 pl-3 text-lg text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto cursor-pointer">Upload
                        Advertisment</a>
                </div>
            </div>

            <div className="flex flex-col h-full mr-20 w-4/5">
                <div className="shadow-xl w-full h-1/8 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-2xl font-bold font-sans text-polar">Uploaded offers</h1>
                </div>
                <div className="flex flex-col w-full mt-2">
                    <div className="flex w-full mx-auto ">
                        <StatusCardsSeller  active={"all"} callback={()=>console.log("a")} base_url={"/"}/>
                    </div>
                    <div className="flex flex-wrap w-full mx-auto justify-center mt-2">
                        {offers.length === 0 && <p className={"text-polar text-lg font-bold mt-10"}>No offers uploaded yet</p>}
                        {offers.map((offer)=>{
                            return(
                                <OfferCardProfile offer={offer} key={offer.offerId}/>
                            );
                        })}
                    </div>
                    {offers.length >= 1 && <div className="mx-auto">
                        <Paginator totalPages={10} actualPage={1} callback={() => console.log("messi")}/>
                    </div>}
                </div>
            </div>
        </div>
    );
};

export default SellerDashboard;
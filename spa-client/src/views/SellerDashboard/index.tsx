import React, {useEffect, useState} from 'react';
import {KycModel} from "../../types/KycModel";
import TransactionList from "../../components/TransactionList";
import TransactionModel from "../../types/TransactionModel";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";
import UserProfileCards from "../../components/UserProfileCards";
import StatusCardsSeller from "../../components/StatusCardsSeller";
import OfferCardProfile from "../../components/OfferCardProfile";
import useOfferService from "../../hooks/useOfferService";
import useTradeService from "../../hooks/useTradeService";
import {toast} from "react-toastify";
import useUserService from "../../hooks/useUserService";
import UserModel from "../../types/UserModel";


const SellerDashboard = () => {
    //TODO fetch this state
    const [kyc, setKyc] = useState<KycModel>({status:'PEN'});

    const [lastTransactions, setLastTransactions] = useState<TransactionModel[]>([]);
    const [offers, setOffers] = useState<OfferModel[]>([]);

    const offerService = useOfferService();
    const tradeService = useTradeService();
    const userService = useUserService();

    const [user, setUser] = useState<UserModel>();

    async function fetchUserData(){
        try{
            const apiCall = await userService?.getUser(userService.getLoggedInUser()!);
            setUser(apiCall);
        }catch (e){
            toast.error("Connection error. Failed to fetch user data")
        }

    }

    useEffect(()=>{
        fetchUserData();
    },[])


    async function getOffers(){
        try{
            const resp = await offerService.getOffersByOwner(userService.getLoggedInUser()!);
            setOffers(resp)
        }catch (e) {
            toast.error("Connection error. Failed to fetch offers");
        }
    }

    async function getLastTransactions(){
        try{
            const resp = await tradeService.getLastTransactions(userService.getLoggedInUser());
            if(resp){
                setLastTransactions(resp)
            }
        }catch (e) {
            console.log(e)
            toast.error("Connection error. Failed to fetch lasts transactions");
        }
    }

    useEffect(()=>{
       getOffers();
    },[])

    useEffect(()=>{
       getLastTransactions();
    },[])

    return (
        <div className="flex h-full w-full px-10 my-10">

            {/*// Left Panel: chat and seller stats*/}
            <div className="flex flex-col h-full mx-10 px-10 w-1/3">
                <div>
                  <UserProfileCards username={user? user.username: "Loading"} phoneNumber={user? user.phoneNumber: "Loading"} email={user? user.email: "loading"} rating={user? user.rating:0} tradeQuantity={user? user.ratingCount : 0}/>
                </div>
                {!kyc && <>
                    <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-6 font-sans font-bold">
                        <img className="w-5 h-5 mr-4 my-auto " src="attention" alt="kyc"/>
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
                            <img className="w-5 h-5 mr-4 my-auto " src="attention" alt={"kyc submitted"}/>
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
                        {/*TODO callback*/}
                        <StatusCardsSeller  active={"ALL"} callback={()=>console.log("a")} base_url={"/"}/>
                    </div>
                    <div className="flex flex-wrap w-full mx-auto justify-center mt-2">
                        {offers.length === 0 && <p className={"text-polar text-lg font-bold mt-10"}>No offers uploaded yet</p>}
                    </div>
                    {offers.length >= 1 && <div className="mx-auto">
                        {offers.map((offer)=>{
                            return(
                                <OfferCardProfile offer={offer} key={offer.offerId}/>
                            );
                        })}
                        <Paginator totalPages={10} actualPage={1} callback={() => console.log("messi")}/>
                    </div>

                    }
                </div>
            </div>
        </div>
    );
};

export default SellerDashboard;
import React, {useEffect, useState} from 'react';
import {KycModel} from "../../types/KycModel";
import TransactionModel from "../../types/TransactionModel";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";
import UserProfileCards from "../../components/UserProfileCards";
import StatusCardsSeller from "../../components/StatusCardsSeller";
import OfferCardProfile from "../../components/OfferCardProfile";
import useOfferService from "../../hooks/useOfferService";
import useTradeService from "../../hooks/useTradeService";
import useUserService from "../../hooks/useUserService";
import {useAuth} from "../../contexts/AuthContext";
import {OFFER_STATUS} from "../../common/constants";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import {attendError} from "../../common/utils/utils";
import TransactionList from "../../components/TransactionList";


const SellerDashboard = () => {
    const [kyc, setKyc] = useState<KycModel|null>(null);
    const [kycAccepted, setKycAccepted] = useState(false);
    const [offers, setOffers] = useState<OfferModel[]>([]);

    const offerService = useOfferService();
    const tradeService = useTradeService();
    const userService = useUserService();
    const {user} = useAuth();
    const [selectedStatus, setSelectedStatus] = useState<OFFER_STATUS>(OFFER_STATUS.Pending);
    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
        actualPage: 0,
        totalPages: 0,
        prevUri:'',
        nextUri:''
    });

    async function fetchKycStatus(){
        try{
            const resp = await userService.getKYCStatus(userService.getLoggedInUser()!);
            if(resp){ //pending
                setKyc(resp);
            }else if(userService.hasKyc()){
                setKycAccepted(true);
            }

        }catch (e) {
            attendError("Connection failed. Couldn't fetch KYC status",e)
        }
    }
    useEffect(()=>{
        fetchKycStatus();
    },[])


    async function getOffers(){
        try{
            const resp = await offerService.getOffersByOwner(userService.getLoggedInUser()!);
            setOffers(resp.items);
            if(resp.paginatorProps){
                setPaginatorProps(resp.paginatorProps);
            }
        }catch (e) {
            attendError("Connection error. Failed to fetch offers",e);
        }
    }


    async function getPagginatedOffers(uri:string){
        try{

            const params = offerService.getSearchParamsFromURI(uri);
            const apiCall = await offerService?.getOffers(params);
            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);

        }catch (e){
            attendError("Connection error. Failed to fetch paginated offers",e)
        }
    }

    useEffect(()=>{
       getOffers();
    },[])



    async function fetchOffersWithStatus(status:OFFER_STATUS) {
        try{
           const resp = await offerService.getOffersByOwner(user?.username!, status);
           setOffers(resp.items);
           if(resp.paginatorProps){
               setPaginatorProps(resp.paginatorProps);
           }
           setSelectedStatus(status);
        }catch (e) {
            attendError("Connection error fetching offers with status "+ status,e)
        }

    }

    return (
        <div className="flex h-full w-full px-10 my-10">

            {/*// Left Panel: chat and seller stats*/}
            <div className="flex flex-col h-full mx-10 px-10 w-1/3">
                <div>
                    {user && <UserProfileCards username={ user.username} phoneNumber={user.phoneNumber} email={user.email} rating={user.rating} tradeQuantity={user.ratingCount } picture={user.picture}/> }
                </div>
                {!kyc && !kycAccepted && <>
                    <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-6 font-sans font-bold">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5"
                             stroke="currentColor" className="w-12 h-12">
                            <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M19.5 14.25v-2.625a3.375 3.375 0 00-3.375-3.375h-1.5A1.125 1.125 0 0113.5 7.125v-1.5a3.375 3.375 0 00-3.375-3.375H8.25m5.231 13.481L15 17.25m-4.5-15H5.625c-.621 0-1.125.504-1.125 1.125v16.5c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 00-9-9zm3.75 11.625a2.625 2.625 0 11-5.25 0 2.625 2.625 0 015.25 0z"/>
                        </svg>
                        <p className={"my-auto mx-auto"}>
                            Validate your identity
                        </p>
                    </div>
                    <div className="mx-auto mt-8">
                        <a href="/kyc/upload"
                           className="cursor-pointer py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto">
                            Start KYC
                        </a>
                    </div>
                </>
                }
                {
                   kyc &&  !kycAccepted &&
                    <div>
                        <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-3 font-sans font-bold">
                            <img className="w-5 h-5 mr-4 my-auto " src="attention" alt={"kyc submitted"}/>
                            <p>Validation of identity submitted. Please wait </p>
                        </div>
                    </div>
                }
                {!kyc && kycAccepted &&
                    <div className="mx-auto mt-5">
                    <a href="/offer/upload"
                       className="py-2 pr-4 pl-3 text-lg text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto cursor-pointer">Upload
                        Advertisement</a>
                    </div>
                }
            </div>
            <div className="flex flex-col h-full mr-20 w-4/5">
                <div className="shadow-xl w-full h-1/8 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-2xl font-bold font-sans text-polar">Uploaded offers</h1>
                </div>
                <div className="flex flex-col w-full mt-2">
                    <div className="flex w-full mx-auto ">
                        <StatusCardsSeller  active={selectedStatus} callback={fetchOffersWithStatus}/>
                    </div>
                    <div className="flex flex-wrap w-full mx-auto justify-center mt-2">
                        {offers.length === 0 && <p className={"text-polar text-lg font-bold mt-10"}>No offers uploaded yet</p>}

                        {offers.length >= 1 && offers.map((offer) => {
                                    return (
                                        <OfferCardProfile offer={offer} key={offer.offerId} renewOffers={fetchOffersWithStatus}/>
                                    );
                                })
                        }
                    </div>
                </div>
                <Paginator paginatorProps={paginatorProps} callback={getPagginatedOffers}/>
            </div>
        </div>
    );
};

export default SellerDashboard;
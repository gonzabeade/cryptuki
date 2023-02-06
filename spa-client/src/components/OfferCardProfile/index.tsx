import React from 'react';
import OfferModel from "../../types/OfferModel";
import {Link, useNavigate} from "react-router-dom";
import useOfferService from "../../hooks/useOfferService";
import {toast} from "react-toastify";
import {OFFER_STATUS} from "../../common/constants";
import {Axios, AxiosError, AxiosResponse} from "axios";
import i18n from "../../i18n";

type OfferCardProfileProps = {
    offer:OfferModel,
    renewOffers:Function
}

const OfferCardProfile: React.FC<OfferCardProfileProps> = ({ offer , renewOffers}) =>{
    const navigate = useNavigate();
    const offerService = useOfferService();

    async function pauseOffer(){
        try{
            await offerService.pauseOffer(offer);
            toast.success("Offer paused");
            renewOffers(OFFER_STATUS.PausedBySeller);
        }catch (e) {
            const error: AxiosError =  e as AxiosError;
            if(error.response?.data){
                const data = error.response.data as {message:string} ;
                toast.error( i18n.t('couldntPauseOffer')+ ` ${data.message}`);
            }else{
                toast.error(i18n.t(`${error.code}`))
            }
        }
    }

    async function deleteOffer(){
        try{
            await offerService.deleteOffer(offer);
            toast.success("Offer deleted");
            renewOffers(OFFER_STATUS.Deleted);
        }catch (e:any) {
            toast.error("An error occurred when deleting the offer. " + e);
        }
    }
    async function resumeOffer(){
        try{
            await offerService.resumeOffer(offer);
            toast.success("Offer resumed");
            renewOffers(OFFER_STATUS.Pending);
        }catch (e) {
            toast.error("Connection error. Couldn't resume offer + e");
        }
    }

    return (
        <div className={"flex flex-col bg-[#FAFCFF] my-3 rounded-lg mx-1"}>
            <Link to={offer.offerStatus !== 'DEL' ? "/seller/offer/"+offer.offerId : "/seller/#"} className={`shadow-lg cursor-pointer z-10 flex flex-col p-3 rounded-lg w-60 my-5 mx-auto ${ offer.offerStatus !== 'DEL' ? '' : 'cursor-not-allowed' }`} >
                <h1 className=" text-polar text-center text-xl font-bold ">
                    {i18n.t('offer')}
                    #{offer.offerId}
                </h1>

                <div className="flex flex-col mx-auto mt-5">
                    <h1 className="text-polar font-bold text-lg text-center">
                        {i18n.t('acceptedRange')}
                    </h1>
                    <p className="text-center">
                        {offer.minInCrypto +' ' + offer.cryptoCode} -  {offer.maxInCrypto +' ' + offer.cryptoCode}
                    </p>
                </div>
                <div className="flex flex-col mx-auto">
                    <h1 className="font-bold text-lg text-polar">
                        {i18n.t('unitPrice')}
                    </h1>
                    <p className="text-center">
                        {offer.unitPrice} ARS
                    </p>
                </div>
                <div className="flex flex-col mx-auto mb-5">
                    <h1 className="font-bold text-lg text-polar">
                        {i18n.t('location')}
                    </h1>
                    <p className="text-center">
                        {i18n.t(offer.location)}
                    </p>
                </div>

            </Link>
            <div className={"flex z-50 my-auto "}>
                {
                    offer.offerStatus !== 'DEL' && offer.offerStatus !== 'PSE' && offer.offerStatus !== 'SOL' &&
                    <div className={"mx-3 w-full mb-2"}>
                        <div className="flex flex-row justify-between my-2">
                                <button type="submit" className=" rounded-lg text-center bg-frostdr hover:bg-frostdr/[0.7] w-1/2 p-2 mr-2 font-semibold text-white z-50" onClick={()=>navigate("/offer/"+offer.offerId+"/edit")}>
                                    {i18n.t('edit')}
                                </button>
                            <div className="rounded-lg text-center bg-nred hover:bg-nred/[0.7] text-white w-1/2 p-2 ml-2 z-50 text-white">
                                <button type="submit"  className="font-semibold text-white" onClick={()=>deleteOffer()/* post to delete method in Service */}>
                                    {i18n.t('delete')}
                                </button>
                            </div>
                        </div>
                        <div className=" z-50 rounded-lg bg-nyellow hover:bg-nyellow/[0.6] py-3 px-5 text-l font-sans text-center cursor-pointer">
                            <button className="font-semibold text-white" onClick={pauseOffer}>
                                {i18n.t('pauseOffer')}
                            </button>
                        </div>
                    </div>
                }
                {
                    offer.offerStatus === 'PSE' &&
                        <button type="submit"  className="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer my-auto mx-auto" onClick={resumeOffer}>
                            {i18n.t('resumeOffer')}
                        </button>
                }
                {
                    offer.offerStatus === 'DEL' && <div className="bg-nred p-2 text-white text-center mx-auto mb-2">
                        {i18n.t('offerDeleted')}
                    </div>
                }
                {
                    offer.offerStatus === 'SOL' &&
                    <div className={"flex flex-col justify-center mx-auto my-5"}>
                        <div className="flex bg-gray-500 my-auto p-2 text-white text-center mx-auto">
                            {i18n.t('offerSold')}
                        </div>
                        <button onClick={()=>navigate("/offer/"+offer.offerId+"/repeat")}
                                className=" font-bold bg-frostdr text-white  mt-4 p-3 rounded-md font-sans  w-32 mx-5 active:cursor-progress">
                            {i18n.t('RepeatOffer')}
                        </button>
                    </div>
                }
            </div>

        </div>



    );
};

export default OfferCardProfile;
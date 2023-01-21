import React from 'react';
import OfferModel from "../../types/OfferModel";
import {useNavigate} from "react-router-dom";
import useOfferService from "../../hooks/useOfferService";
import {toast} from "react-toastify";
import {OFFER_STATUS} from "../../common/constants";

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
            toast.error("Connection error. Couldn't pause offer");
        }
    }

    async function deleteOffer(){
        try{
            await offerService.deleteOffer(offer);
            toast.success("Offer deleted");
            renewOffers(OFFER_STATUS.Deleted);
        }catch (e) {
            toast.error("Connection error. Couldn't delete offer");
        }
    }
    async function resumeOffer(){
        try{
            await offerService.resumeOffer(offer);
            toast.success("Offer resumed");
            renewOffers(OFFER_STATUS.Pending);
        }catch (e) {
            toast.error("Connection error. Couldn't resume offer");
        }
    }

    return (
        <div className={"flex flex-col bg-[#FAFCFF] my-3 rounded-lg mx-1"}>
            <a href={offer.offerStatus !== 'DEL' && offer.offerStatus !== 'SOL'? "/seller/offer/"+offer.offerId : "/seller/#"} className={`shadow-lg cursor-pointer z-10 flex flex-col p-3 rounded-lg w-60 my-5 mx-auto ${ offer.offerStatus !== 'DEL' && offer.offerStatus !== 'SOL' ? '' : 'cursor-not-allowed' }`} >
                <h1 className=" text-polar text-center text-xl font-bold ">
                    Offer
                    #{offer.offerId}
                </h1>

                <div className="flex flex-col mx-auto mt-5">
                    <h1 className="text-polar font-bold text-lg text-center">
                        Accepted Range
                    </h1>
                    <p className="text-center">
                        {offer.minInCrypto +' ' + offer.cryptoCode} -  {offer.maxInCrypto +' ' + offer.cryptoCode}
                    </p>
                </div>
                <div className="flex flex-col mx-auto">
                    <h1 className="font-bold text-lg text-polar">
                        Unit price
                    </h1>
                    <p className="text-center">
                        {offer.unitPrice} ARS
                    </p>
                </div>
                <div className="flex flex-col mx-auto mb-5">
                    <h1 className="font-bold text-lg text-polar">
                        Location
                    </h1>
                    <p className="text-center">
                        {offer.location}
                    </p>
                </div>

            </a>
            <div className={"flex -mt-3  mb-2 z-50 "}>
                {
                    offer.offerStatus !== 'DEL' && offer.offerStatus !== 'PSE' && offer.offerStatus !== 'SOL' &&
                    <div className={"mx-3 w-full"}>
                        <div className="flex flex-row justify-between my-2">
                                <button type="submit" className=" rounded-lg text-center bg-frostdr hover:bg-frostdr/[0.7] w-1/2 p-2 mr-2 font-semibold text-white z-20" onClick={()=>navigate("/offer/"+offer.offerId+"/edit")}>
                                    Edit
                                </button>
                            <div className="rounded-lg text-center bg-nred hover:bg-nred/[0.7] text-white w-1/2 p-2 ml-2 z-30 text-white">
                                <button type="submit"  className="font-semibold text-white" onClick={()=>deleteOffer()/* post to delete method in Service */}>
                                    Delete
                                </button>
                            </div>
                        </div>
                        <div className="rounded-lg bg-nyellow hover:bg-nyellow/[0.6] py-3 px-5 text-l font-sans text-center cursor-pointer">
                            <button className="font-semibold text-white" onClick={pauseOffer}>
                                Pause offer
                            </button>
                        </div>
                    </div>
                }
                {
                    offer.offerStatus === 'PSE' &&
                        <button type="submit"  className="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer my-auto mx-auto" onClick={resumeOffer}>
                            Resume offer
                        </button>
                }
                {
                    offer.offerStatus === 'DEL' && <div className="bg-nred my-auto p-2 text-white text-center mx-auto">
                        Offer deleted
                    </div>
                }
                {
                    offer.offerStatus === 'SOL' &&  <div className="bg-gray-500 my-auto p-2 text-white text-center">
                        Offer Sold
                    </div>
                }
            </div>

        </div>



    );
};

export default OfferCardProfile;
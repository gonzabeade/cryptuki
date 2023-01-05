import React from 'react';
import OfferModel from "../../types/OfferModel";
import {useNavigate} from "react-router-dom";

type OfferCardProfileProps = {
    offer:OfferModel
}

const OfferCardProfile: React.FC<OfferCardProfileProps> = ({ offer }) =>{
        const navigate = useNavigate();
    return (
        <a href={offer.offerStatus !== 'DEL' && offer.offerStatus !== 'SOL'? "/seller/offer/"+offer.offerId : "/seller/#"} className={` cursor-pointer z-10 flex flex-col p-3 bg-[#FAFCFF] rounded-lg w-60 my-5 mx-auto ${ offer.offerStatus !== 'DEL' && offer.offerStatus !== 'SOL' ? 'hover:-translate-y-1 hover:scale-105 duration-200' : 'cursor-not-allowed' }`} >
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
            {
                offer.offerStatus !== 'DEL' && offer.offerStatus !== 'PSE' && offer.offerStatus !== 'SOL' &&
                <>
                    <div className="flex flex-row justify-between my-2">
                        <div className="rounded-lg text-center bg-frostdr hover:bg-frostdr/[0.7] w-1/2 p-2 mr-2">
                            <button type="submit" className="font-semibold text-white" onClick={()=>navigate("/offer/"+offer.offerId+"/edit")}>
                                Edit
                            </button>
                        </div>
                        <div className="rounded-lg text-center bg-nred hover:bg-nred/[0.7] text-white w-1/2 p-2 ml-2 z-30 text-white">
                            <button type="submit"  className="font-semibold text-white" onClick={()=>console.log("delete")/* post to delete method in Service */}>
                               Delete
                            </button>
                        </div>
                    </div>
                    <div className="rounded-lg bg-nyellow hover:bg-nyellow/[0.6] py-3 px-5 text-l font-sans text-center cursor-pointer">
                        <button type="submit" className="font-semibold text-white" onClick={()=>console.log("pause")/* post to pause method in Service */}>
                            Pause offer
                        </button>
                    </div>
                </>
            }
            {
                offer.offerStatus === 'PSE' &&
                <form
                    className="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer my-auto">
                    <button type="submit">
                       Resume offer
                    </button>
                </form>

            }
            {
                offer.offerStatus === 'DEL' && <div className="bg-nred my-auto p-2 text-white text-center">
                    Offer deleted
                </div>
            }
            {
                offer.offerStatus === 'SOL' &&  <div className="bg-gray-500 my-auto p-2 text-white text-center">
                  Offer Sold
                </div>
            }


        </a>

    );
};

export default OfferCardProfile;
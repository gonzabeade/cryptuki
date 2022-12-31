import React from 'react';
import OfferModel from "../../types/OfferModel";

type OfferCardProfileProps = {
    offer:OfferModel
}

const OfferCardProfile: React.FC<OfferCardProfileProps> = ({ offer }) =>{

    return (
        <a href="/" className={`z-10 flex flex-col p-3 bg-[#FAFCFF] rounded-lg w-60 my-5 mx-3 ${ offer.offerStatus != 'DEL' && offer.offerStatus != 'SOL' ? 'hover:-translate-y-1 hover:scale-105 duration-200' : 'cursor-not-allowed' }`}>
            <h1 className="text-center text-3xl font-semibold font-polard font-sans">
               Offer
                #{offer.offerId}
            </h1>

            <div className="flex flex-col mx-auto mt-5">
                <h1 className="font-bold text-lg text-center">
                    Accepted Range
                </h1>
                <p className="text-center">
                   0.1 BTC - 1 BTC
                </p>
            </div>
            <div className="flex flex-col mx-auto">
                <h1 className="font-bold text-lg">
                    Unit price
                </h1>
                <p className="text-center">
                   10000000 ARS
                </p>
            </div>
            <div className="flex flex-col mx-auto mb-5">
                <h1 className="font-bold text-lg">
                   Location
                </h1>
                <p className="text-center">
                    Balvanera
                </p>
            </div>
            {
                offer.offerStatus !== 'DEL' && offer.offerStatus!= 'PSE' && offer.offerStatus!=='SOL' &&
                <>
                    <div className="flex flex-row justify-between">
                        <form className="rounded-lg text-center bg-frostdr hover:bg-frostdr/[0.7] w-1/2 p-2 mr-2">
                            <button type="submit" className=" text-white">
                                Edit
                            </button>
                        </form>
                        <form className="rounded-lg text-center bg-nred hover:bg-nred/[0.7] text-white w-1/2 p-2 ml-2 z-30 text-white">
                            <button type="submit" >
                               Delete
                            </button>
                        </form>
                    </div>
                    <form className="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer">
                        <button type="submit">
                            Pause offer
                        </button>
                    </form>
                </>
            }
            {
                offer.offerStatus === 'PSE' &&
                <form
                    className="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer">
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
import React from 'react';
import TransactionModel from "../../types/TransactionModel";
import RatingStars from "../RatingStars";
import {Link} from "react-router-dom";

type OfferInformationForSellerProps = {
    trade:TransactionModel
}
const OfferInformationForSeller: React.FC<OfferInformationForSellerProps>= ({trade}) => {
    return (
        <div className="flex flex-col justify-center px-10">
            <div
                className="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between w-full mb-3 text-polar">
               Offer Information
            </div>
            <div className="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mb-12 ">
                <div className="flex font-sans h-fit w-full mt-2">
                    {
                        trade.status === 'sold' &&
                        <div className="font-semibold bg-gray-400 w-full text-white text-center p-2 rounded-lg">
                           Sold
                        </div>
                    }
                    {
                        trade.status === 'pending' &&
                        <div className=" font-semibold bg-nyellow  w-full text-white text-center p-2 rounded-lg">
                            Pending
                        </div>
                    }
                    {
                        trade.status === 'rejected' &&
                        <div className=" font-semibold bg-nred/[0.6] w-full text-white  text-center p-2 rounded-lg">
                            Rejected
                        </div>
                    }
                    {
                        trade.status === 'accepted' &&
                        <div className=" font-semibold bg-ngreen  w-full text-white  text-center p-2 rounded-lg">
                            Rejected
                        </div>
                    }
                </div>

                <div className="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                    <h1 className="mx-auto">
                        10
                        BTC
                        ⟶  1000000 ARS
                    </h1>
                </div>

                <div className="flex flex-col my-2">
                    <h1 className="font-bold font-roboto text-polar mx-auto text-center">Buyer:</h1>
                    <div className="flex mx-auto">
                        <h1 className=" text-lg font-sans font-semibold text-center">
                            mdedeu
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="font-sans font-semibold text-center text-gray-400">
                            mdedeu@itba.edu.ar
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="text-xs text-gray-400 font-sans font-semibold text-center">
                            1234241341
                        </h1>
                    </div>
                    <RatingStars rating={1.3}/>
                </div>

                {trade.status === 'sold' &&
                    <a className="mx-auto bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40"
                       href="/trade/:id/receipt">
                        Help
                    </a>
                }
                {trade.status === 'pending' &&
                    <div className="flex flex-row">
                        <form method="post" className="flex justify-center mx-auto my-3">
                            <button type="submit"
                                    className="font-bold bg-red-400 text-white p-3  rounded-lg font-sans mr-4">
                                Reject
                            </button>
                        </form>

                        <form className="flex justify-center mx-auto my-3">
                            <button type="submit"
                                    className="font-bold bg-ngreen text-white p-3 rounded-lg font-sans ">
                                Accept
                            </button>
                        </form>
                    </div>
                }

                {
                    trade.status === 'ACCEPTED' &&


                    <form className="flex justify-center mx-auto my-3">
                        <button type="submit"
                                className="font-bold w-fit bg-frostdr text-white p-3 rounded-lg font-sans mx-auto">
                            Mark as sold
                        </button>
                    </form>
                }
                {
                    trade.status !== 'accepted' && trade.status !== 'pending' &&
                    <div className="flex h-2/5 my-2"/>
                }
            </div>

            <div className="mx-auto">
                <Link to="/" className=" cursor-pointer  font-bold bg-frost px-6 py-3  rounded-lg text-white">
                   Back
                </Link>
            </div>
        </div>

    );
};

export default OfferInformationForSeller;
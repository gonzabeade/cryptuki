import React from 'react';
import OfferCardProfile from "../../components/OfferCardProfile";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";

type SellerOfferDashboardProps ={
    offer:OfferModel,
    selected:string
}
const SellerOfferDashboard:React.FC<SellerOfferDashboardProps> = ({offer, selected}) => {
    return (<>
            <div className="flex flex-row h-full w-full px-20 my-10">
                <div className="flex flex-col h-3/5 w-1/5 pr-2">
                    <div className="flex flex-col w-full py-3 rounded-lg px-5 pt-4 rounded-lg bg-[#FAFCFF]">
                        <h1 className="font-sans w-full mx-auto text-center text-2xl font-bold">
                            Trade proposals received
                        </h1>
                    </div>
                    <OfferCardProfile offer={offer}/>

                    <a href="/"
                       className="rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
                        Back
                    </a>
                </div>
                <div className="flex flex-col w-4/5">
                    {/*TODO componente */}
                    <div className="flex flex-row  rounded-lg px-5 rounded-lg  justify-between">
                        <div
                            className="flex mr-5 bg-white rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto w-full ">
                                <p className={`py-2 px-4 font-bold text-polar text-center ${selected === 'all' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    All
                                </p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-nyellow rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'pending' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Pending</p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-ngreen rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'accepted' ? 'decoration-frostdr underline underline-offset-8' : 'text-l'}`}>
                                    Accepted</p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-nred rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'rejected' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Rejected</p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-gray-200 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'sold' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Sold </p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-blue-400 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2  px-4 font-bold text-polar text-center ${selected === 'deleted' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Deleted by you</p>
                            </a>
                        </div>
                    </div>
                    <div className="flex flex-col">
                        <div className="flex flex-wrap pl-3 mt-10">
                            {/*TODO foreach de trades, con el componente de OfferInformationForSeller*/}
                        </div>
                        <h1 className="mx-auto text-center">
                            <Paginator totalPages={10} actualPage={1} callback={() => console.log("a")}/>
                        </h1>
                    </div>
                </div>
            </div>
        </>
    );
};

export default SellerOfferDashboard;
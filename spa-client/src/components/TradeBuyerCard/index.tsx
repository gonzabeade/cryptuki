import React from 'react';
import TransactionModel from "../../types/TransactionModel";

type TradeCardProp = {
    trade:TransactionModel,
    unSeenMessages:number
}

const TradeBuyerCard = ({trade, unSeenMessages}:TradeCardProp) => {

    return (
        <div className="shadow-xl flex rounded-lg  py-5 px-12 bg-[#FAFCFF] mt-3 justify-start">
            <div className="w-3/4 flex flex-row">
                <div className="flex flex-col font-sans my-auto mr-5">
                    {trade.status === "pending" &&
                        <div className="bg-nyellow  w-40 text-white  text-center p-2">Pending</div>}
                    {trade.status === "rejected" &&
                        <div className="bg-nred/[0.6] w-40 text-white  text-center p-2">Rejected</div>}
                    {trade.status === "accepted" &&  <div className="bg-ngreen w-40 text-white text-center p-2">Accepted</div>
                    }
                    {
                        trade.status === "sold" && <div className="bg-gray-400 w-40 text-white text-center p-2">Sold </div>
                    }
                    {
                        trade.status === "deleted" &&     <div className="bg-blue-400 w-40 text-white text-center p-2">Deleted</div>
                    }
                </div>

                <div className=" flex flex-col font-sans justify-center mr-5">
                    {
                        trade.status === "sold" &&  <h1 className="font-sans">You bought for: </h1>
                    }
                    {
                        trade.status !== "sold" &&  <h1 className="font-sans">You would pay: </h1>
                    }
                    <h3 className="font-sans font-semibold">{trade.amount * trade.offer.unitPrice} ARS</h3>
                </div>

                <div className="flex flex-col font-sans justify-center ">
                    <h1 className="font-sans">In exchange for: </h1>
                    <div className="flex">
                        <h1 className="text-xl font-sans font-bold">{trade.amount / trade.offer.unitPrice} </h1>
                        <h1 className="text-xl font-sans font-bold mx-2">{trade.offer.cryptoCode}</h1>
                        <img src={'/'+trade.offer.cryptoCode+'.png'} alt={trade.offer.cryptoCode} className="w-7 h-7 mx-auto"/>
                    </div>
                </div>

            </div>

            <div className="w-1/4 flex flex-row">
                <div className="flex my-auto ml-1">
                    {trade.status != 'sold' && trade.status != 'rejected' && trade.status !== 'deleted' &&
                        <a className="bg-gray-200 text-polard hover:border-polard hover: border-2 p-2 h-16 justify-center rounded-md font-sans text-center w-40" href={"/trade?tradeId="+trade.id}>
                        Resume trade
                        </a>
                    }
                    {trade.status === "sold" &&
                        <a className="bg-gray-200 text-polard hover:border-polard hover: border-2 p-2 h-16 justify-center rounded-md font-sans text-center w-40" href={"/trade/"+ trade.id
                        +"/receipt"}>
                           Help
                        </a>
                    }
            </div>

            <div className="ml-4 flex flex-row  align-middle my-auto font-sans">
                {unSeenMessages !== 0 &&
                    <>
                        <a href={"/trade?tradeId" + trade.id} className="flex flex-row">
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 mx-auto" fill="none"
                                 viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                <path strokeLinecap="round" strokeLinejoin="round"
                                      d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>
                            </svg>
                            <div className="text-frost align-middle bg-frost rounded-full w-2 h-2"></div>
                        </a>
                        <div
                            className="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">
                            <p className="text-xs">
                                {unSeenMessages}
                            </p>
                        </div>
                    </>

                }
        </div>
</div>

</div>
);
};

export default TradeBuyerCard;
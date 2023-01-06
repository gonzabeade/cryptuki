import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import {CheckCircleIcon, XCircleIcon} from "@heroicons/react/24/outline";
import UserModel from "../../types/UserModel";
import OfferModel from "../../types/OfferModel";

type TransactionListProps = {
    transactions:TransactionModel[],
}

const TRADE_STATUS = new Map([
    ['sold', {icon: <CheckCircleIcon className="text-gray-400 w-6 h-6"/>, title: "Title", subtitle: "You've marked this trade as sold  "}],
    ['rejected', {icon: <XCircleIcon className="text-nred w-6 h-6"/>, title: "Title", subtitle: "You've rejected this trade proposal "}]

    ])
const TransactionList:React.FC<TransactionListProps> = ({transactions}) => {
    const [buyer, setBuyer] = useState<UserModel>();
    const [offer, setOffer] = useState<OfferModel>();

    async  function fetchOffer(){

    }
    useEffect(()=>{
        fetchOffer()
    },[])

    async  function fetchBuyer(){

    }
    useEffect(()=>{
        fetchBuyer()
    },[])
    return (
        <div className="mt-5">
            <div className="py-4 bg-white rounded-lg shadow-md">
                <div className="flex justify-between items-center mb-2 px-4 pt-2">
                    <h5 className="text-xl font-bold leading-none text-polar mx-auto">Last transactions</h5>
                </div>
                <div className="px-4">
                    <ul className="divide-y divide-gray-200">
                        {transactions.map(transaction => (
                            <li className="py-2">
                                <a className="flex items-center space-x-4 hover:bg-gray-100 rounded-lg p-1 cursor-pointer"
                                   href={"/chat/"+transaction.tradeId}>
                                    <div className="flex-shrink-0">
                                        {TRADE_STATUS.get(transaction.status as string)?.icon}
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <div className="flex flex-row justify-between">
                                            <p className="text-sm font-medium text-polar-600 truncate">{buyer?.username}</p>
                                            <h1 className="text-xs my-auto  text-polar truncate">{transaction.status} for {transaction.buyingQuantity + ' ' + offer?.cryptoCode} </h1>
                                        </div>
                                        <p className="text-sm text-gray-500 truncate">{TRADE_STATUS.get(transaction.status as string)?.subtitle }
                                        </p>
                                    </div>
                                </a>
                            </li>
                        ))
                        }
                </ul>
            </div>
        </div>
        </div>
    );
};

export default TransactionList;
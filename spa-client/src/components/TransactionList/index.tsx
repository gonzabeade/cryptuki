import React from 'react';
import TransactionModel from "../../types/TransactionModel";

type TransactionListProps = {
    transactions:TransactionModel[],
}
const TransactionList:React.FC<TransactionListProps> = ({transactions}) => {
    return (
        <div className="mt-5">
            <div className="py-4 bg-white rounded-lg shadow-md">
                <div className="flex justify-between items-center mb-2 px-4 pt-2">
                    <h5 className="text-xl font-bold leading-none text-polar">Last transactions</h5>
                </div>
                <div className="px-4">
                    <ul role="list" className="divide-y divide-gray-200">
                        {transactions.map(transaction => (
                            <li className="py-2">
                                <a className="flex items-center space-x-4 hover:bg-gray-100 rounded-lg p-1 cursor-pointer"
                                   href="/chat?tradeId=${trade.tradeId}">
                                    <div className="flex-shrink-0">
                                        {/*{transaction.icon}*/}
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <div className="flex flex-row justify-between">
                                            <p className="text-sm font-medium text-polar-600 truncate">buyer_username</p>
                                            <h1 className="text-sm  text-polar truncate">Pending , quantity,
                                                cripto </h1>
                                        </div>
                                        <p className="text-sm text-gray-500 truncate">Te hizo una oferta </p>
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
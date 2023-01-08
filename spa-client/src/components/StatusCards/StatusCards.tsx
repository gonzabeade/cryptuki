import React, {useState} from 'react';
import {TRADE_STATUS} from "../../common/constants";


type StatusCardsProps = {
    active:string,
    callback:Function
}

const StatusCards:React.FC<StatusCardsProps> = ({active,  callback}) => {

    const [activeStatus, setActiveStatus] = useState<string>(active);

    async function fetchTrades(status:string){
        await callback(status);
        setActiveStatus(status);
    }
    return (
        <nav className="border-gray-200 py-3 h-20">
            <div className="container mx-auto my-auto px-20 h-full">
                <ol className="flex justify-center mt-4">
                    <li className="bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades(TRADE_STATUS.Pending)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === TRADE_STATUS.Pending ?'decoration-frostdr underline underline-offset-8':' '}`}>
                                Pending
                            </p>
                        </button>
                    </li>
                    <li className="bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades(TRADE_STATUS.Accepted)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === TRADE_STATUS.Accepted ?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Accepted
                            </p>
                        </button>
                    </li>
                    <li className="bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades(TRADE_STATUS.Rejected)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === TRADE_STATUS.Rejected ?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Rejected
                            </p>
                        </button>
                    </li>
                    <li className="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades(TRADE_STATUS.Sold)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === TRADE_STATUS.Sold ? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                Completed
                            </p>
                        </button>
                    </li>
                </ol>
            </div>
        </nav>
    );
};

export default StatusCards;
import React, {useState} from 'react';
import {toast} from "react-toastify";

type StatusCardsProps = {
    active:string,
    base_url:string,
    callback:Function
}
const StatusCardsSeller:React.FC<StatusCardsProps> = ({active, base_url, callback}) => {

    const [activeStatus, setActiveStatus] = useState<string>(active);

    async function fetchOffers(status:string){
            await callback(status);
            setActiveStatus(status);
    }
    return (
        <nav className="border-gray-200 py-3 h-20 mx-auto">
            <div className="container mx-auto my-auto px-20 h-full">
                <ol className="flex justify-center mt-4">
                    <li className="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers('ALL')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === 'ALL'? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                All
                            </p>
                        </button>
                    </li>
                    <li className="bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers('ACCEPTED')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === 'ACCEPTED'?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Active
                            </p>
                        </button>
                    </li>
                    <li className="bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers('PENDING')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === 'PENDING'?'decoration-frostdr underline underline-offset-8':' '}`}>
                                Paused
                            </p>
                        </button>
                    </li>
                    <li className="bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers('REJECTED')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === 'REJECTED'?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Deleted
                            </p>
                        </button>
                    </li>
                    <li className="bg-gray-200 rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers('SOLD')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === 'SOLD'? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                Completed
                            </p>
                        </button>
                    </li>
                </ol>
            </div>
        </nav>
    );
};

export default StatusCardsSeller;
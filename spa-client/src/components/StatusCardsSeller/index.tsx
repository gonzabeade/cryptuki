import React, {useState} from 'react';

type StatusCardsProps = {
    active:string,
    base_url:string,
    callback:Function
}
//TODO status strings
const StatusCardsSeller:React.FC<StatusCardsProps> = ({active, base_url, callback}) => {

    const [activeStatus, setActiveStatus] = useState<string>(active);

    async function fetchTrades(status:string){
        await callback(status);
        setActiveStatus(status);
    }
    return (
        <nav className="border-gray-200 py-3 h-20 mx-auto">
            <div className="container mx-auto my-auto px-20 h-full">
                <ol className="flex justify-center mt-4">
                    <li className="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades('all')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === 'all'? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                All
                            </p>
                        </button>
                    </li>
                    <li className="bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades('accepted')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === 'accepted'?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Active
                            </p>
                        </button>
                    </li>
                    <li className="bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades('pending')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === 'pending'?'decoration-frostdr underline underline-offset-8':' '}`}>
                                Paused
                            </p>
                        </button>
                    </li>
                    <li className="bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades('rejected')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === 'rejected'?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Deleted
                            </p>
                        </button>
                    </li>
                    <li className="bg-gray-200 rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchTrades('sold')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === 'sold'? 'decoration-frostdr underline underline-offset-8':' '}`}>
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
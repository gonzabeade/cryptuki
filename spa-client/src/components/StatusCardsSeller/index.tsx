import React, {useEffect, useState} from 'react';
import {toast} from "react-toastify";
import {OFFER_STATUS} from "../../common/constants";
import i18n from "../../i18n";

type StatusCardsProps = {
    active:string,
    callback:Function
}
const StatusCardsSeller:React.FC<StatusCardsProps> = ({active, callback}) => {

    const [activeStatus, setActiveStatus] = useState<string>(active);

    async function fetchOffers(status:string){
            await callback(status);
            setActiveStatus(status);
    }
    useEffect(()=>{
        setActiveStatus(active);
    }, [active])
    return (
        <nav className="border-gray-200 py-3 h-20 mx-auto">
            <div className="container mx-auto my-auto px-20 h-full">
                <ol className="flex justify-center mt-4">
                    <li className="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers('ALL')}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === 'ALL'? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                {i18n.t('all')}
                            </p>
                        </button>
                    </li>
                    <li className="bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers(OFFER_STATUS.Pending)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === OFFER_STATUS.Pending ?'decoration-frostdr underline underline-offset-8':' '} `}>
                                {i18n.t('activeOffers')}
                            </p>
                        </button>
                    </li>
                    <li className="bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers(OFFER_STATUS.PausedBySeller)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === OFFER_STATUS.PausedBySeller ?'decoration-frostdr underline underline-offset-8':' '}`}>
                                {i18n.t('pausedOffers')}
                            </p>
                        </button>
                    </li>
                    <li className="bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers(OFFER_STATUS.Deleted)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${activeStatus === OFFER_STATUS.Deleted?'decoration-frostdr underline underline-offset-8':' '} `}>
                                {i18n.t('deletedOffers')}
                            </p>
                        </button>
                    </li>
                    <li className="bg-gray-200 rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <button onClick={()=>fetchOffers(OFFER_STATUS.Sold)}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ activeStatus === OFFER_STATUS.Sold? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                {i18n.t('soldOffers')}
                            </p>
                        </button>
                    </li>
                </ol>
            </div>
        </nav>
    );
};

export default StatusCardsSeller;
import React, {useEffect, useState} from "react";
import {CheckCircleIcon, FaceFrownIcon, InformationCircleIcon, TrashIcon} from "@heroicons/react/24/outline";
import i18n from "../../i18n";

type TradeStatusAlertProps = {
        status:string
}
type TradeStatusAlertState = {
    icon: JSX.Element,
    color: string,
    accentColor: string,
    title: string,
    subtitle: string,
}

const ALERTS_MAP = new Map<string, TradeStatusAlertState>(
    [
        ["PENDING", {
            icon: <InformationCircleIcon className="w-12 h-12 text-nyellowd"/>,
            color: "#EBCB8B",
            accentColor: "#936f26",
            title: i18n.t('proposalPending'),
            subtitle: i18n.t('waitForTheSeller'),
        }
        ],
        ['REJECTED', {
            icon: <FaceFrownIcon className="w-12 h-12 text-nredd"/>,
            color: "#BF616A",
            accentColor: "#ad2b36",
            title: i18n.t('proposalRejected'),
            subtitle: i18n.t('tradeRejectedDetail'),
        }],
        ['ACCEPTED', {
            icon: <CheckCircleIcon className="w-12 h-12 text-ngreend"/>,
            color: "#A3BE8C",
            accentColor: "#47523e",
            title: i18n.t('proposalAccepted'),
            subtitle: i18n.t('offerAccepted'),
        }],
        ['DELETED', {
            icon: <TrashIcon className="w-12 h-12 text-frostdr"/>,
            color: "#88C0D0",
            accentColor: "#5E81AC",
            title: i18n.t('proposalTakenBack'),
            subtitle: i18n.t('tradeDeletedDetail')
        }]
    ]
);


const TradeStatusAlert: React.FC<TradeStatusAlertProps> = ({status}) => {

    const [tradeStatus, setTradeStatus] = useState<TradeStatusAlertState>(
        {
            icon: <InformationCircleIcon width={24} height={24} color={"text-red-600"}/>,
            color: "text-red-600",
            accentColor: "red-800",
            title: "Error",
            subtitle: "Something went wrong",
        }
    );

    function setTradeStatusOnStart(){
       setTradeStatus(ALERTS_MAP.get(status)!);
    }

    useEffect(()=>{
        setTradeStatusOnStart();
    },[status])

    return (
        <div className={`flex p-5 text-center rounded-lg mx-auto mt-10 mb-2 border-2`} style={{ backgroundColor: tradeStatus?.color, border: "1px solid " + tradeStatus?.accentColor }}>
            {tradeStatus?.icon}
            <div className="flex flex-col mx-4">
                <p className={` text-left text-xl`} style={{
                    color: tradeStatus?.accentColor
                }}><b>
                    {tradeStatus?.title}
                </b></p>
                <p>
                    {tradeStatus?.subtitle}
                </p>
            </div>
        </div>
    );
}
export default TradeStatusAlert;
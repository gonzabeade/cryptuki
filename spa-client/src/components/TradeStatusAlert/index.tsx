import React, {useState} from "react";
import {CheckCircleIcon, FaceFrownIcon, InformationCircleIcon} from "@heroicons/react/24/outline";

type TradeStatusAlertProps = {
        status:string|undefined
}

let TRADE_STATUS = new Map([
        ['PENDING', {
            icon: <InformationCircleIcon className="w-12 h-12 text-nyellowd"/>,
            color: "#EBCB8B",
            accentColor: "#936f26",
            title: "Your trade proposal was sent to the seller!",
            subtitle: "Wait for the seller to accept your trade proposal",
        }
        ],
    ['REJECTED',  {
        icon: <FaceFrownIcon className="w-12 h-12 text-nredd"/>,
        color: "#EBCB8B",
        accentColor: "#ad2b36",
        title: "This proposal has been rejected",
        subtitle: "Make a new trade proposal if you still want to buy from this seller",
    }],
    ['ACCEPTED',  {
        icon: <CheckCircleIcon className="w-12 h-12 text-ngreend"/>,
        color: "#A3BE8C",
        accentColor: "#47523e",
        title: "The seller accepted your trade proposal",
        subtitle: "Meet the seller to make the trade",
    }]
    ]
);


const TradeStatusAlert: React.FC<TradeStatusAlertProps> = ({status}) => {
    const [tradeStatus] = useState(TRADE_STATUS.get(status as string) ||
        {
            icon: <InformationCircleIcon width={24} height={24} color={"red-600"}/>,
            color: "red-600",
            accentColor: "red-800",
            title: "Error",
            subtitle: "Something went wrong",
        }
    );

    return (
        <div className={`flex p-5 text-center rounded-lg mx-auto mt-10 mb-2 border-2`} style={{ backgroundColor: tradeStatus.color, border: "1px solid " + tradeStatus.accentColor }}>
            {tradeStatus.icon}
            <div className="flex flex-col mx-4">
                <p className={` text-left text-xl`} style={{
                    color: tradeStatus.accentColor
                }}><b>
                    {tradeStatus.title}
                </b></p>
                <p>
                    {tradeStatus.subtitle}
                </p>
            </div>
        </div>
    );
}
export default TradeStatusAlert;
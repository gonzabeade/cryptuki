import React, {useState} from "react";
import {InformationCircleIcon} from "@heroicons/react/24/outline";
import Trade from "../../views/Trade";

type TradeStatusAlertProps = {
        status:string
}

type TradeStatus = {
    icon:JSX.Element,
    color:string,
    accentColor:string
    title:string,
    subtitle:string
}

let TRADE_STATUS = new Map([
        ['pending', {
            icon: <InformationCircleIcon width={24} height={24} color={"nyellowd"}/>,
            color: "nyellow",
            accentColor: "nyellowd",
            title: "Your trade proposal was sent to the seller!",
            subtitle: "Wait for the seller to accept your trade proposal",
        }
        ],
    ['rejected',  {
        icon: <InformationCircleIcon width={24} height={24} color={"nyellowd"}/>,
        color: "nyellow",
        accentColor: "nyellowd",
        title: "Your trade proposal was sent to the seller!",
        subtitle: "Wait for the seller to accept your trade proposal",
    }],
    ['accepted',  {
        icon: <InformationCircleIcon width={24} height={24} color={"nyellowd"}/>,
        color: "nyellow",
        accentColor: "nyellowd",
        title: "Your trade proposal was sent to the seller!",
        subtitle: "Wait for the seller to accept your trade proposal",
    }],
    ['deleted',  {
        icon: <InformationCircleIcon width={24} height={24} color={"nyellowd"}/>,
        color: "nyellow",
        accentColor: "nyellowd",
        title: "Your trade proposal was sent to the seller!",
        subtitle: "Wait for the seller to accept your trade proposal",
    }],
    ['sold',  {
        icon: <InformationCircleIcon width={24} height={24} color={"nyellowd"}/>,
        color: "nyellow",
        accentColor: "nyellowd",
        title: "Your trade proposal was sent to the seller!",
        subtitle: "Wait for the seller to accept your trade proposal",
    }]
    ]
);


const TradeStatusAlert: React.FC<TradeStatusAlertProps> = ({status}) => {
    const [tradeStatus] = useState(TRADE_STATUS.get(status) ||
        {
            icon: <InformationCircleIcon width={24} height={24} color={"red-600"}/>,
            color: "red-600",
            accentColor: "red-800",
            title: "Error",
            subtitle: "Check your connection",
        }
    );

    return (
        <div className={`flex p-5 text-center rounded-lg mx-auto my-10 border-2`} style={{ backgroundColor: tradeStatus.color, border: "1px solid " + tradeStatus.accentColor }}>
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
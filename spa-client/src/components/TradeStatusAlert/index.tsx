import React from "react";
import {InformationCircleIcon} from "@heroicons/react/24/outline";

type TradeStatusAlertProps =
    {
        icon:JSX.Element,
        color:string,
        accentColor:string
        title:string,
        subtitle:string
    }

const TradeStatusAlert: React.FC<TradeStatusAlertProps> = ({icon,color, accentColor,title, subtitle}) => {
    return (
        <div className={`flex p-5 text-center rounded-lg mx-auto border-2`} style={{ backgroundColor: color, border: "1px solid " + accentColor }}>
            {icon}
            <div className="flex flex-col mx-4">
                <p className={` text-left text-xl`} style={{
                    color:accentColor
                }}><b>
                    {title}
                </b></p>
                <p>
                    {subtitle}
                </p>
            </div>
        </div>
    );
}
export default TradeStatusAlert;
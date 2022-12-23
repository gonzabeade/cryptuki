import React from "react";

type TradeStatusAlertProps =
    {
        icon:string,
        color:string,
        accentColor:string
        title:string,
        subtitle:string
    }

const TradeStatusAlert: React.FC<TradeStatusAlertProps> = ({icon,color, accentColor,title, subtitle}) => {
    return (
        <div className={`flex bg-${color} p-5 text-center rounded-lg mx-auto border-2 border-[${accentColor}]`}>
            <div className="my-auto">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-10 w-10" fill="none" viewBox="0 0 24 24"
                     stroke={`${accentColor}`} strokeWidth="2">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                {/*icon*/}
            </div>
            <div className="flex flex-col mx-4">
                <p className={`text-${accentColor} text-left text-xl`}><b>
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
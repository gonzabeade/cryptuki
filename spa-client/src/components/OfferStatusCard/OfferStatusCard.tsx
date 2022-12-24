import React from 'react';

type OfferStatusCardProps = {
    color:string,
    message:string,
    active:boolean
}

const OfferStatusCard:React.FC<OfferStatusCardProps> = ({color, message, active}) => {
    return (
        <div className=" rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer" style={{ backgroundColor:color}}>
            <a href={`/paw-2022a-01/buyer/?status=${message}`}>
                <p className={"py-2 pr-4 pl-3 font-bold text-polar" + active? "underline underline-offset-2": " "}>
                    {message}
                </p>
            </a>
        </div>
    );
};

export default OfferStatusCard;
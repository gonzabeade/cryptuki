import React from 'react';

type StatusCardsProps = {
    active:string,
    base_url:string
}

const StatusCards:React.FC<StatusCardsProps> = ({active, base_url}) => {
    return (
        <nav className="border-gray-200 py-3 h-20">
            <div className="container mx-auto my-auto px-20 h-full">
                <ol className="flex justify-center mt-4">
                    <li className="bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <a href={`${base_url} +/?status=PENDING  `}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${active =='pending'?'decoration-frostdr underline underline-offset-8':' '}`}>
                                Pending
                            </p>
                        </a>
                    </li>
                    <li className="bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <a href={`${base_url} +/?status=ACCEPTED  `}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${active =='accepted'?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Accepted
                            </p>
                        </a>
                    </li>
                    <li className="bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <a href={`${base_url} +/?status=REJECTED  `}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${active == 'rejected'?'decoration-frostdr underline underline-offset-8':' '} `}>
                                Rejected
                            </p>
                        </a>
                    </li>
                    <li className="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200 hover:cursor-pointer">
                        <a href={`${base_url} +/?status=SOLD`}>
                            <p className={`py-2 pr-4 pl-3 font-bold text-polar ${ active == 'sold'? 'decoration-frostdr underline underline-offset-8':' '}`}>
                                Sold
                            </p>
                        </a>
                    </li>
                </ol>
            </div>
        </nav>
    );
};

export default StatusCards;
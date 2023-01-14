import React from 'react';
import {ComplainModel} from "../../types/ComplainModel";

type ComplainCardProfileProps = {
    complain:ComplainModel
}
//TODO: FETCH ONLY PENDING COMPLAINTS. ?

const ComplainCard: React.FC<ComplainCardProfileProps> = ({ complain}) => {
    return <div>
        <div className="flex flex-col">
            <div className="flex flex-col my-auto mx-7">
                <h1 className="font-sans font-polard text-xl font-semibold">
                    Reclamo
                    <b>
                        #{complain.complainId}
                    </b>
                </h1>
                <h3 className="text-gray-300">
                    Efectuado el {complain.date.toDateString()}
                </h3>
            </div>
            <div className="flex flex-col  my-auto mx-7">
                <h1 className="font-sans font-polard text-lg font-semibold">
                    Usuario
                    : <b>
                    {complain.complainer}
                </b>
                </h1>
                <h3 className="text-gray-600 w-60 overflow-y-hidden truncate">
                    Comentario
                    : {complain.comments}</h3>
            </div>
        </div>
            <div className="flex flex-row my-3 mx-auto">
                    <a href='/admin/complaint/${complain.complainId}'
                       className=" text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto">
                        Ver
                    </a>
            </div>
    </div>
};

export default ComplainCard;
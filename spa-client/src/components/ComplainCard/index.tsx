import React from 'react';
import {ComplainModel} from "../../types/ComplainModel";
import useUserService from "../../hooks/useUserService";

type ComplainCardProfileProps = {
    complain:ComplainModel
}

const ComplainCard: React.FC<ComplainCardProfileProps> = ({ complain}) => {
    const userService = useUserService();



    return <div className="flex flex-col bg-white shadow-lg rounded-lg p-4 w-80 h-52 mx-3 my-3">
            <div className="flex flex-col my-auto mx-7">
                <h1 className="font-sans font-polard text-xl font-semibold">
                    Reclamo
                    <b>
                        #{complain.complainId}
                    </b>
                </h1>
                <h3 className="text-gray-500">
                    Efectuado el {complain.date.toString()}
                </h3>
            </div>
            <div className="flex flex-col  my-auto mx-7">
                <h1 className="font-sans font-polard text-lg font-semibold">
                    Usuario
                    : <b>
                    {userService.getUsernameFromURI(complain.complainer)}
                </b>
                </h1>
                <h3 className="text-gray-600 w-60 overflow-y-hidden truncate">
                    Comentario
                    : {complain.comments}</h3>
            </div>
            <div className="flex flex-row my-3 mx-auto">
                <a href={`/admin/complaint/${complain.complainId}`}
                   className=" cursor-pointer text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto">
                    Ver
                </a>
            </div>
    </div>
};

export default ComplainCard;
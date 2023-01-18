import React from 'react';
import KycPreview from "../../components/KycPreview/KycPreview";

const KycLanding = () => {

    return (
        <div className="flex flex-col ml-80 my-10 h-screen w-screen">
            <h1 className="font-sans text-4xl font-bold">Validar identidades</h1>
            <div className="flex flex-wrap w-full mt-3">
                <div className="flex flex-col bg-white shadow rounded-lg p-3 m-5 font-sans font-bold">
                    <KycPreview/>
                </div>
            </div>

            //TODO: harcodeado.
            <div className="flex flex-col">
                <div>
                    <div className="flex flex-row mx-40 justify-center ">
                        <div className="my-auto">
                        </div>
                        <a href="#"
                           className="bg-stormd border-2 border-polard active:text-white-400 px-3 py-1 mx-4 my-5 rounded-full ">1</a>
                        <div className="my-auto">
                        </div>
                    </div>
                </div>
                <h1 className="mx-auto text-gray-400 mx-auto">Total de páginas: 1</h1>
            </div>
        </div>
    );
};

export default KycLanding;
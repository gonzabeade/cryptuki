import React from 'react';

const KycPreview = () => {
    return (
        <div className="flex flex-col bg-white shadow rounded-lg p-3 m-5 font-sans font-bold">
            <div className="w-full mt-2 text-xl text-start"><b>Usuario:</b> holahola</div>
            <div className="w-full mt-2 text-xl text-start"><b>Fecha:</b> 2023-01-17</div>
            <div className="mx-auto my-3">
                <a href="/paw-2022a-01/admin/kyc/holahola"
                   className=" text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto">Ver</a>
            </div>
        </div>
    );
};

export default KycPreview;
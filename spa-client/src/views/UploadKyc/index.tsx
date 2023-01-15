import React from 'react';
import KycForm from "../../components/KycForm/kycForm";


const UploadKyc = () => {
    return (
        <div className="flex flex-row divide-x w-full">
            <div className="flex flex-col w-full">
                <div className="flex">
                    <h1 className="mx-auto my-10 text-2xl font-semibold font-sans text-polar">
                       Upload your Kyc
                    </h1>
                </div>
                <div className="flex items-center justify-center">
                    <UploadKyc/>
                </div>
            </div>
        </div>
    );
};

export default UploadKyc;
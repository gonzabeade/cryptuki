import React from 'react';
import KycForm from "../../components/KycForm/kycForm";


const UploadKyc = () => {
    return (
        <div className="flex flex-row divide-x w-full">
            <div className="flex flex-col w-full">
                <div className="flex items-center justify-center">
                    <KycForm/>
                </div>
            </div>
        </div>
    );
};

export default UploadKyc;
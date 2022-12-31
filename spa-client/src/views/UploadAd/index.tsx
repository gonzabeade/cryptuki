import React from 'react';
import UploadForm from "../../components/UploadForm";

const UploadAd = () => {
    return (
        <div className="flex flex-row divide-x w-full">
            <div className="flex flex-col w-full">
                <div className="flex">
                    <h1 className="mx-auto my-10 text-2xl font-semibold font-sans text-polar">
                       Upload your Offer
                    </h1>
                </div>
                <div className="flex items-center justify-center">
                    <UploadForm/>
                </div>
            </div>
        </div>
    );
};

export default UploadAd;
import React from 'react';
import EditOfferForm from "../../components/EditOfferForm";
import i18n from "../../i18n";


const EditOffer = () => {
    return (
        <div className="flex flex-row divide-x w-full">
            <div className="flex flex-col w-full">
                <div className="flex">
                    <h1 className="mx-auto my-10 text-2xl font-semibold font-sans text-polar">
                        {i18n.t('modifyOffer')}
                    </h1>
                </div>
                <div className="flex items-center justify-center">
                    <EditOfferForm/>
                </div>
            </div>
        </div>
    );
};

export default EditOffer;
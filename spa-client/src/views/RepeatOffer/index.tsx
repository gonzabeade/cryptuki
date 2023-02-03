import React from 'react';
import i18n from "../../i18n";
import RepeatOfferForm from "../../components/RepeatOfferForm";
import {useParams} from "react-router-dom";

const RepeatOffer = () => {
    const params = useParams();

    return (
        <div className="flex flex-row divide-x w-full">
            <div className="flex flex-col w-full">
                <div className="flex">
                    <h1 className="mx-auto my-10 text-2xl font-semibold font-sans text-polar">
                        {i18n.t('repeatOffer')}
                    </h1>
                </div>
                <div className="flex items-center justify-center">
                    <RepeatOfferForm offerId={Number(params.id)}/>
                </div>
            </div>
        </div>
    );
};

export default RepeatOffer;
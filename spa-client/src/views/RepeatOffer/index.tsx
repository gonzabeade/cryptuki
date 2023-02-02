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
                    <h1 className="p-3 bg-frostdr text-white font-roboto font-bold mx-auto rounded-lg my-2 flex flex-row">
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
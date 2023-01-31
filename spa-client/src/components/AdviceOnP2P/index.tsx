import React from 'react';
import i18n from "../../i18n";

const AdviceOnP2P = () => {
    return (
        <div className="flex flex-col mx-auto rounded-lg py-10">
            <div className="flex flex-row mx-auto">
                <h1 className="text-polard text-2xl font-bold text-center mr-5">
                    {i18n.t('adviceOnP2P')}
                </h1>
            </div>
            <ul className="mx-20 list-decimal ">
                <li className="mt-5 text-polard text-lg">
                    {i18n.t('advice1')}
                </li>
                <li className="mt-5 text-polard text-lg">
                    <p>
                        {i18n.t('advice2')}
                    </p>
                </li>
                <li className="mt-5 text-polard text-lg">
                    <p>
                        {i18n.t('advice3')}
                    </p>
                </li>
                <li className="mt-5 text-polard text-lg">
                    <p>
                        {i18n.t('advice4')}
                    </p>
                </li>
            </ul>
            {/*<button className="p-3 bg-frostdr font-bold font-roboto mx-auto rounded-lg text-white" onClick={()=>document.getElementsByTagName("h1")[0].click()} >Ok, continue</button>*/}
        </div>
    );
};

export default AdviceOnP2P;
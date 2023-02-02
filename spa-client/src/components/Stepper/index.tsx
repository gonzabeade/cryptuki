import React from 'react';
import './styles.css';
import i18n from "../../i18n";
type StepperProps = {
    active:number
}
const Stepper:React.FC<StepperProps> = ({active}) => {
    return (
        <div className="flex flex-row mx-auto">
            <div className="col-span-12 block">
                <div className="wrapper-progressBar">
                    <ul className="progressBar">
                        <li className={`${active >= 0  ? 'active': 'text-gray-400'}`}>
                            {i18n.t('tradeProposalSent') + ' ' + i18n.t('waitForTheSeller')}
                        </li>
                        <li className={`${active >= 1  ? 'active': 'text-gray-400'}`}>
                            {i18n.t('proposalAccepted')} {i18n.t('furtherInstructions')}
                        </li>
                        <li className={`${active >= 2  ? 'active': 'text-gray-400'}`}>
                            {i18n.t('exchangeSuccessful')}!
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default Stepper;
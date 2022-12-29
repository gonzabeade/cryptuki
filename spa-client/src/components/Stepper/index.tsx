import React from 'react';
import './styles.css';
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
                            Trade proposal sent! Now wait for the seller
                        </li>
                        <li className={`${active >= 1  ? 'active': 'text-gray-400'}`}>
                            The seller accepted your trade proposal. Meet him/her and buy the crypto.
                        </li>
                        <li className={`${active >= 2  ? 'active': 'text-gray-400'}`}>
                            Exchange successful!
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default Stepper;
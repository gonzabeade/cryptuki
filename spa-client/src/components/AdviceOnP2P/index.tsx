import React from 'react';

const AdviceOnP2P = () => {
    return (
        <div className="flex flex-col mx-auto">
            <div className="flex flex-row mx-auto mt-10">
                <h1 className="text-polard text-2xl font-bold text-center mr-5 ">
                    Advice on P2P Operation
                </h1>
            </div>
            <ul className="mx-20 list-decimal my-3">
                <li className="mt-5 text-polard text-lg">
                    When the seller is about to transfer the crypto, try a transaction with small amounts first, to verify your address and chain are correct.
                </li>
                <li className="mt-5 text-polard text-lg">
                    <p>
                        It's important that you understand where you are going to receive the crypto, if it's a wallet address or an exchange, and the blockchain where the transaction will occur.
                    </p>
                </li>
                <li className="mt-5 text-polard text-lg">
                    <p>
                        Meet the seller in a public shared location.
                    </p>
                </li>
                <li className="mt-5 text-polard text-lg">
                    <p>
                        If an offer sounds too good to be true, it probably is. Malicious actors often post enticing ads to lure newcomers and less-experienced traders to try to steal your funds or data. Always use the common sense: people trade for a profit, so why would they trade at a loss?
                    </p>
                </li>
            </ul>

        </div>
    );
};

export default AdviceOnP2P;
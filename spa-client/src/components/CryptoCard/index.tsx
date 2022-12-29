import React from "react";
import './styles.css';
import icons from "../../assets";
import OfferModel from "../../types/OfferModel";

const CryptoCard = ({offer}: {offer: OfferModel}) => {
    return (
        <div className="crypto-card rounded-lg">
            <div className="column">
                <div className="label">Vendedor:</div>
                <div className="bold text-polar">gonzabeade</div>
                <div className="light">Usuario nuevo</div>
                <div className="label">Último Login: 2022-05-08</div>
            </div>
            <div className="column">
                <div className="label">Precio:</div>
                <div className="bold text-polar">{offer.unitPrice} ARS por {offer.cryptoCode}<img src={icons.ETH} alt={offer.cryptoCode}></img></div>
                <div className="light">Min: {offer.minInCrypto} ARS - Máx: {offer.maxInCrypto} ARS</div>
            </div>
            <div className="column">
                <div className="label">Ubicación:</div>
                <div className="bold text-polar">{offer.location}</div>
            </div>
            <div className="column">
                <button className="my-auto rounded-lg bg-frost px-6 py-3 text-white font-bold hover:bg-frostd">Compra</button>
            </div>           
        </div>
    )

}; 

export default CryptoCard; 
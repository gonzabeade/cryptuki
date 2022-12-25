import React from "react";
import './styles.css';
import icons from "../../assets";
import OfferModel from "../../types/OfferModel";

const CryptoCard = ({offer}: {offer: OfferModel}) => {
    return (
        <div className="crypto-card">
            <div className="column">
                <div className="label">Vendedor:</div>
                <div className="bold">gonzabeade</div>
                <div className="light">Usuario nuevo</div>
                <div className="label">Último Login: 2022-05-08</div>
            </div>
            <div className="column">
                <div className="label">Precio:</div>
                <div className="bold">{offer.unitPrice} ARS por {offer.cryptoCode}<img src={icons.ETH} alt={offer.cryptoCode}></img></div>
                <div className="light">Min: {offer.minInCrypto} ARS - Máx: {offer.maxInCrypto} ARS</div>
            </div>
            <div className="column">
                <div className="label">Ubicación:</div>
                <div className="bold">{offer.location}</div>
            </div>
            <div className="column">
                <button className="blue">Compra</button>
            </div>           
        </div>
    )

}; 

export default CryptoCard; 
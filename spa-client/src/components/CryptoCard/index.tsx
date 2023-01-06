import React, {useEffect} from "react";
import './styles.css';
import icons from "../../assets";
import OfferModel from "../../types/OfferModel";
import {useNavigate} from "react-router-dom";
import RatingStars from "../RatingStars";
import UserModel from "../../types/UserModel";
const CryptoCard = ({offer}: {offer: OfferModel}) => {
    const navigate = useNavigate();
    const [seller, setSeller] = React.useState<UserModel>();

    async function fetchUserData(){
        //fetch seller data
    }

    useEffect(()=>{
        fetchUserData();
    })

    return (
        <div className="crypto-card rounded-lg">
            <div className="column">
                <div className="label">Vendedor:</div>
                <div className="bold text-polar">{seller?.username}</div>
                {seller?.rating === 0 || !seller?.rating ? <div className="light">Usuario nuevo</div>: <RatingStars rating={seller?.rating}/>}
                <div className="label">Último Login: {seller?.lastLogin}</div>
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
                <button className="my-auto rounded-lg bg-frost px-6 py-3 text-white font-bold hover:bg-frostd"
                onClick={()=> navigate('/offer/' + offer.offerId)}>Compra</button>
            </div>           
        </div>
    )

}; 

export default CryptoCard; 
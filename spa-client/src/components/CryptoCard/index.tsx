import React, {useEffect} from "react";
import './styles.css';
import icons from "../../assets";
import OfferModel from "../../types/OfferModel";
import {useNavigate} from "react-router-dom";
import RatingStars from "../RatingStars";
import UserModel from "../../types/UserModel";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
const CryptoCard = ({offer}: {offer: OfferModel}) => {
    const navigate = useNavigate();
    const [seller, setSeller] = React.useState<UserModel>();
    const userService = useUserService();

    async function fetchUserData(username:string){
        try{
            const resp = await userService.getUser(username);
            setSeller(resp);
        }catch (e) {
            toast.error("Connection error. Failed to fetch user data");
        }
    }

    useEffect(()=>{
        const username = userService.getUsernameFromURI(offer.seller)
        fetchUserData(username);
    },[])

    return (
        <div className="crypto-card rounded-lg">
            <div className="column">
                <div className="label">Vendedor:</div>
                <div className="bold text-polar">{seller?.username}</div>
                {seller?.rating === 0 || !seller?.rating ? <div className="light">Usuario nuevo</div>: <RatingStars rating={seller?.rating/2}/>}
                <div className="label">Último Login: {seller?.lastLogin.toString().substring(0,10)}</div>
            </div>
            <div className="column">
                <div className="label">Precio:</div>
                <div className="bold text-polar">{offer.unitPrice} ARS por {offer.cryptoCode}<img src={"/images/"+ offer.cryptoCode+".png"} alt={offer.cryptoCode}></img></div>
                <div className="light">Min: {offer.minInCrypto} ARS - Máx: {offer.maxInCrypto} ARS</div>
            </div>
            <div className="column">
                <div className="label">Ubicación:</div>
                <a  target="_blank" rel="noreferrer" className="bold text-polar hover:cursor-pointer hover:text-blue-600 hover:underline" href={`https://www.google.com/maps/search/?api=1&query=${offer.location}`}>{offer.location}</a>
            </div>
            <div className="column">
                <button className="my-auto rounded-lg bg-frost px-6 py-3 text-white font-bold hover:bg-frostd"
                onClick={()=> navigate('/offer/' + offer.offerId)}>Compra</button>
            </div>           
        </div>
    )

}; 

export default CryptoCard; 
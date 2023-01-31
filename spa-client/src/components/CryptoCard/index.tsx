import React, {useEffect} from "react";
import './styles.css';
import icons from "../../assets";
import OfferModel from "../../types/OfferModel";
import {useNavigate} from "react-router-dom";
import RatingStars from "../RatingStars";
import UserModel from "../../types/UserModel";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
import {attendError} from "../../common/utils/utils";
import i18n from "../../i18n";
const CryptoCard = ({offer}: {offer: OfferModel}) => {
    const navigate = useNavigate();
    const [seller, setSeller] = React.useState<UserModel>();
    const userService = useUserService();

    async function fetchUserData(username:string){
        try{
            const resp = await userService.getUser(username);
            setSeller(resp);
        }catch (e) {
            attendError("Connection error. Failed to fetch user data",e);
        }
    }

    useEffect(()=>{
        const username = userService.getUsernameFromURI(offer.seller)
        fetchUserData(username);
    },[])

    return (
        <div className="crypto-card rounded-lg ">
            <div className="column">
                <div className="label">{i18n.t('seller')}:</div>
                <div className="flex flex-row text-gray-400 text-center">
                    <div className="bold text-polar">{seller?.username }</div>
                    {seller?.ratingCount === 0 || !seller?.rating ?
                        <div className="light ml-2">{i18n.t('newUser')}</div> : <div className=" text-polar text-xs items-center ml-1 mt-0.5">({ seller.ratingCount} {i18n.t('tradeQuantity')})</div>
                    }
                </div>
                {seller?.ratingCount !== 0 && seller?.rating && <RatingStars rating={seller.rating/2}/>}
                <div className="label text-gray-400 text-xs font-medium">{i18n.t('lastLogin')}: {seller?.lastLogin.toString().substring(0, 10)}</div>
            </div>
            <div className="column">
                <div className="label">{i18n.t('price')}:</div>
                <div className="bold text-polar">{offer.unitPrice} ARS {i18n.t('for')} {offer.cryptoCode}<img src={"/images/"+ offer.cryptoCode+".png"} alt={offer.cryptoCode}></img></div>
                <div className="text-gray-400 font-medium">Min: {offer.minInCrypto * offer.unitPrice} ARS - Máx: {offer.maxInCrypto * offer.unitPrice} ARS</div>
            </div>
            <div className="column">
                <div className="label">{i18n.t('location')}:</div>
                <a  target="_blank" rel="noreferrer" className="bold text-polar hover:cursor-pointer hover:text-blue-600 hover:underline" href={`https://www.google.com/maps/search/?api=1&query=${offer.location}`}>{offer.location}</a>
            </div>
            <div className="column">
                <button className="my-auto rounded-lg bg-frost px-6 py-3 text-white font-bold hover:bg-frostd"
                onClick={()=> navigate('/offer/' + offer.offerId)}>{i18n.t('buy')}</button>
            </div>           
        </div>
    )

}; 

export default CryptoCard; 
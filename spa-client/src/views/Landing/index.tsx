import {SetStateAction, useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import { useLocation, useNavigate } from "react-router-dom";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]>(); 
    const offerService = useOfferService();
    
    const navigate = useNavigate(); 
    const location = useLocation(); 

    useEffect( ()=>{
        offerService?.getOffers(5, 5)
        .then((data: SetStateAction<OfferModel[] | undefined>) => setOffers(data))
        .catch( () => navigate("/login", {state: {from: location}, replace: true}))
    }, [offerService, location, navigate])

    return (
        <div className="landing">
            {offers?.map( (offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
        </div>
    )

}; 

export default Landing; 
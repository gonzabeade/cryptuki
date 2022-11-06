import {useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import {offerService} from "../../services"
import OfferModel from "../../types/OfferModel";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]>(); 

    useEffect( ()=>{
        offerService.getOffers(0, 5)
        .then((data) =>
            setOffers(data)
        ) 
    }, [])

    return (
        <div className="landing">
            {offers?.map( (offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
        </div>
    )

}; 

export default Landing; 
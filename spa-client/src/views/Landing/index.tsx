import {useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]>(); 

    useEffect( ()=>{
        fetch('http://localhost:8080/webapp/api/offers?per_page=10')
        .then(res=>{
            return res.json(); 
        })
        .then((data)=>{
            setOffers(data)
        })

    }, [])

    return (
        <div className="landing">
            {offers?.map( (offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
        </div>
    )

}; 

export default Landing; 
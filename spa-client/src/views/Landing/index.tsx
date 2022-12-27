import {SetStateAction, useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import { useLocation, useNavigate } from "react-router-dom";
import CryptoFilters from "../../components/CryptoFilters";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]>([{
        cryptoCode:"BTC",
        date:new Date(),
        location:"Balvanera",
        maxInCrypto:2,
        minInCrypto:0.001,
        offerId:1,
        offerStatus:"PENDING",
        unitPrice:1000000,
        url:"/offer/1",
        seller:"marquitos",
        trades:"1"
    }]);
    const offerService = useOfferService();
    
    const navigate = useNavigate(); 
    const location = useLocation(); 

    useEffect( ()=>{
        // offerService?.getOffers(5, 5)
        // .then((data: SetStateAction<OfferModel[] | undefined>) => setOffers(data))
        // .catch( () => navigate("/login", {state: {from: location}, replace: true}))

    }, [offerService, location, navigate])

    return (
        <div className="flex flex-wrap w-full h-full justify-around">
            <div className={" flex w-1/3 justify-center "}>
                <CryptoFilters/>
            </div>
            <div className="w-2/3">
                {offers?.map( (offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
            </div>
        </div>

    )

}; 

export default Landing; 
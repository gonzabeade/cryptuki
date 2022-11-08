import { OfferService } from "../services/OfferService";
import useAxiosPrivate from "./useAxiosPrivate";


const useOfferService = () => {

    const axiosPrivate = useAxiosPrivate(); 
    const offerService = OfferService.getInstance(axiosPrivate); 

    return offerService; 
}

export default useOfferService; 
import { OfferService } from "../services/OfferService";
import {useAxios} from "./useAxios";

const useOfferService = () => {
    const axiosInstance = useAxios(); 
    const offerService = OfferService.getInstance(axiosInstance); 
    return offerService; 
}

export default useOfferService; 
import { OfferService } from "../services/OfferService";
import {useAxios} from "./useAxios";

const useOfferService = () => {
    const axiosInstance = useAxios(); 
    const offerService = new OfferService(axiosInstance); 
    return offerService; 
}



export default useOfferService; 
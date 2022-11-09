import { useContext } from "react";
import AxiosContext from "../contexts/AxiosContext";
import { OfferService } from "../services/OfferService";

const useOfferService = () => {

    const axiosInstance = useContext(AxiosContext).axiosInstance
    const offerService = OfferService.getInstance(axiosInstance); 

    return offerService; 
}

export default useOfferService; 
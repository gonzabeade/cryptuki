import {OfferService} from "../services/OfferService";
import {useAxios} from "./useAxios";

const useOfferService = () => {
    const axiosInstance = useAxios();

    return new OfferService(axiosInstance);
}



export default useOfferService; 
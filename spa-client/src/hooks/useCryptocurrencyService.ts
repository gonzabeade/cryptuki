import {useAxios} from "./useAxios";
import {CryptocurrencyService} from "../services/CryptocurrencyService";

const useCryptocurrencyService = () => {
    const axiosInstance = useAxios();
    return new CryptocurrencyService(axiosInstance);
}

export default useCryptocurrencyService;
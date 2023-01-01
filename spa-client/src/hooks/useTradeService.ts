
import {useAxios} from "./useAxios";
import {TradeService} from "../services/TradeService";

const useTradeService = () => {
    const axiosInstance = useAxios();

    return new TradeService(axiosInstance);
}



export default useTradeService;
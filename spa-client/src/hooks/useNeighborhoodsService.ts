import {useAxios} from "./useAxios";
import {NeighborhoodsService} from "../services/NeighborhoodsService";

const useNeighborhoodsService = () => {
    const axiosInstance = useAxios();
    return new NeighborhoodsService(axiosInstance);
}

export default useNeighborhoodsService;
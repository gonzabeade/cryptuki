import {useAxios, withNoAuthorization} from "./useAxios";
import {ComplainService} from "../services/ComplainService";

const useComplainService = () => {
    const axiosInstance = useAxios();
    withNoAuthorization();
    return new ComplainService(axiosInstance);
}

export default useComplainService;
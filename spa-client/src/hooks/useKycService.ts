import {useAxios} from "./useAxios";
import {KycService} from "../services/KycService";

const useKycService = () => {
    const axiosInstance = useAxios();
    return new KycService(axiosInstance);
}

export default useKycService;
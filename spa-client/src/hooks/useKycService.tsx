import {KycService} from "../services/KycService";
import {useAxios} from "./useAxios";

const useKycService = () => {
    const axiosInstance = useAxios();

    return new KycService(axiosInstance);
}



export default useKycService;
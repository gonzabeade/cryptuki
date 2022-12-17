import { UserService } from "../services/UserService";
import {useAxios} from "./useAxios";

const useUserService = () => {
    const axiosInstance = useAxios(); 
    const offerService = new UserService(axiosInstance); 
    return offerService; 
}



export default useUserService; 
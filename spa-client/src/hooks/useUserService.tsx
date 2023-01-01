import {UserService} from "../services/UserService";
import {useAxios} from "./useAxios";

const useUserService = () => {
    const axiosInstance = useAxios();
    return new UserService(axiosInstance);
}



export default useUserService; 
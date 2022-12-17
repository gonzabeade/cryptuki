import { AxiosService } from "../services/AxiosService";

const withBasicAuthorization = (username: string, password: string) => {
    const axiosService = AxiosService.getInstance(); 
    axiosService.useBasicAuthentication(username, password); 
}

const withNoAuthorization = () => {
    const axiosService = AxiosService.getInstance(); 
    axiosService.useNoAuthentication(); 
}

const withBearerAuthorization = () => {
    const axiosService = AxiosService.getInstance(); 
    axiosService.useBearerAuthentication(); 
}

const useAxios = () => {
    const axiosService = AxiosService.getInstance(); 
    return axiosService.getActiveAxiosInstance(); 
}

export {withBasicAuthorization, withBearerAuthorization, withNoAuthorization, useAxios}; 
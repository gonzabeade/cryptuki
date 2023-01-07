import { AxiosService } from "../services/AxiosService";
import {AxiosInstance} from "axios";

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

const useAxios = ():()=>AxiosInstance => {
    return AxiosService.getAxiosInstance;
}

export {withBasicAuthorization, withBearerAuthorization, withNoAuthorization, useAxios}; 
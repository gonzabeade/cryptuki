import axios, { AxiosInstance } from "axios";
import { createContext, useContext, useState } from "react";
import {paths} from "../common/constants"; 
import AuthContext from "./AuthContext";

interface AxiosContextType {
    axiosInstance: AxiosInstance,
    addBasicAuthRequestInterceptor: (arg0: string, arg1: string) => void; 
    addBasicAuthResponseInterceptor: VoidFunction; 
}


export function AxiosProvider({ children }: { children: React.ReactNode }) {

    const accessToken = useState(''); 
    const refreshToken = useState(''); 

    const axiosInstance = axios.create({
        baseURL: paths.BASE_URL
    });
    
    const addBasicAuthRequestInterceptor = (username: string, password: string) => {
        axiosInstance.interceptors.request.clear()
        const requestIntercept = axiosInstance.interceptors.request.use(
            (config: any) => {
                config.headers['Authorization'] = `Basic ${username}:${password}`;
                return config; 
            }, 
            (error: any) => {
                Promise.reject(error)
            }
        )
    }

    const addBasicAuthResponseInterceptor = () => {
        axiosInstance.interceptors.response.clear()
        const responseIntercept = axiosInstance.interceptors.response.use(
            response => {
                console.log("Hello World!")
                console.log(response.headers["jwt_token"])
                console.log(response.headers["refresh_token"])
                return response
            }
        )
    }


    let value = { axiosInstance, addBasicAuthRequestInterceptor, addBasicAuthResponseInterceptor};

    return <AxiosContext.Provider value={value}>{children}</AxiosContext.Provider>;
}

const AxiosContext = createContext<AxiosContextType>(null!);
export default AxiosContext; 
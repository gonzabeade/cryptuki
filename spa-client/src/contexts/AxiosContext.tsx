import axios, { AxiosInstance } from "axios";
import { createContext, useContext, useState } from "react";
import {paths} from "../common/constants"; 
import AuthContext from "./AuthContext";

interface AxiosContextType {
    axiosInstance: AxiosInstance,
    addBasicAuthRequestInterceptor: (arg0: string, arg1: string) => void; 
    addBasicAuthResponseInterceptor: VoidFunction; 
    addOAuthInterceptors: VoidFunction; 
}


export function AxiosProvider({ children }: { children: React.ReactNode }) {

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
                response.headers["x-access-token"] && localStorage.setItem("accessToken", response.headers["x-access-token"]); 
                response.headers["x-refresh-token"] && localStorage.setItem("refreshToken", response.headers["x-refresh-token"])
                console.log(localStorage.getItem("accessToken"))
                return response
            }
        )
    }

    const addOAuthRequestInterceptor = () => {
        axiosInstance.interceptors.request.clear()
        const requestIntercept = axiosInstance.interceptors.request.use(
            (config: any) => {
                if (  config.headers['Authorization'] ) { 
                    // It is not a retry, it is the first attempt
                    // Just embed the already-calculated access token to the request 
                    config.headers['Authorization'] = `Bearer ${localStorage.getItem("accessToken")}` 
                }
                return config; 
            }, 
            (error: any) => {
                Promise.reject(error)
            }
        )

        
    }

    const addOAuthResponseInterceptor = () => {

    }

    const addOAuthInterceptors = () => {
        addOAuthRequestInterceptor() 
        addOAuthResponseInterceptor()
    }


    let value = { axiosInstance, addBasicAuthRequestInterceptor, addBasicAuthResponseInterceptor, addOAuthInterceptors};

    return <AxiosContext.Provider value={value}>{children}</AxiosContext.Provider>;
}

const AxiosContext = createContext<AxiosContextType>(null!);
export default AxiosContext; 
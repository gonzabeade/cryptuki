import axios, { AxiosInstance } from "axios";
import { createContext, useContext, useState } from "react";
import { paths } from "../common/constants";
import AuthContext from "./AuthContext";

interface AxiosContextType {
    axiosInstance: AxiosInstance,
    useFirst: VoidFunction;
    useSecond: VoidFunction;
}


export function AxiosProvider({ children }: { children: React.ReactNode }) {


    /* Set up no authentication axios instance */
    const axiosFirstInstance = axios.create({
        baseURL: paths.BASE_URL
    });

    /* Set up basic authentication axios instance */
    const axiosSecondInstance = axios.create({
        baseURL: paths.BASE_URL
    });

    var [axiosInstance, setAxiosInstance] = useState<AxiosInstance>(axiosFirstInstance); 


    axiosFirstInstance.interceptors.request.use(
        (config: any) => {
            console.log("USING FIRST")
            return config;
        },
        (error: any) => {
            Promise.reject(error)
        }
    )

    axiosSecondInstance.interceptors.request.use(
        (config: any) => {
            console.log("USING SECOND")
            return config;
        },
        (error: any) => {
            Promise.reject(error)
        }
    )

    const useFirst = () => {
        console.log("SETTING FIRST UP")
        // setAxiosInstance(axiosFirstInstance); 
    }

    const useSecond = () => {
        console.log("SETTING SECOND UP")
        // setAxiosInstance(axiosSecondInstance); 
    }

    let value = { axiosInstance, useFirst, useSecond};

    return <AxiosContext.Provider value={value}>{children}</AxiosContext.Provider>;
}

const AxiosContext = createContext<AxiosContextType>(null!);
export default AxiosContext; 
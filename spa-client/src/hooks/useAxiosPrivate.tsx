import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import { useAuth } from "../contexts/AuthContext";

let useRefreshToken = ()=>(() => null);  // implement

const useAxiosPrivate = () => {

    const refresh = useRefreshToken(); 
    const auth = useAuth(); 


    useEffect( ()=> {

        const requestIntercept = axiosPrivate.interceptors.request.use(
            (config: any) => {
                if ( ! config.headers?['Authorization'] : Boolean) { // Ese boolean esta menos chequeado 
                    // It is not a retry, it is the first attempt
                    // Just embed the already-calculated access token to the request 
                    config.headers['Authorization'] = `Bearer ${auth?.user?.accessToken}`; 
                }
                console.log("REQUEST INTERCEPTED"); 
                return config; 
            }, 
            (error: any) => {
                Promise.reject(error)
            }
        )

        const responseIntercept = axiosPrivate.interceptors.response.use(
            response => response, // Everything SHOULD be okay ... 
            async (error) => {  // But if it is not, it must be because of the token 
                const previousRequest = error?.config; 
                if (error?.response?.status === '401' && !previousRequest.sent) {
                    previousRequest.set = true; // Avoid endless loop 
                    const newAccessToken = await refresh(); 
                    previousRequest.headers['Authorization'] = `Bearer ${newAccessToken}`; 
                    return axiosPrivate(previousRequest); 
                }
                return Promise.reject(error); 
            }
        )

        return () => {
            axiosPrivate.interceptors.request.eject(requestIntercept);
            axiosPrivate.interceptors.response.eject(responseIntercept);
        }

    }, [auth, refresh])

    return axiosPrivate; 
}

export default useAxiosPrivate; 
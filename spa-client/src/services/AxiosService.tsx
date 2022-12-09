import { paths } from "../common/constants";
import axios, { AxiosInstance } from "axios";

export class AxiosService {

    private readonly axiosBearerInstance : AxiosInstance = axios.create({
        baseURL: paths.BASE_URL
    });
     
    private axiosBasicInstance : AxiosInstance = axios.create({
        baseURL: paths.BASE_URL
    }); 

    private axiosNoAuthenticationInstance : AxiosInstance = axios.create({
        baseURL: paths.BASE_URL
    });

    private axiosActiveInstance : AxiosInstance = this.axiosNoAuthenticationInstance; 


    private static readonly axiosServiceInstance = new AxiosService();  


    private constructor() {

        /*Add response interceptor for Basic*/
        this.axiosBasicInstance.interceptors.response.use(
            response => {
                response.headers["x-access-token"] && localStorage.setItem("accessToken", response.headers["x-access-token"]);
                response.headers["x-refresh-token"] && localStorage.setItem("refreshToken", response.headers["x-refresh-token"])
                console.log(localStorage.getItem("accessToken"))
                return response
            }
        )

        /* Add request interceptor for Bearer */
        this.axiosBearerInstance.interceptors.request.use(
            (config: any) => {
                if (config.headers['Authorization']) {
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

        /* Add response interceptor for Bearer */
        const responseIntercept = this.axiosBearerInstance.interceptors.response.use(
            response => response, // Everything SHOULD be okay ... 
            async (error) => {  // But if it is not, it must be because of the token 
                const previousRequest = error?.config;
                if (error?.response?.status === '401' && !previousRequest.sent) {
                    previousRequest.set = true; // Avoid endless loop 
                    const newAccessToken = null; // await refresh(); 
                    previousRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
                    return this.axiosBearerInstance(previousRequest);
                }
                return Promise.reject(error);
            }
        )


    }

    public static getInstance() {
        return AxiosService.axiosServiceInstance;  
    }

    public getActiveAxiosInstance() {
        return this.axiosActiveInstance;  
    }

    public useBearerAuthentication() {
        this.axiosActiveInstance = this.axiosBearerInstance; 
    }

    public useBasicAuthentication(username: string, password: string) {

        this.axiosBasicInstance.interceptors.request.clear(); 

        const requestIntercept = this.axiosBasicInstance.interceptors.request.use(
            (config: any) => {
                config.headers['Authorization'] = `Basic ${username}:${password}`;
                return config;
            },
            (error: any) => {
                Promise.reject(error)
            }
        )

        this.axiosActiveInstance = this.axiosBasicInstance; 
    }

    public useNoAuthentication() {
        this.axiosActiveInstance = this.axiosNoAuthenticationInstance; 
    }

}


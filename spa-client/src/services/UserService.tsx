import { AxiosInstance } from "axios";
import jwtDecode from "jwt-decode";

export class UserService {

    private readonly axiosInstance : AxiosInstance; 

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }

    public getLoggedInUser(): string | null {
        const refreshToken = localStorage.getItem("refreshToken"); 

        if (refreshToken) {
            const tok : any = jwtDecode(refreshToken); 
            return tok.sub; 
        } 
        return null; 
    }

}
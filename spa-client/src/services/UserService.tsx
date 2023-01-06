import { AxiosInstance } from "axios";
import jwtDecode from "jwt-decode";

export class UserService {

    private readonly axiosInstance : AxiosInstance; 

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }
    //TODO decision. Metodo para recuperar toda la user data o mover User Models en todo momento?
    public getLoggedInUser(): string | null {
        const refreshToken = localStorage.getItem("refreshToken"); 

        if (refreshToken) {
            const tok : any = jwtDecode(refreshToken);
            return tok.sub; 
        } 
        return null; 
    }

    public getUsernameFromURI(uri:string, basePath:string):string{
        const username = uri.replace(basePath, "");
        return username.substring(0, username.indexOf("/"));
    }

}
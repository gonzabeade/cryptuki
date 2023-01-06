import { AxiosInstance } from "axios";
import jwtDecode from "jwt-decode";
import UserModel from "../types/UserModel";
import {paths} from "../common/constants";

export class UserService {

    private readonly axiosInstance : AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.USERS;

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

    public async  getUser(username:string):Promise<UserModel>{
        //TODO
        const secrets = await this.axiosInstance.get<UserModel>(this.basePath + username + '/secrets');
        const publicInfo = await this.axiosInstance.get<UserModel>(this.basePath + username);
        return secrets.data;
    }


    public getUsernameFromURI(uri:string):string{
        const n = uri.lastIndexOf('/');
        return uri.substring(n + 1);
    }

}
import { AxiosInstance } from "axios";
import jwtDecode from "jwt-decode";
import UserModel from "../types/UserModel";
import {paths} from "../common/constants";

export class UserService {

    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.USERS;

    public constructor(axiosInstance: ()=>AxiosInstance) {
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

    public async  getUser(username:string):Promise<UserModel>{
        const resp = await this.axiosInstance().get<UserModel>(this.basePath + username);
        return resp.data;
    }


    public getUsernameFromURI(uri:string):string{
        if(uri){
            const n = uri.lastIndexOf('/');
            return uri.substring(n + 1);
        }
        return '';
    }

    public async register(username:string, password:string, repeatPassword:string, phoneNumber:string, email:string){
        await this.axiosInstance().post(this.basePath + "register", {
            username: username,
            password: password,
            repeatPassword: repeatPassword,
            phoneNumber: phoneNumber,
            email: email
        });
    }
    public async verifyUser(code:number, username:string){
        await this.axiosInstance().post(this.basePath + username, {
           code:code
        });
    }
    public async getKYCStatus(username:string){
        const resp = await this.axiosInstance().post(this.basePath + username + '/kyc');
        if(resp.status === 204){
            return null;
        }else{
            return resp.data;
        }
    }


}
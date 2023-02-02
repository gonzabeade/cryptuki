import { AxiosInstance } from "axios";
import jwtDecode from "jwt-decode";
import UserModel from "../types/UserModel";
import {paths} from "../common/constants";
import {recoverPasswordForm} from "../views/RecoverPassword";

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

    public getRole(): string | null {
        const refreshToken = localStorage.getItem("refreshToken");

        if (refreshToken) {
            const tok: any = jwtDecode(refreshToken);
            return tok.role;
        }
        return null;
    }

    public async  getUser(username:string):Promise<UserModel>{
        const resp = await this.axiosInstance()
            .get<UserModel>(this.basePath + username,{
                headers:{
                    'Accept':'application/vnd.cryptuki.v1.user+json'
                }
            });
        return resp.data;
    }

    public async  getUserByUrl(url:string):Promise<UserModel>{
        const resp = await this.axiosInstance().get<UserModel>(url,{
            headers:{
                'Accept':'application/vnd.cryptuki.v1.user+json'
            }
        });
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
        await this.axiosInstance().post(paths.BASE_URL + "/users", {
            username: username,
            password: password,
            phoneNumber: phoneNumber,
            email: email
        },{
            headers:{
                'Accept':'application/vnd.cryptuki.v1.user+json',
                'Content-Type':'application/vnd.cryptuki.v1.user+json'
            }
        });
    }
    public async verifyUser(code:number, username:string){
        await this.axiosInstance().post(this.basePath + username, {
           code:code
        },{
            headers:{
                'Content-Type':'application/vnd.cryptuki.v1.user-validation+json'
            }
        });
    }

    public async getKYCStatus(username:string){
        const resp = await this.axiosInstance().get(this.basePath + username + '/kyc',
            {
                headers:{
                    'Accept':'application/vnd.cryptuki.v1.kyc+json'
                }
            });
        if(resp.status === 204){
            return null;
        }else{
            return resp.data;
        }
    }


    public async getProfilePictureByUrl(url:string):Promise<string|null>{
        const resp = await this.axiosInstance().get<Blob>(url,{responseType:'arraybuffer'});
        if(resp.status === 204 ){
            return null;
        }
        return this.convertBlobToBase64(new Blob([resp.data]))
    }

    public async RecoverPassword(data:recoverPasswordForm){
        const params = new URLSearchParams();
        params.append("email",data.email)
        await this.axiosInstance().post(paths.BASE_URL + "/users",null, {
            params:params
            ,headers:{
                'Accept':'application/vnd.cryptuki.v1.nonce-ack+json',
            }
        });
    }

    convertBlobToBase64 = async (blob: Blob) => {
        return new Promise<string>((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onloadend = () => {
                resolve(reader.result as string);
            };
            reader.onerror = reject;
        });
    };


}
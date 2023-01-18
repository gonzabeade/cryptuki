import  {AxiosInstance} from "axios";
import {paths} from "../common/constants";
import {KycInformationModel} from "../types/KycInformationModel";
import {SolveKycForm} from "../components/KycAdminInformation/KycInformation";
import UserModel from "../types/UserModel";

export class KycService {

    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.USERS;
    private readonly userPath = paths.BASE_URL + '/users';

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getKycInformation(username:string):Promise<KycInformationModel>{
       const resp = await this.axiosInstance().get<KycInformationModel>(this.basePath + username + '/kyc');
       return resp.data;
    }

    public async getPendingKycInformation(page?:number, pageSize?:number):Promise<UserModel[]>{
        const resp = await this.axiosInstance().get<UserModel[]>(this.userPath,
            {
                params:{
                    page: page,
                    pageSize: pageSize,
                    kyc_status:"PEN"
                }
            });
        return resp.data;
    }

    public async solveKyc(KycForm:SolveKycForm, username:string){
        const resp = await this.axiosInstance().post(this.basePath + username + '/kyc', {body:{
                status:KycForm.status
            }});
        return resp.data;
    }
}
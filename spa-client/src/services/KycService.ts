import  {AxiosInstance} from "axios";
import {paths} from "../common/constants";
import {KycInformationModel} from "../types/KycInformationModel";
import {SolveKycForm} from "../components/KycAdminInformation/KycInformation";
import UserModel from "../types/UserModel";
import {PaginatedResults} from "../types/PaginatedResults";
import {processPaginatedResults} from "../common/utils/utils";

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

    public async getPendingKycInformationByUrl(url:string):Promise<PaginatedResults<UserModel>>{
        const resp = await this.axiosInstance().get<UserModel[]>(url);
        return processPaginatedResults(resp);
    }

    public async getPendingKycInformation():Promise<PaginatedResults<UserModel>>{
        let params = new URLSearchParams();
        params.append("kyc_status","PEN");
        params.append("per_page", "2");
        const resp = await this.axiosInstance().get<UserModel[]>(
            this.userPath,{ params:params });
        return processPaginatedResults(resp);
    }

    public async solveKyc(KycForm:SolveKycForm, username:string){
        const resp = await this.axiosInstance().put(this.basePath + username + '/kyc', {body:{
                status:KycForm.status,
                comments:KycForm.comments
            }});
        return resp.data;
    }
}
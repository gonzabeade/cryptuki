import  {AxiosInstance} from "axios";
import {paths} from "../common/constants";
import {KycInformationModel} from "../types/KycInformationModel";
import {SolveKycForm} from "../components/KycAdminInformation/KycInformation";
import UserModel from "../types/UserModel";
import useUserService from "../hooks/useUserService";

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
        const resp = await this.axiosInstance().put(this.basePath + username + '/kyc', {body:{
                status:KycForm.status,
                comments:KycForm.comments
            }});
        return resp.data;
    }

    public async uploadKyc(
        username:string,
        names:string,
        surnames:string,
        emissionCountry:string,
        documentCode:string,
        idType:string,
        idPictures:FileList,
        facePictures:FileList
    ){
        let formData = new FormData();

        formData.append('kyc-information',
            new Blob([JSON.stringify({
                    'givenNames':names,
                    'surnames':surnames,
                    'emissionCountry':emissionCountry,
                    'idCode':documentCode,
                    'idType':idType
                })],
                {type: "application/json"}));

        formData.append('id-photo', idPictures[0]);
        formData.append('validation-photo', facePictures[0]);

        await this.axiosInstance().post(this.basePath + username + '/kyc', formData,{
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
}
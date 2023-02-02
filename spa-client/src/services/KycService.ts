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
       const resp = await this.axiosInstance()
           .get<KycInformationModel>(this.basePath + username + '/kyc',
               {
                   headers:{
                       'Accept':'application/vnd.cryptuki.v1.kyc+json'
                   }
               });
       return resp.data;
    }

    public async getPendingKycInformationByUrl(url:string):Promise<PaginatedResults<UserModel>>{
        const resp = await this.axiosInstance().get<UserModel[]>(url,
            {
                headers:{
                    'Accept':'application/vnd.cryptuki.v1.user-list+json'
                }
            });
        return processPaginatedResults(resp);
    }

    public async getPendingKycInformation():Promise<PaginatedResults<UserModel>>{
        let params = new URLSearchParams();
        params.append("kyc_status","PEN");
        params.append("per_page", "2");
        const resp = await this.axiosInstance().get<UserModel[]>(
            this.userPath,{ params:params ,
            headers:{
                'Accept':'application/vnd.cryptuki.v1.user-list+json'
            }
            });
        return processPaginatedResults(resp);
    }

    public async getPicturesByUrl(url:string):Promise<string>{
        const resp = await this.axiosInstance().get<Blob>(url,{responseType:'arraybuffer'});
        return await this.convertBlobToBase64(new Blob([resp.data]))
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



    public async solveKyc(KycForm:SolveKycForm, username:string){
        const resp = await this.axiosInstance().patch(this.basePath + username + '/kyc', {
                comments:KycForm.comments,
                status: KycForm.status
            },{
            headers:{
                'Accept':'application/vnd.cryptuki.v1.kyc+json',
                'Content-Type': 'application/vnd.cryptuki.v1.kyc+json'
            }
        });
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
                'Content-Type': 'multipart/form-data',
                'Accept':'application/vnd.cryptuki.v1.kyc+json'
            }
        });
    }
}
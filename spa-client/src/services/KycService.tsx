import {AxiosInstance} from "axios";
import {KycModel} from "../types/KycModel";
import {paths} from "../common/constants";
import KycForm from "../components/KycForm/kycForm";

export class KycService {

    //TODO mirar como armar el path
    private readonly basePath = paths.BASE_URL;
    private readonly axiosInstance : ()=>AxiosInstance;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async uploadKyc(
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
            JSON.stringify({
                'givenNames':names,
                'surnames':surnames,
                'emissionCountry':emissionCountry,
                'idCode':documentCode,
                'idType':idType
            }));

        formData.append('id-photo', idPictures[0]);
        formData.append('validation-photo', facePictures[0]);

        await this.axiosInstance().post(this.basePath,formData,{
            headers:{
                'content-type': 'multipart/form-data'
            }
        });
    }
}
import {AxiosInstance} from "axios";
import useUserService from "../hooks/useUserService";
import {paths} from "../common/constants";

export class KycService {

    //TODO mirar como armar el path
    private readonly currentUser = useUserService().getLoggedInUser();
    private readonly basePath = paths.BASE_URL + paths.USERS + this.currentUser + "/kyc";
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
        console.log(this.basePath);
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

        await this.axiosInstance().post(this.basePath, formData,{
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
}
import {AxiosInstance} from "axios";
import {paths} from "../common/constants";
import {KycInformationModel} from "../types/KycInformationModel";

export class NeighborhoodsService{
    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + "/locations";

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getNeighborhoods():Promise<NeighborhoodsService[]>{
        const resp = await this.axiosInstance()
            .get<NeighborhoodsService[]>(this.basePath);
        return resp.data;
    }


}
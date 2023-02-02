import {AxiosInstance} from "axios";
import {paths} from "../common/constants";
import {KycInformationModel} from "../types/KycInformationModel";
import NeighborhoodModel from "../types/NeighborhoodModel";

export class NeighborhoodsService{
    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + "/locations";

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getNeighborhoods():Promise<NeighborhoodModel[]>{
        const resp = await this.axiosInstance()
            .get<NeighborhoodModel[]>(this.basePath);
        return resp.data;
    }


}
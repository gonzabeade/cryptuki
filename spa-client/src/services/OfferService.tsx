import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private static readonly instances: Map<AxiosInstance, OfferService> = new Map(); 
    private readonly axiosInstance : AxiosInstance; 

    private constructor(axiosInstance: AxiosInstance) {
        OfferService.instances.set(axiosInstance, this); 
        this.axiosInstance = axiosInstance; 
    }

    public static getInstance(axiosInstance: AxiosInstance) {
        let instance = OfferService.instances.get(axiosInstance)
        return instance === undefined ? new OfferService(axiosInstance) : instance 
    }

    public async getOffers(page?: number, pageSize?: number): Promise<Array<OfferModel>> {
        const resp = await this.axiosInstance.get<Array<OfferModel>>(this.basePath, {
            params: {
                page,
                per_page: pageSize 
            }
        })
        return resp.data; 
    }

}


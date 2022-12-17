import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance : AxiosInstance; 

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance; 
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


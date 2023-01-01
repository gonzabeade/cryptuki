import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";
import Result from "../types/Result";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance : AxiosInstance; 

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }

    public async getOffers(page?: number, pageSize?: number): Promise<Result<OfferModel[]>> {
        const resp = await this.axiosInstance.get<OfferModel[]>(this.basePath, {
            params: {
                page,
                per_page: pageSize 
            }
        })
        return Result.ok(resp.data);
    }
    public async getOfferInformation(offerId:number):Promise<Result<OfferModel>>{
        const resp = await this.axiosInstance.get<OfferModel>(this.basePath, {
            params:{
                offerId: offerId
            }
        })
        return Result.ok(resp.data);
    }

}


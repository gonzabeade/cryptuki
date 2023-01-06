import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance : AxiosInstance; 

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }

    public async getOffers(page?: number, pageSize?: number, cryptoCodes?:string[], locations?:string[], orderBy?:string): Promise<OfferModel[]> {
        const resp = await this.axiosInstance.get<OfferModel[]>(this.basePath, {
            params: {
                page:page,
                per_page: pageSize ,
                cryptoCodes: cryptoCodes,
                locations: locations,
                orderBy: orderBy
            }
        })
        return resp.data;
    }
    public async getOfferInformation(offerId:number):Promise<OfferModel>{

        const resp = await this.axiosInstance.get<OfferModel>(this.basePath + offerId);
        return resp.data;
    }
    public async getOffersByOwner(username:string, page?:number, pageSize?:number):Promise<OfferModel[]>{
        const resp = await this.axiosInstance.get<OfferModel[]>(this.basePath, {
            params:{
                page:page,
                pageSize:pageSize,
                by_user: username
            }
        })
        return resp.data;
    }

}


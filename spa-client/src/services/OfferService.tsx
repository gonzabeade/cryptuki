import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";
import {ModifyFormValues} from "../components/EditOfferForm";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance : ()=>AxiosInstance;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }

    public async getOffers(page?: number, pageSize?: number, cryptoCodes?:string[], locations?:string[], orderBy?:string): Promise<OfferModel[]> {
        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
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
        const resp = await this.axiosInstance().get<OfferModel>(this.basePath + offerId);
        return resp.data;
    }
    public async getOffersByOwner(username:string, page?:number):Promise<OfferModel[]>{
        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params:{
                page:page,
                by_user: username
            }
        })
        return resp.data;
    }

    public async modifyOffer(offer:ModifyFormValues){
        const resp = await this.axiosInstance().put<OfferModel[]>(this.basePath + offer.offerId, {
            cryptoCode: offer.cryptoCode,
            location: offer.location,
            minInCrypto: offer.minInCrypto,
            maxInCrypto: offer.maxInCrypto,
            unitPrice: offer.unitPrice,
            comments: offer.comments
        })
        return resp.data;
    }
    public async getOrderedOffers(page?:number, orderBy?:string):Promise<OfferModel[]>{
        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params:{
                page:page,
                orderBy:orderBy
            }
        })
        return resp.data;
    }
    public async getOffersByStatus(status:string|undefined, page?:number){

        if(status === 'ALL'){
            status = undefined
        }

        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params:{
                page:page,
                status:status
            }
        })
        return resp.data;
    }

    public getOfferIdFromURI(uri:string):string{
        const n = uri.lastIndexOf('/');
        return uri.substring(n + 1);
    }

}


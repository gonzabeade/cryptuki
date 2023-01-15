import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";
import {ModifyFormValues} from "../components/EditOfferForm";
import {PaginatedResults} from "../types/PaginatedResults";
import {getLinkHeaders, getPaginatorProps, Link} from "../common/utils/utils";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance : ()=>AxiosInstance;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }

    public async getOffers(page?: number, exclude_username?:string, status?:string[] , cryptoCodes?:string[], locations?:string[], orderBy?:string): Promise<PaginatedResults<OfferModel>> {
        let params = new URLSearchParams();
        params.append("per_page", "1");
        if(page){
            params.append("page", page?.toString()!);
        }

        if(orderBy){
            params.append("order_by", orderBy!);
        }
       if(exclude_username){
           params.append("exclude_user", exclude_username!);
       }

        if(status){
            status.map((s)=>{
                params.append("status", s);
            })
        }
        if(cryptoCodes){
            cryptoCodes.map((c)=>{
                params.append("crypto_code", c);
            })
        }
        if(locations){
            locations.map((l)=>{
                params.append("locations", l);
            })
        }

        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params: params
        });

        const linkHeaders:Link[] = getLinkHeaders(resp.headers["link"]!);

        if(resp.status != 200){
            throw new Error("Error fetching offers");
        }
        return {
            items: resp.data,
            paginatorProps: getPaginatorProps(linkHeaders)
        };

    }

    public async getPaginatedOffers(uri:string): Promise<PaginatedResults<OfferModel>>{
        const resp = await this.axiosInstance().get<OfferModel[]>(uri);

        const linkHeaders:Link[] = getLinkHeaders(resp.headers["link"]!);
        if(resp.status != 200){
            throw new Error("Error fetching offers");
        }

        return {
            items: resp.data,
            paginatorProps: getPaginatorProps(linkHeaders)
        };

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
    public async getOffersByStatus(status:string|undefined,  username:string, page?:number){

        if(status === 'ALL'){
            status = undefined
        }

        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params:{
                page:page,
                status:status,
                by_user:username
            }
        })
        return resp.data;
    }

    public getOfferIdFromURI(uri:string):string{
        const n = uri.lastIndexOf('/');
        return uri.substring(n + 1);
    }

}


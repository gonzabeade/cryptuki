import { paths } from "../common/constants";
import OfferModel from "../types/OfferModel";
import { AxiosInstance } from "axios";
import {ModifyFormValues} from "../components/EditOfferForm";
import {Link, PaginatedResults} from "../types/PaginatedResults";
import {getLinkHeaders, getPaginatorProps} from "../common/utils/utils";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance : ()=>AxiosInstance;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance; 
    }

    public async getOffers(params?:URLSearchParams): Promise<PaginatedResults<OfferModel>> {

        params?.append('per_page', "1");
        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params: params
        });


        if(resp.status === 200){
            const linkHeaders:Link[] = getLinkHeaders(resp.headers["link"]!);
            return {
                items: resp.data,
                paginatorProps: getPaginatorProps(linkHeaders),
                params: params!
            };
        }else if(resp.status === 204){
            return {
                items: [],
                params: params!,
            }
        }else{
            throw new Error("Error fetching offers");
        }


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
    public getSearchParamsFromURI(uri:string):URLSearchParams{
        console.log(uri);
        const n = uri.lastIndexOf('?');
        return new URLSearchParams(uri.substring(n + 1));
    }

}


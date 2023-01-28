import {OFFER_STATUS, paths} from "../common/constants";
import OfferModel from "../types/OfferModel";
import {AxiosInstance} from "axios";
import {ModifyFormValues} from "../components/EditOfferForm";
import {PaginatedResults} from "../types/PaginatedResults";
import {processPaginatedResults} from "../common/utils/utils";
import {CryptoFormValues} from "../components/CryptoFilters";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;
    private readonly axiosInstance: () => AxiosInstance;

    public constructor(axiosInstance: () => AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getOffers(params?: URLSearchParams): Promise<PaginatedResults<OfferModel>> {

        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params: params
        });

        return processPaginatedResults(resp, params);
    }

    public async getOfferInformation(offerId: number): Promise<OfferModel> {
        const resp = await this.axiosInstance().get<OfferModel>(this.basePath + offerId);
        return resp.data;
    }

    public async getOffersByOwner(username: string, status: OFFER_STATUS | undefined = OFFER_STATUS.Pending, page?: number): Promise<PaginatedResults<OfferModel>> {

        const params = new URLSearchParams();
        if (status === 'ALL') {
            status = undefined
        }
        params.append('by_user', username);
        if (page) {
            params.append('page', page.toString());
        }
        if (status) {
            params.append("status", status);
        }

        const resp = await this.axiosInstance().get<OfferModel[]>(this.basePath, {
            params: params
        })

        return processPaginatedResults(resp, params);
    }

    public async getOfferInformationByUrl(offerUrl: string): Promise<OfferModel> {
        const resp = await this.axiosInstance().get<OfferModel>(offerUrl);
        return resp.data;
    }

    public async modifyOffer(offer: ModifyFormValues, status?: OFFER_STATUS) {
        const resp = await this.axiosInstance().put<OfferModel[]>(this.basePath + offer.offerId, {
            cryptoCode: offer.cryptoCode,
            location: offer.location,
            minInCrypto: offer.minInCrypto,
            maxInCrypto: offer.maxInCrypto,
            unitPrice: offer.unitPrice,
            comments: offer.comments,
            offerStatus: status
        })
        return resp.data;
    }

    public getOfferIdFromURI(uri: string): string {
        const n = uri.lastIndexOf('/');
        return uri.substring(n + 1);
    }

    public getSearchParamsFromURI(uri: string): URLSearchParams {
        const n = uri.lastIndexOf('?');
        return new URLSearchParams(uri.substring(n + 1));
    }

    public async createOffer(minInCrypto: number, maxInCrypto: number, cryptoCode: string, location: string, unitPrice: number, firstChat?: string): Promise<void> {
        await this.axiosInstance().post<OfferModel[]>(this.basePath, {
            minInCrypto: minInCrypto,
            maxInCrypto: maxInCrypto,
            cryptoCode: cryptoCode,
            location: location,
            unitPrice: unitPrice,
            firstChat: firstChat
        });
    }

    public async pauseOffer(offer: OfferModel) {
        return this.modifyOffer({
            offerId: offer.offerId,
            cryptoCode: offer.cryptoCode,
            location: offer.location,
            minInCrypto: offer.minInCrypto,
            maxInCrypto: offer.maxInCrypto,
            unitPrice: offer.unitPrice,
            comments: offer.comments
        }, OFFER_STATUS.PausedBySeller);
    }

    public async deleteOffer(offer: OfferModel) {
        return this.modifyOffer({
            offerId: offer.offerId,
            cryptoCode: offer.cryptoCode,
            location: offer.location,
            minInCrypto: offer.minInCrypto,
            maxInCrypto: offer.maxInCrypto,
            unitPrice: offer.unitPrice,
            comments: offer.comments
        }, OFFER_STATUS.Deleted);
    }

    public async resumeOffer(offer: OfferModel) {
        return this.modifyOffer({
            offerId: offer.offerId,
            cryptoCode: offer.cryptoCode,
            location: offer.location,
            minInCrypto: offer.minInCrypto,
            maxInCrypto: offer.maxInCrypto,
            unitPrice: offer.unitPrice,
            comments: offer.comments
        }, OFFER_STATUS.Pending);
    }

    public getSearchParamsFromFilters(filters: CryptoFormValues|undefined,  orderCriteria?:string, username?:string|null): URLSearchParams{
        const params = new URLSearchParams();
        if(filters){
            console.log("a")
            if(filters.cryptos){
                for(let crypto of filters.cryptos){
                    params.append('crypto_code', crypto);
                }
            }
            if(filters.locations){
                for(let location of filters.locations){
                    params.append('location', location);
                }
            }
        }

        if(orderCriteria){
            console.log("b")
            params.append('order_by', orderCriteria);
        }

        params.append('status', OFFER_STATUS.Pending);

        if(username){
            console.log("c")
            params.append('exclude_user', username);
        }
        console.log(params)
        return params;
    }
}


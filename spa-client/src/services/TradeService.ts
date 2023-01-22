import {paths, TRADE_STATUS} from "../common/constants";
import {AxiosInstance, AxiosResponse} from "axios";
import TransactionModel from "../types/TransactionModel";
import {Link, PaginatedResults} from "../types/PaginatedResults";
import {getLinkHeaders, getPaginatorProps, processPaginatedResults} from "../common/utils/utils";


export class TradeService {

    private readonly basePath = paths.BASE_URL + paths.TRADE;
    private readonly axiosInstance:()=>AxiosInstance;

    public constructor(axiosInstance:()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }
    public async getTradeInformation(tradeId: number):Promise<TransactionModel> {
            const resp = await this.axiosInstance().get<TransactionModel>(this.basePath + tradeId);
            return resp.data;
    }
    public async getLastTransactions(username:string|null):Promise<TransactionModel[]>{
        const resp = await this.axiosInstance().get<TransactionModel[]>(this.basePath, {
            params: {
                buyer: username
            }
        });
        return resp.data;
    }
    public async getRelatedTrades(username:string|null, status?:string):Promise<PaginatedResults<TransactionModel>>{
        const params = new URLSearchParams();
        params.append("buyer", username!);
        if(status) {
            params.append("status", status);
        }
        const resp = await this.axiosInstance().get<TransactionModel[]>(this.basePath, {
            params: params
        });
        return processPaginatedResults(resp,params);
    }
    public async getTradesWithOfferId(offerId:number, status?:TRADE_STATUS, page?:number ):Promise<PaginatedResults<TransactionModel>>{
        const params = new URLSearchParams();

        if(status === TRADE_STATUS.All){
            status = undefined;
        }
        if(status){
            params.append("status", status);
        }
        params.append("from_offer", offerId.toString());
        if(page){
            params.append("page", page.toString());
        }

        const resp = await this.axiosInstance().get<TransactionModel[]>(this.basePath, {
            params: params
        });

        return processPaginatedResults(resp);
    }
    public async createTrade(amount:number, offerId:number|undefined):Promise<string>{
        const resp = await this.axiosInstance().post(paths.BASE_URL + paths.OFFERS + offerId + paths.TRADE, {
                 quantity: amount
        });
        return resp.headers["location"]!;
    }
    public async changeTradeStatus(tradeId:number, status:string):Promise<TransactionModel>{
        const resp = await this.axiosInstance().patch(paths.BASE_URL + paths.TRADE + tradeId , {
                newStatus: status
        });
        return resp.data;
    }

    public async getPaginatedTrades(uri:string):Promise<PaginatedResults<TransactionModel>>{
        const resp = await this.axiosInstance().get(uri);
        return processPaginatedResults(resp);
    }
    public async getRatingInfo(tradeId:number):Promise<AxiosResponse> {
        return await this.axiosInstance().get(this.basePath + tradeId + '/rating');
    }
    public async rateCounterPart(tradeId:number, rating:number):Promise<void>{
        await this.axiosInstance().patch(this.basePath + tradeId + '/rating', {
            rating:rating
        });
    }

}

import { paths } from "../common/constants";
import { AxiosInstance } from "axios";
import TransactionModel from "../types/TransactionModel";

export class TradeService {

    private readonly basePath = paths.BASE_URL + paths.TRADE;
    private readonly axiosInstance : AxiosInstance;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }
    public async getTradeInformation(tradeId: number):Promise<TransactionModel> {
            const resp = await this.axiosInstance.get<TransactionModel>(this.basePath, {
                params: {
                    tradeId: tradeId
                }
            });
            return resp.data;
    }
    public async getLastTransactions(username:string|null):Promise<TransactionModel[]>{
        const resp = await this.axiosInstance.get<TransactionModel[]>(this.basePath, {
            params: {
                buyer: username
            }
        });
        return resp.data;
    }
    public async getRelatedTrades(username:string|null, status:string):Promise<TransactionModel[]>{
        const resp = await this.axiosInstance.get<TransactionModel[]>(this.basePath, {
            params: {
                username: username,
                status: status
            }
        });
        return resp.data;
    }
    public async getTradesWithOfferId(offerId:number):Promise<TransactionModel[]>{
        const resp = await this.axiosInstance.get<TransactionModel[]>(this.basePath, {
            params: {
                from_offer: offerId,
            }
        });
        return resp.data;
    }
    public async createTrade(amount:number, offerId:number|undefined):Promise<TransactionModel>{
        const resp = await this.axiosInstance.post(paths.BASE_URL + offerId + paths.TRADE, {
            body: {
                offerId: offerId,
                amount:amount
            }
        });
        return resp.data;
    }
    public async changeTradeStatus(tradeId:number, status:string):Promise<TransactionModel>{
        const resp = await this.axiosInstance.put(paths.BASE_URL + paths.TRADE + tradeId , {
            body: {
                status: status
            }
        });
        return resp.data;
    }
}

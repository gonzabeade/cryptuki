import { paths } from "../common/constants";
import { AxiosInstance } from "axios";
import Result from "../types/Result";
import TransactionModel from "../types/TransactionModel";

export class TradeService {

    private readonly basePath = paths.BASE_URL + paths.TRADE;
    private readonly axiosInstance : AxiosInstance;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }
    public async getTradeInformation(tradeId: number):Promise<Result<TransactionModel>> {
            const resp = await this.axiosInstance.get<TransactionModel>(this.basePath, {
                params: {
                    tradeId: tradeId
                }
            });
            return Result.ok(resp.data);
    }
    public async getLastTransactions(username:string|null):Promise<Result<TransactionModel[]>>{
        const resp = await this.axiosInstance.get<TransactionModel[]>(this.basePath, {
            params: {
                buyer: username
            }
        });
        return Result.ok(resp.data);
    }
    public async getRelatedTrades(username:string|null, status:string):Promise<Result<TransactionModel[]>>{
        const resp = await this.axiosInstance.get<TransactionModel[]>(this.basePath, {
            params: {
                username: username,
                status: status
            }
        });
        return Result.ok(resp.data);
    }
    public async getTradesWithOfferId(offerId:number):Promise<Result<TransactionModel[]>>{
        const resp = await this.axiosInstance.get<TransactionModel[]>(this.basePath, {
            params: {
                from_offer: offerId,
            }
        });
        return Result.ok(resp.data);
    }
    public async createTrade(amount:number, offerId:number|undefined):Promise<Result<TransactionModel>>{
        const resp = await this.axiosInstance.post(paths.BASE_URL + offerId + paths.TRADE, {
            body: {
                offerId: offerId,
                amount:amount
            }
        });
        return Result.ok(resp.data);
    }
}
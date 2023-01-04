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
            return Result.ok({
                status: 'sold',
                buyer: {
                    accessToken: "",
                    refreshToken: "string",
                    admin: false,
                    email: "mdedeu@itba.edu.ar",
                    phoneNumber: "1245311",
                    username: "mdedeu",
                    lastLogin: "online",
                    trades_completed: 1,
                    rating: 1.3,
                    image_url: "/"
                },
                offer: {
                    cryptoCode: "BTC",
                    date: new Date(),
                    location: "Balvanera",
                    maxInCrypto: 2,
                    minInCrypto: 0.001,
                    offerId: 1,
                    offerStatus: "PENDING",
                    unitPrice: 1000000,
                    url: "/offer/1",
                    seller: {
                        accessToken: "",
                        refreshToken: "string",
                        admin: false,
                        email: "mdedeu@itba.edu.ar",
                        phoneNumber: "1245311",
                        username: "mdedeu",
                        lastLogin: "online",
                        trades_completed: 1,
                        rating: 1.3,
                        image_url: "/"
                    }
                },
                amount: 1000,
                id: 1,
                date: new Date()
            });
    }

}
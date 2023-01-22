import {AxiosInstance} from "axios";
import {CryptocurrencyModel} from "../types/Cryptocurrency";
import {paths} from "../common/constants";
export type TiendaCryptoAPI = { coin:string, buy:number, timestamp:Date, sell:number, variation:any };
export class CryptocurrencyService {

    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.CRYPTOCURRENCIES;
    private readonly  apiPath = "https://api.tiendacrypto.com/v1/price/all";

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

     public async getCryptocurrencies():Promise<CryptocurrencyModel[]> {
         const resp = await this.axiosInstance().get<CryptocurrencyModel[]>(this.basePath);
         return await this.getCryptoPrices(resp.data);
     }

    private async getCryptoPrices(coins: CryptocurrencyModel[]): Promise<CryptocurrencyModel[]> {
        const apiCall = await fetch(this.apiPath, {
            method: 'GET',
        }).then(res => res.json());

        for (let i = 0; i < coins.length; i++) {
            const value = apiCall[coins[i].code + '_ARS'];
            coins[i].price = (Number(value.buy) + Number(value.sell)) / 2;
        }
        return coins;


    }


}
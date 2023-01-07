import {AxiosInstance} from "axios";
import {CryptocurrencyModel} from "../types/Cryptocurrency";
import {paths} from "../common/constants";

export class CryptocurrencyService {

    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.CRYPTOCURRENCIES;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

     public async getCryptocurrencies():Promise<CryptocurrencyModel[]> {
         const resp = await this.axiosInstance().get<CryptocurrencyModel[]>(this.basePath);
         return resp.data;
     }


}
import {AxiosInstance} from "axios";
import {CryptocurrencyModel} from "../types/Cryptocurrency";
import Result from "../types/Result";
import {paths} from "../common/constants";

export class CryptocurrencyService {

    private readonly axiosInstance : AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.CRYPTOCURRENCIES;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

     public async getCryptocurrencies():Promise<Result<CryptocurrencyModel[]>> {

         const resp = await this.axiosInstance.get<CryptocurrencyModel[]>(this.basePath);


         if(resp.status === 200){
             //fetch TC data and add it to the response
             return Result.ok(resp.data);
         }
         return Result.fail(resp.data, 500);
     }


}
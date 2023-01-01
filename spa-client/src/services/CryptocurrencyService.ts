import {AxiosInstance} from "axios";
import jwtDecode from "jwt-decode";
import {CryptocurrencyModel} from "../types/Cryptocurrency";
import Result from "../types/Result";

export class CryptocurrencyService {

    private readonly axiosInstance : AxiosInstance;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public getCryptocurrencies():Result<CryptocurrencyModel[]>{
        //fetch to our backend and modify price with TC API
        return Result.ok([{name:"bitcoin", code:"BTC", price:102001301}]);

    }


}
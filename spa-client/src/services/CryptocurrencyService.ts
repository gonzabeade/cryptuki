import {AxiosInstance} from "axios";
import jwtDecode from "jwt-decode";

export class CryptocurrencyService {

    private readonly axiosInstance : AxiosInstance;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public getCryptocurrencies(){
        //fetch to our backend and modify price with TC API

    }


}
import {AxiosInstance} from "axios";
import Result from "../types/Result";
import {CryptocurrencyModel} from "../types/Cryptocurrency";
import {MessageModel} from "../types/MessageModel";

export class ChatService {

    private readonly axiosInstance : AxiosInstance;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public getMessages():Result<MessageModel[]>{
        //fetch to our backend and modify price with TC API
        return Result.ok([{content:"Hola", username:"marquitos"}, {content:"el otro", username:"marqui"}]);

    }


}
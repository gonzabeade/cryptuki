import axios, {AxiosInstance} from "axios";
import Result from "../types/Result";
import {CryptocurrencyModel} from "../types/Cryptocurrency";
import {MessageModel} from "../types/MessageModel";
import {paths} from "../common/constants";

export class ChatService {

    private readonly axiosInstance : AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.CHAT;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getMessages():Promise<Result<MessageModel[]>>{
       const resp = await this.axiosInstance.get<MessageModel[]>(this.basePath);
       return Result.ok(resp.data);
    }


}
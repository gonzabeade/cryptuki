import  {AxiosInstance} from "axios";
import Result from "../types/Result";
import {MessageModel} from "../types/MessageModel";
import {paths} from "../common/constants";

export class ChatService{

    private readonly axiosInstance : AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.TRADE;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getMessages(tradeId:number):Promise<Result<MessageModel[]>>{
       const resp = await this.axiosInstance.get<MessageModel[]>(this.basePath + tradeId + '/messages');
       return Result.ok(resp.data);
    }

    public async getUnseenMessagesCount(tradeId:number, username:string | null):Promise<Result<number>>{
        return Result.ok(2);
    }
    public async sendMessage(tradeId:number, content:string):Promise<Result<any>>{
        const resp = await this.axiosInstance.post(this.basePath + tradeId + '/messages', {body:{
                message:content
            }});
        return Result.ok(resp.data);
    }
}
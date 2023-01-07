import  {AxiosInstance} from "axios";
import {MessageModel} from "../types/MessageModel";
import {paths} from "../common/constants";

export class ChatService{

    private readonly axiosInstance : ()=>AxiosInstance;
    private readonly basePath = paths.BASE_URL + paths.TRADE;

    public constructor(axiosInstance: ()=>AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    public async getMessages(tradeId:number):Promise<MessageModel[]>{
       const resp = await this.axiosInstance().get<MessageModel[]>(this.basePath + tradeId + 'messages');
       return resp.data;
    }

    public async getUnseenMessagesCount(tradeId:number, username:string | null):Promise<number>{
       return 2;
    }
    public async sendMessage(tradeId:number, content:string):Promise<MessageModel>{
        const resp = await this.axiosInstance().post(this.basePath + tradeId + 'messages', {body:{
                message:content
            }});
        return resp.data;
    }
}
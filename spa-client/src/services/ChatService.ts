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
       const resp = await this.axiosInstance()
           .get<MessageModel[]>(this.basePath + tradeId + '/messages',
               {
                   headers:{
                       'Accept':'application/vnd.cryptuki.v1.message-list+json'
                   }
               });
       return resp.data;
    }


    public async sendMessage(tradeId:number, content:string):Promise<MessageModel>{
        const resp = await this.axiosInstance().post(this.basePath + tradeId + '/messages', {
                message:content
            },{
                headers:{
                    'Accept':'application/vnd.cryptuki.v1.message+json',
                    'Content-Type':'application/vnd.cryptuki.v1.message+json'
                }
        });
        return resp.data;
    }
}
import React, {useEffect, useState} from 'react';
import Message from "../Message";
import UserModel from "../../types/UserModel";
import {MessageModel} from "../../types/MessageModel";
import useChatService from "../../hooks/useChatService";
import i18n from "../../i18n";
import {toast} from "react-toastify";
import AdminMessage from "../AdminMessage";

type ChatSnippetProps = {
    buyer: UserModel ,
    seller:UserModel ,
    tradeId:number
}

const ChatMessagesForAdmin= ({ seller, tradeId ,buyer}:ChatSnippetProps) => {

    const [messages, setMessages] = useState<MessageModel[]>([]);
    const chatService = useChatService();

    async function getMessages(){
        try{
            const resp = await chatService.getMessages(tradeId);
            setMessages(resp);
        }catch (e) {
            //todo check api error
            toast.error(i18n.t('connectionError'));
        }

    }

    useEffect(()=>{
        getMessages();
    },[])


    return (
        <div className=" flex flex-col w-1/3 h-full">
            <h1 className="font-sans font-bold text-polard text-center mb-3">
                {i18n.t('chatHistory')}
            </h1>
            <div className="flex flex-row h-full w-full justify-around ">
            <div className="container mx-10 h-4/5 F border-gray-200">
                <div className=" border rounded bg-[#FAFCFF]">
                    <div>
                        <div className=" flex flex-col w-full">
                            <div className="relative flex items-center p-3 border-b border-gray-300">
                                <div className={"flex flex-row w-1/2 justify-start"}>
                                    <img className="object-cover w-10 h-10 rounded-full"
                                         src={seller?.picture} alt={seller?.username}/>
                                    <span className="block ml-2 font-bold text-gray-600 my-auto">{seller?.username}</span>
                                </div>
                                <div className="flex flex-row w-1/2 justify-end">
                                    <img className="object-cover w-10 h-10 rounded-full"
                                         src={buyer?.picture} alt={buyer?.username}/>
                                    <span className="block ml-2 font-bold text-gray-600 my-auto">{buyer?.username}</span>
                                </div>
                            </div>

                            <div className="relative w-full p-6 overflow-y-auto h-[25rem]">

                                <ul className="space-y-2">
                                    {
                                         messages && messages.map((message, key)=>{
                                            return (
                                                <AdminMessage key={key} content={message.content} sender={message.sender} seller={seller.self} />
                                            );
                                        })
                                    }

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
              </div>
        </div>
    );
};

export default ChatMessagesForAdmin;
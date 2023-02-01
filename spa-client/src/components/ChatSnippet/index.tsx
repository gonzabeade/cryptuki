import React, {useEffect, useState} from 'react';
import Message from "../Message";
import UserModel from "../../types/UserModel";
import {MessageModel} from "../../types/MessageModel";
import useChatService from "../../hooks/useChatService";
import {useForm} from "react-hook-form";
import {attendError} from "../../common/utils/utils";
import i18n from "../../i18n";
import {toast} from "react-toastify";

type ChatSnippetProps = {
    counterPart:UserModel | undefined,
    tradeId:number
}
type ChatFormValues ={
    message:string
}

const ChatSnippet= ({ counterPart, tradeId}:ChatSnippetProps) => {

    const [messages, setMessages] = useState<MessageModel[]>([]);
    const chatService = useChatService();
    const { register, handleSubmit, formState: { errors } , reset} = useForm<ChatFormValues>();

    async function getMessages(){
        try{
            if(tradeId){
                const resp = await chatService.getMessages(tradeId);
                setMessages(resp);
            }
        }catch (e) {
            toast.error("Error fetching messages. Check your connection " + e)
        }

    }

    useEffect(()=>{
      getMessages();
    },[tradeId])

    async function sendMessage(data:ChatFormValues){
        try{
            const resp = await chatService.sendMessage(tradeId, data.message);
            reset();
            await getMessages();
        }catch (e) {
         toast.error("Connection error. Failed to send message",e);
        }
    }

    return (
        <div className="flex flex-row h-full w-full justify-around mr-10 mt-10">
            <div className="container mx-10 h-4/5 F border-gray-200">
                <div className=" border rounded bg-[#FAFCFF]">
                    <div>
                        <div className=" flex flex-col w-full px-3">
                            <div className="flex relative items-center py-3 border-b border-gray-300 justify-between">
                                <div className={"flex"}>
                                    <img className="object-cover w-10 h-10 my-auto rounded-full"
                                         src={counterPart?.picture} alt={counterPart?.username}/>
                                    <div className="flex flex-col ml-4">
                                        <span className="block font-bold text-gray-600 text-justify ">{counterPart?.username}</span>
                                        {counterPart ? 
                                            <>
                                            <span className="font-sans text-gray-400 text-sm text-justify ">
                                                {i18n.t('lastLogin')}
                                            </span>
                                                <span
                                                    className="text-left text-xs text-justify text-gray-400 ">{counterPart ? counterPart.lastLogin.toString().substring(0, 10) : "Loading"}
                                                </span>
                                            </>

                                            : "Loading"}
                                    </div>
                                </div>
                                {counterPart? Date.parse(counterPart.lastLogin.toString()) === Date.now() &&  <span className="absolute w-3 h-3 bg-green-600 rounded-full left-7  top-6 "></span>:"Loading"}

                                <h1 className="text-right font-sans font-bold justify-self-end">
                                    {i18n.t('trade')} # {tradeId}
                                </h1>
                            </div>
                            <div className="relative w-full p-6 overflow-y-auto h-[25rem]">

                                <ul className="space-y-2">
                                    {
                                        messages && messages.map((message, key)=>{
                                            return (
                                                <Message key={key} content={message.content} senderURI={message.sender}/>
                                            );
                                        })
                                    }

                                </ul>
                            </div>

                            <div className="flex justify-end w-full p-3  border-gray-300">
                                <form className="flex flex-row w-full" onSubmit={handleSubmit(sendMessage)}>
                                    <input type="text" placeholder="Message"
                                           className="block w-full py-2 pl-4 mr-3 bg-gray-100 rounded-lg outline-none focus:text-gray-700"
                                           required
                                            {...register("message", { required: true })}
                                    />

                                    <button type="submit">
                                        <svg className="w-5 h-5 text-gray-500 origin-center transform rotate-90"
                                             xmlns="http://www.w3.org/2000/svg"
                                             viewBox="0 0 20 20" fill="currentColor">
                                            <path
                                                d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"/>
                                        </svg>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ChatSnippet;
import {ChatService} from "../services/ChatService";
import useChatService from "../hooks/useChatService";
import axios from "axios";
import {paths} from "../common/constants";
import {AxiosService} from "../services/AxiosService";


jest.mock('axios')


describe("get message", () =>{
    it("makes GET to proper URL", () =>{
        let chatService = new ChatService(() => axios)
        axios.get = jest.fn().mockResolvedValue({data: []})

        chatService.getMessages(1)

        expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.TRADE + "1" + "/messages")
        expect(axios.get).toHaveBeenCalledTimes(1)
    })
})

describe("send message", () =>{
    it("makes POST to proper URL", () =>{
        let chatService = new ChatService(() => axios)
        axios.post = jest.fn().mockResolvedValue({data: []})
        const message = "This is a new message"

        chatService.sendMessage(1, message)

        expect(axios.post).toHaveBeenCalledWith(
            paths.BASE_URL + paths.TRADE + "1" + "/messages",
            {"message" : message})
        expect(axios.post).toHaveBeenCalledTimes(1)
    })
})

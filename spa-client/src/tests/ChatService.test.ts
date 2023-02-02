import {ChatService} from "../services/ChatService";
import axios from "axios";
import {paths} from "../common/constants";


jest.mock('axios')

beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({data: []})
})

const message_mime = "application/vnd.cryptuki.v1.message+json"
const message_list_mime = "application/vnd.cryptuki.v1.message-list+json"

describe("get message", () =>{
    it("makes GET to proper URL", () =>{
        const chatService = new ChatService(() => axios)

        chatService.getMessages(1)

        expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.TRADE + "1/messages", {
            "headers": {
                "Accept": message_list_mime
            }
        })
        expect(axios.get).toHaveBeenCalledTimes(1)
    })
})

describe("send message", () =>{
    it("makes POST to proper URL", () =>{
        const chatService = new ChatService(() => axios)
        const message = "This is a new message"

        chatService.sendMessage(1, message)

        expect(axios.post).toHaveBeenCalledWith(
            paths.BASE_URL + paths.TRADE + "1/messages",
            {"message" : message},{
                "headers": {
                    "Accept": message_mime,
                    "Content-Type": message_mime
                }
            }
        )
        expect(axios.post).toHaveBeenCalledTimes(1)
    })
})

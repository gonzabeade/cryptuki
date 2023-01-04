import {useAxios} from "./useAxios";
import {ChatService} from "../services/ChatService";

const useChatService = () => {
    const axiosInstance = useAxios();
    return new ChatService(axiosInstance);
}

export default useChatService;
import React, {useEffect, useState} from 'react';
import useUserService from "../../hooks/useUserService";

type MessageProps = {
    senderURI:string,
    content:string
}
const Message = ({senderURI, content}:MessageProps) => {
    const [left, setLeft] = useState<boolean>(false);
    const userService = useUserService();

    useEffect(()=>{
        console.log(senderURI)
        if(senderURI){
            if(userService.getLoggedInUser() != userService.getUsernameFromURI(senderURI)){
                setLeft(true);
            }
        }
    }, [senderURI])

    return (
        <div className={`flex ${left ? 'justify-start':'justify-end' }`}>
            <div className= {`relative max-w-xl px-4 py-2 text-gray-700 rounded shadow " + ${ left ? '':'bg-[#eff6e5]' }` }>
                <span className="block">{content}</span>
            </div>
        </div>
    );
};

export default Message;

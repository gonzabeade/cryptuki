import React from 'react';
import {MessageModel} from "../../types/MessageModel";


type MessageProps = {
    left:boolean,
    content:string
}
const Message = ({left, content}:MessageProps) => {
    return (
        <div className={`flex ${left ? 'justify-start':'justify-end' }`}>
            <div className= {`relative max-w-xl px-4 py-2 text-gray-700 rounded shadow " + ${ left ? '':'bg-[#eff6e5]' }` }>
                <span className="block">{content}</span>
            </div>
        </div>
    );
};

export default Message;

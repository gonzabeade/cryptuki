import React from 'react';

type MessageProps = {
    left:boolean,
    content:string
}

const Message:React.FC<MessageProps> = ({left, content}) => {
    return (
        <li className="flex justify-start">
            <div className={"relative max-w-xl px-4 py-2 text-gray-700 rounded shadow" + left ? "":"bg-[#eff6e5]" }>
                <span className="block">{content}</span>
            </div>
        </li>
    );
};

export default Message;

import React from 'react';

type MessageProps = {
    left:boolean,
    content:string
}

const Message:React.FC<MessageProps> = ({left, content}) => {
    return (
        <div className={`flex ${left ? 'justify-start':'justify-end' }`}>
            <div className= {`relative max-w-xl px-4 py-2 text-gray-700 rounded shadow " + ${ left ? '':'bg-[#eff6e5]' }` }>
                <span className="block">{content}</span>
            </div>
        </div>
    );
};

export default Message;

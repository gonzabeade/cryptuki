import React, {useEffect, useState} from 'react';

type MessageProps = {
    sender:string,
    content:string,
    seller:string
}
const AdminMessage = ({sender,seller, content}:MessageProps) => {
    const [left, setLeft] = useState(false);

    useEffect(()=>{
        if(sender === seller)
            setLeft(true)
    },[])


    return (
        <div className={`flex ${left ? 'justify-start':'justify-end' }`}>
            <div className= {`relative max-w-xl px-4 py-2 text-gray-700 rounded shadow " + ${ left ? '':'bg-[#eff6e5]' }` }>
                <span className="block">{content}</span>
            </div>
        </div>
    );
};

export default AdminMessage;
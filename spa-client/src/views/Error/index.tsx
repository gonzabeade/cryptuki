import React from 'react';


type ErrorProps = {
    message:string,
    illustration:string
}
const Error = ({message, illustration}:ErrorProps) => {
    return (
        <>
            <div className="flex w-full my-2">
                <a className="mx-auto mt-10" href="/">
                    <img className='object-contain mx-auto' src={illustration}
                         alt="logo"/>
                </a>
            </div>
            <div className=" flex flex-col justify-center mx-20 my-10">
                <h1 className="text-2xl text-polard font-bold font-sans text-center">{message}</h1>
                <img src="/" alt="" className="h-96 mx-auto"/>
                <a href="/"
                   className="cursor-pointer mx-auto mt-2 p-4 text-sm text-white font-bold text-center bg-frost rounded-lg">Home</a>
            </div>
        </>
    );
};

export default Error;
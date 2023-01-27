import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
type ErrorProps = {
    illustration:string
}


const Index = ({illustration}:ErrorProps) => {
    const params = useParams();

    useEffect(()=>{
        toast.error(params.message)
    },[])

    return (
        <>
            <div className="flex w-full my-2">
                <a className="mx-auto mt-10" href="/">
                    <img className='object-contain mx-auto w-52' src={illustration}
                         alt="logo"/>
                </a>
            </div>
            <div className=" flex flex-col justify-center mx-20 my-10">
                <h1 className="text-2xl text-polard font-bold font-sans text-center">An error occurred.</h1>
                <a href="/"
                   className="cursor-pointer mx-auto mt-2 p-4 text-sm text-white font-bold text-center bg-frost rounded-lg">Home</a>
            </div>
        </>    );
};

export default Index;
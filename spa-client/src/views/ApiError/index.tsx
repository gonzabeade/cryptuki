import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import i18n from "../../i18n";
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
                <Link className="mx-auto mt-10" to="/">
                    <img className='object-contain mx-auto w-52' src={illustration}
                         alt="logo"/>
                </Link>
            </div>
            <div className=" flex flex-col justify-center mx-20 my-10">
                <h1 className="text-2xl text-polard font-bold font-sans text-center">{i18n.t('errorOccurred')}</h1>
                <Link to="/"
                   className="cursor-pointer mx-auto mt-2 p-4 text-sm text-white font-bold text-center bg-frost rounded-lg">{i18n.t('home')}</Link>
            </div>
        </>    );
};

export default Index;
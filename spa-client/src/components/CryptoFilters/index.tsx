import React, {useEffect, useState} from 'react';
import { useForm } from 'react-hook-form';
import {NEIGHBORHOODS} from "../../common/constants";
import {CryptocurrencyModel} from "../../types/Cryptocurrency";
import Result from "../../types/Result";

import useCryptocurrencyService from "../../hooks/useCryptocurrencyService";
import {toast} from "react-toastify";

type CryptoFormValues = {

    cryptos?:string[],
    locations?:string[],
    amount?:number,
    amountCurrency?:string
}
type CryptoFiltersProps = {
    callback:Function
}
const CryptoFilters = ({callback}:CryptoFiltersProps) => {
    const { register, handleSubmit, formState: { errors } } = useForm<CryptoFormValues>();
    const [cryptocurrencies, setCryptoCurrencies] = useState<CryptocurrencyModel[]>([]);
    const cryptocurrencyService = useCryptocurrencyService();

    async function fetchCryptocurrencies(){
        try{
            const apiCall:Result<CryptocurrencyModel[]> = await cryptocurrencyService.getCryptocurrencies();

            if(apiCall.statusCode === 200){
                setCryptoCurrencies(apiCall.getData());
            } else{
                toast.error("Something went wrong.");
            }
        }catch (e){
            toast.error("Connection error. Failed to fetch cryptocurrencies")

        }

    }

    useEffect(()=>{
        fetchCryptocurrencies();
    });


    function onSubmit(data:CryptoFormValues){
        callback(data);
    }

    return (
       <div className="flex flex-col m-3 w-full">
            <h3 className="font-roboto font-semibold text-2xl text-polar mx-auto my-2">Filters</h3>
           <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col bg-white drop-shadow-lg mx-3 rounded-lg">
               <div className="flex flex-col  px-6 py-3 mt-2">
                   <h2 className="font-semibold text-polar mx-auto text-lg">Amount</h2>
                   <div className="flex flex-row mx-auto">
                       <input type="number" placeholder="Amount" className="p-2 m-2 rounded-lg shadow" step="0.000001"
                           {...register("amount", {min:{value:0, message:"Amount must be greater than 0"}})} />
                       <select className="p-2 m-2 rounded-lg shadow"  {...register("amountCurrency")}>
                           <option>ARS</option>
                           {
                               cryptocurrencies.map((cryptocurrency)=>{
                                   return (
                                       <option value={cryptocurrency.code} key={cryptocurrency.code}>
                                           {cryptocurrency.commercialName}
                                       </option>
                                   );
                               })
                           }
                       </select>
                   </div>
                   {errors && errors.amount && <p className="text-red-600 mx-auto my-2">{errors.amount.message} </p>}
              </div>
               <div className="flex flex-col px-6 pb-3">
                   <h2 className="font-semibold text-polar mx-auto text-lg">Location</h2>
                   <select multiple className="p-2 m-2 rounded-lg shadow"  {...register("locations")}>
                       { NEIGHBORHOODS.map((neighborhood)=>{
                           return (
                               <option value={neighborhood} key={neighborhood}>
                                   {neighborhood}
                               </option>
                           );
                       })}
                   </select>
               </div>
               <div className="flex flex-col px-6 pb-3">
                   <h2 className="font-semibold text-polar mx-auto text-lg">Cryptos</h2>
                   <select multiple className="p-2 m-2 rounded-lg shadow"  {...register("cryptos")}>
                       {
                           cryptocurrencies.map((cryptocurrency)=>{
                               return (
                                   <option value={cryptocurrency.code} key={cryptocurrency.code}>
                                       {cryptocurrency.commercialName}
                                   </option>
                               );
                           })
                       }
                   </select>
               </div>
               <div className="bg-gray-100 w-full py-3 rounded-b-lg flex">
                   <button type="submit" className="rounded-lg bg-frostdr w-24 mx-auto p-2">
                       <div className="flex flex-row mx-auto justify-center">
                           <p className="font-roboto font-bold text-white mx-2 text-center">Filter</p>
                       </div>
                   </button>
               </div>

           </form>
       </div>
    );
};

export default CryptoFilters;
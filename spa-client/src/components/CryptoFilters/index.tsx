import React, {useEffect, useState} from 'react';
import { useForm } from 'react-hook-form';
import {NEIGHBORHOODS} from "../../common/constants";
import {CryptocurrencyModel} from "../../types/Cryptocurrency";

import useCryptocurrencyService from "../../hooks/useCryptocurrencyService";
import {toast} from "react-toastify";
import {attendError} from "../../common/utils/utils";
import i18n from "../../i18n";

export type CryptoFormValues = {

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
            const apiCall:CryptocurrencyModel[] = await cryptocurrencyService.getCryptocurrencies();
            setCryptoCurrencies(apiCall);
        }catch (e){
            attendError("Connection error. Failed to fetch cryptocurrencies",e)

        }

    }

    useEffect(()=>{
        fetchCryptocurrencies();
    },[]);


    function onSubmit(data:CryptoFormValues){
        callback(data);
    }

    return (
       <div className="flex flex-col m-3 w-full">
            <h3 className="font-roboto font-semibold text-2xl text-polar mx-auto my-2">{i18n.t('filters')}</h3>
           <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col bg-white drop-shadow-lg mx-3 rounded-lg">
               <div className="flex flex-col  px-6 py-3 mt-2">
                   <h2 className="font-semibold text-polar mx-auto text-lg">{i18n.t('offeredAmount')}</h2>
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
                   <h2 className="font-semibold text-polar mx-auto text-lg">{i18n.t('location')}</h2>
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
                   <h2 className="font-semibold text-polar mx-auto text-lg">{i18n.t('cryptocurrencies')}</h2>
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
                           <p className="font-roboto font-bold text-white mx-2 text-center">{i18n.t('filter')}</p>
                       </div>
                   </button>
               </div>

           </form>
       </div>
    );
};

export default CryptoFilters;
import React from 'react';
import { useForm } from 'react-hook-form';
import {ChevronDownIcon, MagnifyingGlassCircleIcon} from "@heroicons/react/24/outline";

type CryptoFormValues = {

    cryptos?:string[],
    locations?:string[],
    amount?:number,
    amountCurrency?:string
}
type CryptoFiltersProps = {
    callback:Function
}
//todo hay que mandarle un callback para actualizar las offers
const CryptoFilters = ({callback}:CryptoFiltersProps) => {
    const { register, handleSubmit, formState: { errors } } = useForm<CryptoFormValues>();

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
                           {...register("amount")} />
                       <select className="p-2 m-2 rounded-lg shadow"  {...register("amountCurrency")}>
                           <option>ARS</option>
                           <option>ETH</option>
                           <option>BTC</option>
                       </select>
                   </div>
              </div>
               <div className="flex flex-col px-6 pb-3">
                   <h2 className="font-semibold text-polar mx-auto text-lg">Location</h2>
                   <select multiple className="p-2 m-2 rounded-lg shadow"  {...register("locations")}>
                       <option>Barrio 1</option>
                       <option>Barrio 2</option>
                   </select>
               </div>
               <div className="flex flex-col px-6 pb-3">
                   <h2 className="font-semibold text-polar mx-auto text-lg">Cryptos</h2>
                   <select multiple className="p-2 m-2 rounded-lg shadow"  {...register("cryptos")}>
                       <option>USDC</option>
                       <option>USDT</option>
                       <option>DAI</option>
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


           {/*multiple cryptos*/}
           {/*multiple locations*/}
           {/*amount*/}
       </div>
    );
};

export default CryptoFilters;
import React from 'react';
import { useForm } from 'react-hook-form';

type CryptoFormValues = {
    cryptos?:string[],
    locations?:string[],
    amount?:number
}

const CryptoFilters = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<CryptoFormValues>();

    function onSubmit(data:CryptoFormValues){
        console.log(data)
    }

    return (
       <div className="m-3 w-full  border-r-2 border-frostdr">
            <h3 className="font-roboto font-semibold text-xl text-polar">Filters</h3>
           <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col">
               <input type="checkbox" placeholder="Location" {...register("locations", {})} />
               <button type="submit">Filter</button>
           </form>
           {/*multiple cryptos*/}
           {/*multiple locations*/}
           {/*amount*/}
       </div>
    );
};

export default CryptoFilters;
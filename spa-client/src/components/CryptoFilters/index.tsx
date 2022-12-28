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
       <div className="flex flex-col m-3 w-full">
            <h3 className="font-roboto font-semibold text-xl text-polar mx-auto my-2">Filters</h3>
           <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col  bg-polarl  drop-shadow-lg p-6 mx-3 rounded-lg">
               <div className="flex flex-col my-3">
                   <h2 className="font-semibold text-storm mx-auto">Amount in ARS</h2>
                   <input type="number" placeholder="Amount" className="p-2 m-2 rounded-lg bg-storml"/>
              </div>
               <div className="flex flex-col my-3">
                   <h2 className="font-semibold text-storm mx-auto">Location</h2>
                   <input type="number" placeholder="Amount" className="p-2 m-2 rounded-lg bg-storml"/>
               </div>
               <div className="flex flex-col my-3">
                   <h2 className="font-semibold text-storm mx-auto">Cryptos</h2>
                   <input type="number" placeholder="Amount" className="p-2 m-2 rounded-lg bg-storml"/>

               </div>
               <button type="submit">Filter</button>
           </form>


           {/*multiple cryptos*/}
           {/*multiple locations*/}
           {/*amount*/}
       </div>
    );
};

export default CryptoFilters;
import React from 'react';
import {useForm} from "react-hook-form";
import useUserService from "../../hooks/useUserService";
import {useSearchParams} from "react-router-dom";
import {useAuth} from "../../contexts/AuthContext";

type ContactFormValues = {
    email:string|null,
    message:string|null,
    tradeId?:number
}
//TODO errors and tradeId param?
const Support= () => {

    const {user} = useAuth();
    const { register, handleSubmit, formState: { errors } } = useForm<ContactFormValues>({defaultValues: async () => getDefaultValues()} );
    const [searchParams]= useSearchParams();

    function getDefaultValues(): ContactFormValues{
        return {
            email: user?.email!,
            message: null
        }
    }


    function onSubmit(data:ContactFormValues){
        if(searchParams && searchParams.get("tradeId")){

        }
    }

    return (
        <>
            <div className=" flex  flex-col justify-center mx-10">
                <div className="flex flex-col mt-10 mb-10 ">
                    <h1 className="text-center text-4xl font-semibold font-sans text-polar">
                        Need help?
                    </h1>
                    <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                        In case of any inconvenience, contact us.
                    </h3>
                    <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                        We can help!
                    </h3>
                </div>
            </div>
            <div className="flex justify-center">
                <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                    <div className="flex flex-col p-5 justify-center">
                        <div className="flex-row justify-center">
                            <input type="email" className="min-w-full h-10 justify-center rounded-lg p-2" placeholder="Email" {...register("email", {required:true})}/>
                        </div>
                    </div>
                    <div className="flex flex-col p-5 ">
                        <div className="flex-row justify-center">
                            <textarea className="min-w-full h-32 rounded-lg mx-auto p-5"  placeholder="Message" {...register("message", {required:true})}/>
                        </div>
                    </div>
                    <div className="flex flex-row p-5">
                        <button type="submit" className=" font-bold bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                            Submit
                        </button>
                    </div>
                </form>
            </div>
        </>


);
};

export default Support;
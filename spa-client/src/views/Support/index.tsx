import React, {useState} from 'react';
import {useForm} from "react-hook-form";
import useComplainService from "../../hooks/useComplainService";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {attendError} from "../../common/utils/utils";

export interface CreateComplainForm {
    email: string,
    tradeId:number,
    message:string
}

type ContactFormProps = {
    tradeId: number
}

const Support= ({tradeId}:ContactFormProps) => {

    const { register, handleSubmit, formState: { errors } } = useForm<CreateComplainForm>( );
    const complainService = useComplainService();
    const params = useParams();
    const [backButton, setBackButton] = useState<boolean>(false);
    const navigate = useNavigate();

    async function onSubmit(data:CreateComplainForm){
        try {
            await complainService.createComplain(data);
            toast("Your complaint was saved. As short as possible, we will have an answer.")
            setBackButton(true);
        }catch (e){
            attendError("The complaint could not be saved",e );
        }
    }

    return (
        <>
            {!backButton && <div className=" flex  flex-col justify-center mx-10">
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
            </div>}
            <div className="flex justify-center">
                {!backButton && <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                    <input type="hidden" value={params.id} {...register("tradeId")}/>
                    <div className="flex flex-col p-5 justify-center">
                        <div className="flex-row justify-center">
                            <input type="email" className="min-w-full h-10 justify-center rounded-lg p-2" placeholder="Email" {...register("email", {required:"Email is required."})}/>
                            {errors && errors.email && <span className="text-red-500">{errors.email.message}</span>}
                        </div>
                    </div>
                    <div className="flex flex-col p-5 ">
                        <div className="flex-row justify-center">
                            <textarea className="min-w-full h-32 rounded-lg mx-auto p-5"  placeholder="Message" {...register("message", {required:"Message is required."})}/>
                            {errors && errors.message && <span className="text-red-500">{errors.message.message}</span>}
                        </div>
                    </div>
                    <div className="flex flex-row p-5">
                        <button type="submit" className=" font-bold bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                            Submit
                        </button>
                    </div>
                </form>}
                {backButton && <div>
                    <div className="flex flex-col mt-10 mb-10 ">
                        <h1 className="text-center text-4xl font-semibold font-sans text-polar">
                            Our team is working to solve your issue.
                        </h1>
                        <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                            Remember, we know our sellers.
                        </h3>
                        <h3 className="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">
                            Don't worry!
                        </h3>
                    </div>
                    <div className="flex flex-row p-5">
                        <button onClick={()=>navigate("/")} className=" font-bold bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">
                            Go home
                        </button>
                    </div>
                </div>}
            </div>
        </>


);
};

export default Support;
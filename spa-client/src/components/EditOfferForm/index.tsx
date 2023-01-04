import React, {useEffect, useState} from 'react';
import {CryptocurrencyModel} from "../../types/Cryptocurrency";
import useCryptocurrencyService from "../../hooks/useCryptocurrencyService";
import Result from "../../types/Result";
import {NEIGHBORHOODS} from "../../common/constants";
import {toast} from "react-toastify";
import {useForm} from "react-hook-form";
import {useParams} from "react-router-dom";
import OfferModel from "../../types/OfferModel";
import {UploadFormValues} from "../UploadForm/uploadForm";

interface ModifyFormValues extends UploadFormValues {
   offerId:number
}
//TODO- Errors and pattern validations

const EditOfferForm = () => {

    //State

    const params = useParams();
    const [cryptocurrencies, setCryptoCurrencies] = useState<CryptocurrencyModel[]>([]);
    const cryptocurrencyService = useCryptocurrencyService();
    const [offer, setOffer] = useState<OfferModel>();

    function fetchCryptocurrencies(){
        const apiCall:Result<CryptocurrencyModel[]> = cryptocurrencyService.getCryptocurrencies();

        if(apiCall.statusCode === 200){
            setCryptoCurrencies(apiCall.getData());
        } else{
            toast.error("Something went wrong.");
        }
    }
    //TODO: This feels like a bad practice. Maybe useEffect on selected value?
    function changeSuggestedPrice(){
        const selectCryptos:HTMLSelectElement = document.getElementById("cryptoSelected")! as HTMLSelectElement;
        const cryptoModel:CryptocurrencyModel = cryptocurrencies.find(cryptocurrency=> cryptocurrency.code ===  selectCryptos.value)!;
        const price = document.getElementById("priceCrypto") as HTMLElement;
        price.innerHTML = cryptoModel?.price ? cryptoModel?.price.toString() + ' ARS': 'No price detected' ;
    }
    async function offerInitialValues(){
        //build form values with offer
       return {
           offerId:offer?.offerId || 1,
            minInCrypto: offer?.minInCrypto || 0,
            maxInCrypto:offer?.maxInCrypto || 0 ,
            location:offer?.location || 'DEFAULT',
            unitPrice:offer?.unitPrice || 0,
            cryptoCode:offer?.cryptoCode || 'BTC',
            automaticResponse: offer?.comments || 'a'
       }
    }

    useEffect(()=>{
        fetchCryptocurrencies();
    },[])
    useEffect(()=>{
        //fetch offer with param id
        //set Offer
        //set initial values

    })



    //Form
    const { register, handleSubmit, formState: { errors }, getValues } = useForm<ModifyFormValues>({defaultValues: async () => offerInitialValues()});

    function onSubmit(data:ModifyFormValues) {

        //add offer id param  and modify
        console.log(data);
    }

    return (
        <div className="flex flex-row mx-auto">
            <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                <div className="flex flex-row divide-x">
                    <div className="flex flex-col mx-5 w-1/3">
                        <h1 className="font-sans text-polar font-bold text-xl text-center">1. Price
                            Settings </h1>
                        <div className="flex flex-col justify-center">
                            <label
                                className="text-lg font-sans text-polard  mb-3 mt-2 text-center">Cryptocurrency*</label>
                            <div className="flex flex-col justify-center mx-auto">
                                <select className="rounded-lg p-3" id="cryptoSelected"
                                        {...register("cryptoCode",{required:"You must choose a cryptocurrency to sell", validate:{
                                                notDefault: value => value !== "DEFAULT" || "You must choose a cryptocurrency to sell"
                                            }, onChange:changeSuggestedPrice})} defaultValue="DEFAULT">
                                    <option disabled value="DEFAULT">Choose an option</option>
                                    {
                                        cryptocurrencies.map((cryptocurrency)=>{
                                            return (
                                                <option value={cryptocurrency.code} key={cryptocurrency.code}>
                                                    {cryptocurrency.name}
                                                </option>
                                            );
                                        })
                                    }
                                </select>
                                {errors && errors.cryptoCode && <p className="text-red-600 mx-auto mt-2">{errors.cryptoCode.message}</p> }
                            </div>
                            <h1 className="flex flex-row mx-auto mt-4">
                                <p className="text-sm text-gray-400 mr-2">*Suggested Price </p>
                                <p className="text-sm text-gray-400" id="priceCrypto">Select a coin </p>
                            </h1>
                        </div>
                        <div className="flex flex-col mt-4">
                            <label className="text-lg font-sans text-polard  mb-3 text-center ">Price per unit in
                                ARS*</label>
                            <div className="flex flex-col justify-center ">
                                <input type="number" className="h-10 justify-center rounded-lg p-3 mx-auto "
                                       step=".01"
                                       {...register("unitPrice", {required:"You must set a price per unit", min: {value:100, message:"Please input an amount greater than 100 ARS"}})}
                                />
                            </div>
                            {errors && errors.unitPrice && <p className="text-red-600 mx-auto mt-2">{errors.unitPrice.message}</p> }
                        </div>
                        <div className="flex flex-col justify-center mt-4">
                            <h2 className="text-lg font-sans text-polard mb-3 text-center flex flex-row justify-center ">Limits*</h2>
                            <div className="flex flex-row justify-center">
                                <div>
                                    <label
                                        className="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center ">
                                        Min in
                                        <p id="minCoin" className="mx-2">{offer?.cryptoCode}</p></label>
                                    <div className="flex flex-row justify-center mx-auto">
                                        <input type="number" className="h-10 justify-center rounded-lg p-3 mx-5 w-20"
                                               step=".00000001"
                                               {...register("minInCrypto", {
                                                   required: "Minimum amount is required",
                                                   min: {value: 0, message: "Please input an amount greater than 0"},
                                                   validate: {
                                                       smallerThanMax: value => value <= getValues().maxInCrypto || "Min must be smaller than max",
                                                   }
                                               })}/>
                                    </div>
                                    {errors && errors.minInCrypto && <p className="text-red-600 mx-auto mt-2">{errors.minInCrypto.message}</p> }
                                </div>
                                <div className="mt-12">
                                    -
                                </div>
                                <div>
                                    <label
                                        className="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center">
                                        Max in
                                        <p id="maxCoin" className="mx-2">{offer?.cryptoCode}</p></label>
                                    <div className="flex flex-row justify-center mx-auto">
                                        <input type="number" className="h-10 justify-center rounded-lg p-3 mx-5 w-20"
                                               step=".00000001"
                                               {...register("maxInCrypto", {
                                                   required: "Maximum amount is required",
                                                   min: {value: 0, message: "Please input an amount greater than 0"},
                                                   validate: {
                                                       biggerThanMin: value => value >= getValues().minInCrypto || "Max must be bigger than min",
                                                   }
                                               })}/>
                                    </div>
                                    {errors && errors.maxInCrypto && <p className="text-red-600 mt-2 mx-auto">{errors.maxInCrypto.message}</p> }
                                </div>
                            </div>
                        </div>

                    </div>
                    <div className="flex flex-col mx-auto w-1/3 ">
                        <label
                            className="text-xl font-sans text-polar font-bold mb-3 text-center ">2.
                            Location</label>
                        <div className="flex flex-col justify-center px-5">
                            <h2 className="text-lg font-sans text-polard text-center flex flex-row justify-center my-3">Neighborhood*</h2>
                            <select className="font-sans text-polard mb-3 text-center rounded-lg p-2 " {...register("location",{
                                required: "Location must not be empty",
                                validate:{
                                    locationNotEmpty: value => value !== "DEFAULT" || "Must select location"
                                }
                            })}  defaultValue="DEFAULT">
                                <option disabled value="DEFAULT">Choose option</option>
                                { NEIGHBORHOODS.map((neighborhood)=>{
                                    return (
                                        <option value={neighborhood} key={neighborhood}>
                                            {neighborhood}
                                        </option>
                                    );
                                })}
                            </select>
                            {errors && errors.location && <p className="text-red-600 mx-auto">{errors.location.message}</p> }
                        </div>
                    </div>
                    <div className="flex flex-col px-10 w-1/3">
                        <label className="text-xl font-sans text-polar font-bold mb-3 text-center ">3. Automatic
                            Response</label>
                        <h2 className="text-justify">You can set an automatic response every time someone makes you a
                            trade proposal </h2>
                        <div className="flex flex-row justify-center w-80 mx-auto mt-2">
                            <textarea className="w-full h-36 rounded-lg mx-auto p-5"
                                      {...register("automaticResponse", {maxLength: {value:240, message:"Max length is 240 characters"}})}/>
                        </div>
                        {errors && errors.automaticResponse && <p className="mt-2 text-red-600 mx-auto">{errors.automaticResponse.message}</p> }
                    </div>
                </div>
                <div className="flex flex-row p-5 mx-auto">
                    <a className=" font-bold cursor-pointer bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans mx-5 w-32"
                       href="/">Cancel
                    </a>
                    <button type="submit"
                            className=" font-bold bg-frostdr text-white  mt-4 p-3 rounded-md font-sans  w-32 mx-5 active:cursor-progress">
                        Submit
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditOfferForm;
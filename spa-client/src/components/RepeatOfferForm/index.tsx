import React, {useEffect, useState} from 'react';
import {CryptocurrencyModel} from "../../types/Cryptocurrency";
import useCryptocurrencyService from "../../hooks/useCryptocurrencyService";
import {NEIGHBORHOODS} from "../../common/constants";
import {toast} from "react-toastify";
import {useForm} from "react-hook-form";
import useOfferService from "../../hooks/useOfferService";
import {useNavigate, useParams} from "react-router-dom";
import i18n from "../../i18n";
import {attendError} from "../../common/utils/utils";
import OfferModel from "../../types/OfferModel";

export interface RepeatOfferFormValues {
    minInCrypto:number,
    maxInCrypto:number,
    location:string,
    unitPrice:number,
    cryptoCode:string,
    comments?:string
}

const RepeatOfferForm = () => {

    //State
    const [cryptocurrencies, setCryptoCurrencies] = useState<CryptocurrencyModel[]>([]);
    const cryptocurrencyService = useCryptocurrencyService();
    const offerService = useOfferService();
    const navigate = useNavigate();
    const [oldOffer, setOldOffer] = useState<OfferModel>();
    const params = useParams();

    async function fetchCryptocurrencies(){
        try{
            const apiCall:CryptocurrencyModel[] = await cryptocurrencyService.getCryptocurrencies();
            setCryptoCurrencies(apiCall);
        }catch (e) {
            toast.error("Connection error. Failed to fetch cryptocurrencies " + e);
        }
    }

    function changeSuggestedPrice(){
        const selectCryptos:HTMLSelectElement = document.getElementById("cryptoSelected")! as HTMLSelectElement;
        const cryptoModel:CryptocurrencyModel = cryptocurrencies.find(cryptocurrency=> cryptocurrency.code ===  selectCryptos.value)!;
        const price = document.getElementById("priceCrypto") as HTMLElement;
        price.innerHTML = cryptoModel?.price ? cryptoModel?.price.toString() + ' ARS': 'No price detected' ;

        const minLabel = document.getElementById("minCoin");

        if(minLabel)
            minLabel.innerHTML= cryptoModel.code;

        const maxLabel = document.getElementById("maxCoin");

        if(maxLabel)
            maxLabel.innerHTML= cryptoModel.code;

    }

    async function getOffer(id:number) {
        try {
            const resp = await offerService.getOfferInformation(id);
            setOldOffer(resp);
        } catch (e) {
            attendError("Connection error. Failed to fetch offer", e);
        }
    }



    useEffect(()=>{
        if(params.id)
            fetchCryptocurrencies().then(()=>getOffer(Number(params.id)));
    },[])

    //Form
    const { register, handleSubmit, formState: { errors }, getValues } = useForm<RepeatOfferFormValues>();

    async function onSubmit(data:RepeatOfferFormValues) {
        try{
            const offer = await offerService.createOffer(data.minInCrypto, data.maxInCrypto, data.cryptoCode, data.location, data.unitPrice, data.comments);
            toast.success("Offer created");
            navigate('/seller/offer/'+offer.offerId);
        }catch (e) {
            toast.error("Check your connection. Creation of offer failed. " + e)
        }
    }

    return (
        <div className="flex flex-row mx-auto">
            <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                <div className="flex flex-row divide-x">
                    <div className="flex flex-col mx-5 w-1/3">
                        <h1 className="font-sans text-polar font-bold text-xl text-center">1. {i18n.t('priceSettings')} </h1>
                        <div className="flex flex-col justify-center">
                            <label
                                className="text-lg font-sans text-polard  mb-3 mt-2 text-center">{i18n.t('cryptocurrency')}*</label>
                            <div className="flex flex-col justify-center mx-auto">
                                <select className="rounded-lg p-3" id="cryptoSelected"
                                        {...register("cryptoCode",{required:"You must choose a cryptocurrency to sell", validate:{
                                                notDefault: value => value !== "DEFAULT" || "You must choose a cryptocurrency to sell"
                                            }, onChange:changeSuggestedPrice})} defaultValue="DEFAULT">
                                    <option disabled value="DEFAULT">{i18n.t('chooseAnOption')}</option>
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
                                {errors && errors.cryptoCode && <p className="text-red-600 mx-auto mt-2">{errors.cryptoCode.message}</p> }
                            </div>
                            <h1 className="flex flex-row mx-auto mt-4">
                                <p className="text-sm text-gray-400 mr-2">*{i18n.t('suggestedPrice')} </p>
                                <p className="text-sm text-gray-400" id="priceCrypto">{i18n.t('selectACoin')}</p>
                            </h1>
                        </div>
                        <div className="flex flex-col mt-4">
                            <label className="text-lg font-sans text-polard  mb-3 text-center ">{i18n.t('unitPrice')}
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
                            <h2 className="text-lg font-sans text-polard mb-3 text-center flex flex-row justify-center ">{i18n.t('limits')}*</h2>
                            <div className="flex flex-row justify-center">
                                <div>
                                    <label
                                        className="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center ">
                                        {i18n.t('minIn')}
                                        <p id="minCoin" className="mx-2">BTC</p></label>
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
                                        {i18n.t('maxIn')}
                                        <p id="maxCoin" className="mx-2">BTC</p></label>
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
                            {i18n.t('location')}</label>
                        <div className="flex flex-col justify-center px-5">
                            <h2 className="text-lg font-sans text-polard text-center flex flex-row justify-center my-3">{i18n.t('hood')}*</h2>
                            <select className="font-sans text-polard mb-3 text-center rounded-lg p-2 " {...register("location",{
                                required: "Location must not be empty",
                                validate:{
                                    locationNotEmpty: value => value !== "DEFAULT" || "Must select location"
                                }
                            })}  defaultValue="DEFAULT">
                                <option disabled value="DEFAULT">{i18n.t('chooseAnOption')}</option>
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
                        <h2 className="text-justify">{i18n.t('automaticResponseDetail')} </h2>
                        <div className="flex flex-row justify-center w-80 mx-auto mt-2">
                            <textarea className="w-full h-36 rounded-lg mx-auto p-5"
                                      {...register("comments", {maxLength: {value:240, message:"Max length is 240 characters"}})}/>
                        </div>
                        {errors && errors.comments && <p className="mt-2 text-red-600 mx-auto">{errors.comments.message}</p> }
                    </div>
                </div>
                <div className="flex flex-row p-5 mx-auto">
                    <div className="font-bold cursor-pointer bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans mx-5 w-32"
                         onClick={()=>navigate(-1)}>{i18n.t('cancel')}
                    </div>
                    <button type="submit"
                            className=" font-bold bg-frostdr text-white  mt-4 p-3 rounded-md font-sans  w-32 mx-5 active:cursor-progress">
                        {i18n.t('send')}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default RepeatOfferForm;
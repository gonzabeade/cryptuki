import React, {useEffect, useState} from 'react';
import {CryptocurrencyModel} from "../../types/Cryptocurrency";
import useCryptocurrencyService from "../../hooks/useCryptocurrencyService";
import {NEIGHBORHOODS} from "../../common/constants";
import {toast} from "react-toastify";
import {useForm} from "react-hook-form";
import useOfferService from "../../hooks/useOfferService";
import {useNavigate} from "react-router-dom";
import i18n from "../../i18n";
import OfferModel from "../../types/OfferModel";
import UserModel from "../../types/UserModel";
import useUserService from "../../hooks/useUserService";
import {AxiosError} from "axios";

export interface RepeatOfferFormValues {
    minInCrypto:number,
    maxInCrypto:number,
    location:string,
    unitPrice:number,
    cryptoCode:string,
    comments?:string
}
type repeatOfferProps={
    offerId: number;
}

const RepeatOfferForm = ({offerId}:repeatOfferProps) => {

    //State
    const [cryptocurrencies, setCryptoCurrencies] = useState<CryptocurrencyModel[]>([]);
    const cryptocurrencyService = useCryptocurrencyService();
    const offerService = useOfferService();
    const navigate = useNavigate();
    const [offer,setOffer] = useState<OfferModel>();
    const [seller, setSeller] = useState<UserModel>();
    const userService = useUserService();

    async function fetchCryptocurrencies(){
        try{
            const apiCall:CryptocurrencyModel[] = await cryptocurrencyService.getCryptocurrencies();
            setCryptoCurrencies(apiCall);
        }catch (e) {
            toast.error(i18n.t('connectionError')+ i18n.t('failedToFetch') + i18n.t('cryptocurrencies'));
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

    async function getOffer(offerId:number){
        try{
                const resp = await offerService.getOfferInformation(offerId);
                setOffer(resp);
                return resp;
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError'));
        }
    }


    useEffect(()=>{
        fetchCryptocurrencies();
    },[])

    async function fetchSeller(url:string){
        try{
            setSeller(await userService.getUserByUrl(url));
        }catch (e) {
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError'));
        }
    }

    useEffect(()=>{
        if(offer)
            fetchSeller(offer.seller);
    },[offer]);

    useEffect(()=>{
        if(offer && seller && seller.username !== userService.getLoggedInUser())
            navigate("/offer/"+offer.offerId)
    },[offer,seller])

    async function offerInitialValues(){
        //build form values with offer
        const offerRetrieved = await getOffer(offerId);
        return {
            minInCrypto:0,
            maxInCrypto:0,
            location: offerRetrieved?.location || 'DEFAULT',
            unitPrice: offerRetrieved?.unitPrice || 0,
            cryptoCode: offerRetrieved?.cryptoCode || 'BTC',
            comments: offerRetrieved?.comments || 'DEFAULT'
        }
    }

    //Form
    const { register, handleSubmit, formState: { errors }, getValues } = useForm<RepeatOfferFormValues>({defaultValues: async () => offerInitialValues()});

    async function onSubmit(data:RepeatOfferFormValues) {
        try{
            const offer = await offerService.createOffer(data.minInCrypto, data.maxInCrypto, data.cryptoCode, data.location, data.unitPrice, data.comments);
            toast.success("Offer created");
            navigate('/seller/offer/'+offer.offerId);
        }catch (e) {
            toast.error(i18n.t('failedToCreateOffer') + e)
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
                                        {...register("cryptoCode",{required:i18n.t('ChooseCrypto')!, validate:{
                                                notDefault: value => value !== "DEFAULT" || i18n.t('ChooseCrypto')!
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
                                       {...register("unitPrice", {required:i18n.t('pricePerUnit')!, min: {value:100, message:i18n.t('greaterThan100')!}})}
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
                                                   required: i18n.t("minimumAmountRequired")!,
                                                   min: {value: 0, message: i18n.t('greaterThan100')!},
                                                   validate: {
                                                       smallerThanMax: value => value <= getValues().maxInCrypto || i18n.t('minSmallerThanMax')!,
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
                                                   required: i18n.t("maximumAmountRequired")!,
                                                   min: {value: 0, message: i18n.t("greaterThanZero")},
                                                   validate: {
                                                       biggerThanMin: value => value >= getValues().minInCrypto ||  i18n.t('maxBiggerThanMin')!,
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
                                required: i18n.t('noEmptyLocation')!,
                                validate:{
                                    locationNotEmpty: value => value !== "DEFAULT" || i18n.t('noEmptyLocation')!
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
                        <label className="text-xl font-sans text-polar font-bold mb-3 text-center ">3. {i18n.t('firstChat')}</label>
                        <h2 className="text-justify">{i18n.t('automaticResponseDetail')} </h2>
                        <div className="flex flex-row justify-center w-80 mx-auto mt-2">
                            <textarea className="w-full h-36 rounded-lg mx-auto p-5"
                                      {...register("comments", {maxLength: {value:240, message:i18n.t('maxLength')!}})}/>
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
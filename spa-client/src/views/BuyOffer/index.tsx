import React, { useEffect, useState} from 'react';
import UserInfo from "../../components/UserInfo/index";
import {Link, useNavigate, useParams} from "react-router-dom";
import useOfferService from "../../hooks/useOfferService";
import OfferModel from "../../types/OfferModel";
import { useForm} from "react-hook-form";
import {toast} from "react-toastify";
import useTradeService from "../../hooks/useTradeService";
import UserModel from "../../types/UserModel";
import useUserService from "../../hooks/useUserService";

type BuyOfferFormValues = {
    amount:number
}
const BuyOffer = () => {
    const { register, handleSubmit, formState: { errors }, setValue } = useForm<BuyOfferFormValues>();

    const params = useParams();
    const offerService = useOfferService();
    const navigate = useNavigate();
    const [offer, setOffer] = useState<OfferModel>();
    const [seller, setSeller] = useState<UserModel>();
    const tradeService = useTradeService();
    const userService = useUserService();


    async function  retrieveOfferInformation(offerId:number){
        try{
            const resp = await  offerService.getOfferInformation(offerId);
            setOffer(resp);

        }catch (e) {
            toast.error("Connection error. Couldn't fetch offer");
        }

    }
    function fillCrypto(e:any){
        const arsAmount:number = parseInt(e.target.value);
        if(offer){
            const cryptoAmount:number =  arsAmount / offer?.unitPrice
            const cryptoInput:HTMLInputElement = document.getElementById("crypto_amount")! as HTMLInputElement;
            if(!isNaN(cryptoAmount))
                cryptoInput.value = cryptoAmount.toString();
        }
    }

    function fillARS(e:any){
        const cryptoAmount:number = parseInt(e.target.value);
        if(offer){
            const arsAmount:number =  cryptoAmount * offer?.unitPrice
            const arsInput:HTMLInputElement = document.getElementById("ars_amount")! as HTMLInputElement;
            if(!isNaN(arsAmount)){
                arsInput.value = arsAmount.toString();
                setValue('amount', arsAmount);
            }

        }
    }
    async function onSubmit(data:BuyOfferFormValues){
        try{
            const resp = await tradeService.createTrade(data.amount, offer?.offerId);
            navigate('/trade/' + resp.tradeId);
        }catch (e){
            toast.error("Connection error. Failed to create trade");
        }
    }

    useEffect(()=>{
        if(params.id){
           retrieveOfferInformation(Number(params.id));
        }
    },[])

    async function fetchSeller(username:string){
        try{
            setSeller(await userService.getUser(username));
        }catch (e) {
            toast.error("Connection error. Couldn't fetch seller");
        }
    }
    useEffect(()=>{
            //fetch offer seller model
        if(offer){
            fetchSeller(userService.getUsernameFromURI(offer.seller));
        }

    },[offer]);


    return (
        <div className="flex flex-wrap mt-10 mx-5">
            <div className="flex flex-col w-3/5 shadow rounded-lg bg-white">
                <div className="flex">
                    <div className="flex flex-col mx-auto mt-10">
                        <h2 className="font-sans font-semibold text-polard text-2xl text-center">
                            You are about to buy
                        </h2>
                        {/*TODO hacer como me dijo gonza con los icons */}
                        <img src={`/images/${offer? offer.cryptoCode + '.png':'404.png'}`} alt={offer?.cryptoCode} className="w-20 h-20 mx-auto"/>
                        <h1 className="text-center text-3xl font-bold text-polar">
                            {offer? offer.cryptoCode: 'Loading...'}
                        </h1>
                        <h2 className="font-sans font-medium  text-xl text-center text-polar">

                            {offer? offer.unitPrice + ' ARS': 'Loading...'}

                        </h2>
                        <div className="flex flex-row mt-3 font-sans ">
                            <h2 className="font-sans mx-2"><b className="text-polar">
                                Minimum acceptable offer:</b> {offer? offer.minInCrypto * offer.unitPrice  + ' ARS': 'Loading...' }
                            </h2>
                            <h2 className="font-sans"><b className="text-polar">
                                Maximum acceptable offer: </b>{offer? offer.maxInCrypto * offer.unitPrice + ' ARS': 'Loading...' }
                            </h2>
                        </div>
                        <h2 className="pt-2 font-sans text-center"><b className="text-polar">
                            Location: </b>
                            {offer? offer.location : 'Loading...' }
                        </h2>
                    </div>
                </div>
                <form className="flex flex-col mt-5" onSubmit={handleSubmit(onSubmit)}>
                    <label className="mx-auto text-center">Amount in ARS</label>
                    <input className="p-2 m-2 rounded-lg shadow mx-auto" placeholder="Amount in ARS"  {...register("amount", {required:"You must input an amount"})} onChange={(e)=>fillCrypto(e)} id={"ars_amount"}/>
                    {errors && errors.amount && <p className={"text-red-600 mx-auto"}> {errors.amount.message}</p>}
                    <p className="mx-auto font-bold text-polar">or</p>
                    <label className="mx-auto text-center mt-3">Amount in crypto</label>
                    <input className="p-2 m-2 rounded-lg shadow mx-auto" placeholder={`Amount in CRYPTO`}  onChange={(e)=>fillARS(e)} id={"crypto_amount"}/>
                    <div className="flex flex-row justify-evenly mt-3 mb-3">
                        <Link to="/" className="p-3 w-48 bg-polarlr/[0.6] text-white font-roboto rounded-lg font-bold text-center cursor-pointer" >Cancel</Link>
                        <button type="submit" className=" w-48 p-3 bg-frostdr text-white font-roboto rounded-lg font-bold">Make trade proposal</button>
                    </div>
                </form>

            </div>
            <div className="flex flex-col w-2/5 items-center">
            <UserInfo
                username={seller? (seller.username): 'Loading...'}
                email={seller? seller.email: 'Loading'}
                phone_number={seller? seller.phoneNumber :'Loading'}
                last_login={seller? seller.lastLogin.toString().substring(0,10): 'Loading'}
                trades_completed={seller? seller.ratingCount: 0}
                rating={seller? seller.rating: 0}/>

                    <h1 className="font-sans font-bold text-2xl mx-auto text-polar mt-6 mb-2">
                        Location
                    </h1>
                { offer?.location ? <iframe
                    title={"google maps"}
                    className="mx-auto mb-10"
                    width="450"
                    height="250"
                    frameBorder="0"
                    referrerPolicy="no-referrer-when-downgrade"
                    src={`https://www.google.com/maps/embed/v1/search?key=${process.env.REACT_APP_GMAPS_API_KEY}&q=${offer.location}`}
                    allowFullScreen>
                </iframe>:
                    <div>Loading Map...</div>}

            </div>
        </div>
    );
};

export default BuyOffer;
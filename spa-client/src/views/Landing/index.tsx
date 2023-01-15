import {useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import CryptoFilters, {CryptoFormValues} from "../../components/CryptoFilters/index";
import Paginator from "../../components/Paginator";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";
import useUserService from "../../hooks/useUserService";
import {OFFER_STATUS} from "../../common/constants";
import {PaginatorPropsValues} from "../../types/PaginatedResults";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]|null>();
    const offerService = useOfferService();
    const userService = useUserService();
    const [isLoading, setIsLoading] = useState<boolean>(true);


    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
        actualPage: 1,
        totalPages: 1,
        nextUri:'',
        prevUri:''
        }
    );


    async function getOffers(page?:number){
        try{
            const apiCall = await offerService?.getOffers(page, userService.getLoggedInUser()!, [OFFER_STATUS.Pending, OFFER_STATUS.Sold]);
            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps);
            setIsLoading(false);
        }catch (e){
            toast.error("Connection error. Failed to fetch offers")
        }
    }

    async function getPaginatedOffers(uri:string){
        console.log(uri)
        try{
            const apiCall = await offerService?.getPaginatedOffers(uri);
            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps);
            setIsLoading(false);
        }catch (e){
            toast.error("Connection error. Failed to fetch offers")
        }
    }

    async function orderOffers(order_by:string){
        try{
            const apiCall = await offerService?.getOrderedOffers(paginatorProps.actualPage, order_by );
            setOffers(apiCall);
        }catch (e) {
            toast.error("Connection error. Failed to fetch offers")
        }
    }
    async function getOffersWithFilters(data:CryptoFormValues){
        console.log(data);
    }

    useEffect(  ()=>{
            getOffers();
        }, []);

    return (<>
            <div className="flex flex-wrap w-full h-full justify-between">
                <div className="flex w-1/3">
                    <CryptoFilters callback={getOffersWithFilters}/>
                </div>


                    {!isLoading ?
                        <>
                        <div className="flex flex-col w-2/3 mt-10">
                            <div className="flex flex-row mx-10 justify-between">
                                <div className="flex flex-row">
                                    <h3 className="font-bold mx-2 my-auto">Order by</h3>
                                    <form>
                                        <select className="p-2 rounded-lg" onChange={(e)=>{orderOffers(e.target.value)} }>
                                            <option value={"PRICE_LOWER"}>Lowest Price</option>
                                            <option value={"DATE"}>Most recent</option>
                                            <option value={"RATE"}>Best Rated user</option>
                                            <option value={"PRICE_UPPER"}>Higher price</option>
                                            <option value={"LAST_LOGIN"}>Seller Last login</option>
                                        </select>
                                    </form>
                                </div>
                                <div>
                                    <h3 className="text-gray-400">You got {offers? offers.length: 0} results</h3>
                                </div>
                            </div>
                            {offers && offers.map((offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
                            {offers && offers.length > 0 &&  <Paginator paginatorProps={paginatorProps} callback={getPaginatedOffers}/>}
                            {!offers && <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> No offers available at this moment</h1>}

                        </div>
                        </>
                        :
                        <div className="flex flex-col w-2/3 mt-10">
                            <Loader/>
                        </div>
                    }

            </div>
    </>


    )

}; 

export default Landing; 
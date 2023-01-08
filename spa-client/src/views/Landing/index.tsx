import {useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import {useLocation, useNavigate,  useSearchParams} from "react-router-dom";
import CryptoFilters from "../../components/CryptoFilters/index";
import Paginator from "../../components/Paginator";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]|null>([]);
    const offerService = useOfferService();
    const [actualPage, setActualPage] = useState<number>(1);
    const [totalPages, setTotalPages] = useState<number>(1);

    async function getOffers(page?:number, pageSize?:number){
        try{
            const apiCall = await offerService?.getOffers(page, pageSize);
            setOffers(apiCall);
            //get Headers and set Actual and total pages
        }catch (e){
            toast.error("Connection error. Failed to fetch offers")
        }

    }
    async function orderOffers(order_by:string){
        try{
            const apiCall = await offerService?.getOrderedOffers(actualPage, order_by );
            setOffers(apiCall);
        }catch (e) {
            toast.error("Connection error. Failed to fetch offers")
        }
    }

    useEffect(  ()=>{
            getOffers();
        }, []);

    return (<>
            <div className="flex flex-wrap w-full h-full justify-between">
                <div className="flex w-1/3">
                    {/*TODO this callback should update offers*/}
                    <CryptoFilters callback={()=>console.log("messi")}/>
                </div>


                    {offers ?
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
                            {offers.map((offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
                            {offers.length > 0 &&  <Paginator totalPages={totalPages} actualPage={actualPage} callback={() => console.log("called")}/>}

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
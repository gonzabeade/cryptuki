import {useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import CryptoFilters, {CryptoFormValues} from "../../components/CryptoFilters/index";
import Paginator from "../../components/Paginator";
import Loader from "../../components/Loader";
import useUserService from "../../hooks/useUserService";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import {useAuth} from "../../contexts/AuthContext";
import i18n from "../../i18n";
import {toast} from "react-toastify";


const Landing = () => {
    const [offers, setOffers] = useState<OfferModel[]|null>();
    const offerService = useOfferService();
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const [filters, setFilters] = useState<CryptoFormValues>();
    const [orderCriteria, setOrderCriteria] = useState<string>();

    const {user} = useAuth();
    const userService = useUserService();

    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
        actualPage: 0,
        totalPages: 0,
        nextUri:'',
        prevUri:''
        }
    );


    async function getOffers(){
        try{

            setIsLoading(true);

            const params =  offerService.getSearchParamsFromFilters(filters, orderCriteria, userService.getLoggedInUser());

            const apiCall = await offerService?.getOffers(params);

            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setIsLoading(false);

        }catch (e){
            //todo check errors
            toast.error(i18n.t('connectionError') + i18n.t('failedToFetch') + i18n.t('offers'));
        }
    }

    //Callback from Paginator component
    async function getPaginatedOffers(uri:string){
        try{
            const params = offerService.getSearchParamsFromURI(uri);
            setIsLoading(true);

            const apiCall = await offerService?.getOffers(params);
            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);

            setIsLoading(false);
        }catch (e){
            //todo check errors
            toast.error(i18n.t('connectionError')+ i18n.t('failedToFetch') + i18n.t('offers'));
        }
    }

    async function orderOffers(order_by:string){
            setOrderCriteria(order_by);
    }


    async function getOffersWithFilters(data:CryptoFormValues){
            setFilters(data);
    }

    useEffect(() => {
        getOffers();
    }, [user, orderCriteria, filters]);

    return (<>
            <div className="flex flex-wrap w-full h-full justify-between">
                <div className="flex w-1/3">
                    <CryptoFilters callback={getOffersWithFilters}/>
                </div>
                        <>
                            <div className="flex flex-col w-2/3 mt-10">
                                <div className="flex flex-row mx-10 justify-between">
                                    <div className="flex flex-row">
                                        <h3 className="font-bold mx-2 my-auto">{i18n.t('orderBy')}</h3>
                                        <select className="p-2 rounded-lg" onChange={(e) => {
                                            orderOffers(e.target.value)
                                        }}>
                                            <option value={"DATE"}>{i18n.t('dateOrder')}</option>
                                            <option value={"PRICE_LOWER"}>{i18n.t('priceLowToHigh')}</option>
                                            <option value={"RATE"}>{i18n.t('ratingFilter')}</option>
                                            <option value={"PRICE_UPPER"}>{i18n.t('priceHighToLow')}</option>
                                            <option value={"LAST_LOGIN"}>{i18n.t('loginOrder')}</option>
                                        </select>
                                    </div>
                                    {!isLoading &&
                                        <div>
                                            <h3 className="text-gray-400">{i18n.t('youGot')} {offers ? offers.length : 0} {i18n.t('results')}</h3>
                                        </div>
                                    }
                                </div>
                                {!isLoading && offers && offers.map((offer => <CryptoCard offer={offer}
                                                                            key={offer.offerId}></CryptoCard>))}
                                { !isLoading &&  offers && offers.length > 0 &&
                                    <Paginator paginatorProps={paginatorProps} callback={getPaginatedOffers}/>}
                                {!isLoading &&  (!offers || offers.length === 0) &&
                                    <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> {i18n.t('noResults')}</h1>}
                                {isLoading &&
                                    <div className="flex flex-col w-2/3 mt-10 mx-auto">
                                    <Loader/>
                                </div>
                                }
                            </div>
                        </>
            </div>
    </>


    )

}; 

export default Landing; 
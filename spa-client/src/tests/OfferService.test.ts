import {OfferService} from "../services/OfferService";
import axios from "axios";
import * as utils from "../common/utils/utils";
import {OFFER_STATUS, paths} from "../common/constants";
import exp from "constants";
import {CryptoFormValues} from "../components/CryptoFilters";

jest.mock('axios')

beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({data: []})
    axios.put = jest.fn().mockResolvedValue({data: []})
    axios.patch = jest.fn().mockResolvedValue({data: []})
})

const basicOffer = {
    cryptoCode: "ETH",
    location: "Boedo",
    minInCrypto: 2,
    maxInCrypto: 3,
    unitPrice: 6,
    comments: "These are comments of the modified offer",
}

const offerStatus = OFFER_STATUS.Sold
const firstChat = "Buenas, quier comprar BTC"
const offerId = 5

const cryptoFormValues : CryptoFormValues = {
    cryptos: ["ETH"],
    locations: ["Recoleta"],
    amount: 10,
    amountCurrency: "20"
}

test("get offers", () => {
    const offerService = new OfferService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();
    let params = new URLSearchParams();
    params.append("param_1","a");
    params.append("param_2", "b");

    offerService.getOffers(params)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.OFFERS, {params})
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get offer information", () => {
    const offerService = new OfferService(() => axios)

    offerService.getOfferInformation(2)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.OFFERS + "2")
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get offers by owner", () => {
    const offerService = new OfferService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    const username = "salvaCasta"
    const status = OFFER_STATUS.PausedBySeller
    const page = 4

    let params = new URLSearchParams
    params.append('by_user', username)
    params.append('page', page.toString())
    params.append('status', status)

    offerService.getOffersByOwner(username, status, page)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.OFFERS, {params})
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get offer information by url", () => {
    const offerService = new OfferService(() => axios)
    const test_url = "test_url"

    offerService.getOfferInformationByUrl(test_url)

    expect(axios.get).toHaveBeenCalledWith(test_url)
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get offer id from uri", () => {
    const offerService = new OfferService(() => axios)

    const testedId = offerService.getOfferIdFromURI(paths.BASE_URL + paths.OFFERS + "/67")

    expect(testedId).toMatch("67")
})

test("get search params from uri", () => {
    const offerService = new OfferService(() => axios)
    let params = "param1=2&param2=1"

    const testedParams = offerService.getSearchParamsFromURI(paths.BASE_URL + paths.OFFERS + "?" + params)

    expect(testedParams.toString()).toMatch(params)
})

test("modify offer", () => {
    const offerService = new OfferService(() => axios)

    offerService.modifyOffer({
        minInCrypto: basicOffer.minInCrypto,
        maxInCrypto: basicOffer.maxInCrypto,
        location: basicOffer.location,
        unitPrice: basicOffer.unitPrice,
        cryptoCode: basicOffer.cryptoCode,
        comments: basicOffer.comments,
        offerId: offerId
    }, offerStatus)

    expect(axios.put).toHaveBeenCalledWith(paths.BASE_URL + paths.OFFERS + offerId.toString(), {
        minInCrypto: basicOffer.minInCrypto,
        maxInCrypto: basicOffer.maxInCrypto,
        location: basicOffer.location,
        unitPrice: basicOffer.unitPrice,
        cryptoCode: basicOffer.cryptoCode,
        comments: basicOffer.comments,
        offerStatus: offerStatus
    })

    expect(axios.put).toHaveBeenCalledTimes(1)

})

test("create offer", () => {
    const offerService = new OfferService(() => axios)

    offerService.createOffer(
        basicOffer.minInCrypto,
        basicOffer.maxInCrypto,
        basicOffer.cryptoCode,
        basicOffer.location,
        basicOffer.unitPrice,
        firstChat
        )

    expect(axios.post).toHaveBeenCalledWith(paths.BASE_URL + paths.OFFERS, {
        minInCrypto: basicOffer.minInCrypto,
        maxInCrypto: basicOffer.maxInCrypto,
        cryptoCode: basicOffer.cryptoCode,
        location: basicOffer.location,
        unitPrice: basicOffer.unitPrice,
        firstChat: firstChat
    })

    expect(axios.post).toHaveBeenCalledTimes(1)
})

test("get search params from filters", () => {
    const offerService = new OfferService(() => axios)
    const order_by = "locations"
    const username = "salvaCasta"

    let params = new URLSearchParams()
    if(cryptoFormValues.cryptos)
        params.append('crypto_code', cryptoFormValues.cryptos[0])
    if(cryptoFormValues.locations)
        params.append('location', cryptoFormValues.locations[0])
    params.append('order_by', order_by)
    params.append('status', OFFER_STATUS.Pending);
    params.append('exclude_user', username)

    const testedParams = offerService.getSearchParamsFromFilters(cryptoFormValues, order_by, username)

    expect(testedParams.toString()).toMatch(params.toString())
})

import {TradeService} from "../services/TradeService";
import axios from "axios";
import * as utils from "../common/utils/utils";
import exp from "constants";
import {CryptoFormValues} from "../components/CryptoFilters";
import {TRADE_STATUS, paths} from "../common/constants";

jest.mock('axios')

const username = "salvaCasta"
const status = TRADE_STATUS.Deleted
const page = 5
const tradeId = 6
const amount = 10
const location = "Boedo"
const trade_url = "trade_url"
const rating = 3

beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({headers: new Headers({"location": location})})
    axios.put = jest.fn().mockResolvedValue({data: []})
    axios.patch = jest.fn().mockResolvedValue({data: []})
})

test("get trade information", () => {
    const tradeService = new TradeService(() => axios)

    tradeService.getTradeInformation(1)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.TRADE + "1")
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get trade information by url", () => {
    const tradeService = new TradeService(() => axios)

    tradeService.getTradeInformationByUrl(trade_url)

    expect(axios.get).toHaveBeenCalledWith(trade_url)
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get last transactions", () => {
    const tradeService = new TradeService(() => axios)
    //jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    tradeService.getLastTransactions(username)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.TRADE, {
        params : {
            buyer : username
        }
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get related trades", () => {
    const tradeService = new TradeService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    let params = new URLSearchParams()
    params.append('buyer', username)
    params.append('status', status)

    tradeService.getRelatedTrades(username, status)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.TRADE, {
        params : params
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get trades with offer id", () => {
    const tradeService= new TradeService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    let params = new URLSearchParams()
    params.append('status', status)
    params.append('from_offer', tradeId.toString())
    params.append('page', page.toString())

    tradeService.getTradesWithOfferId(tradeId, status, page)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.TRADE, {
        params : params
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("create trade", () => {
    const tradeService= new TradeService(() => axios)

    tradeService.createTrade(amount, tradeId)

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.OFFERS +
        tradeId.toString() +
        paths.TRADE,{
            quantity: amount
        })
    expect(axios.post).toHaveBeenCalledTimes(1)
})

test("change trade status", () => {
    const tradeService= new TradeService(() => axios)

    tradeService.changeTradeStatus(tradeId, status)

    expect(axios.patch).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.TRADE +
        tradeId.toString(), {
            newStatus: status
        }
    )
    expect(axios.patch).toHaveBeenCalledTimes(1)
})

test("get paginated trades", () => {
    const tradeService = new TradeService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    tradeService.getPaginatedTrades(trade_url)

    expect(axios.get).toHaveBeenCalledWith(trade_url)
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get rating info", () => {
    const tradeService = new TradeService(() => axios)

    tradeService.getRatingInfo(tradeId)

    expect(axios.get).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.TRADE +
        tradeId.toString() +
        "/rating"
    )
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("rate counterpart", () => {
    const tradeService = new TradeService(() => axios)

    tradeService.rateCounterPart(tradeId, rating)

    expect(axios.patch).toHaveBeenCalledWith(
        paths.BASE_URL +
        paths.TRADE +
        tradeId.toString() +
        "/rating", {
            rating: rating
        }
    )
})
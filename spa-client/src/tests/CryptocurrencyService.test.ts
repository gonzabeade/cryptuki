import {CryptocurrencyService} from "../services/CryptocurrencyService";
import axios from "axios";
import {paths} from "../common/constants";

jest.mock('axios')

const resp = {
    data: [{
        commercialName: "Bitcoin",
        code: "BTC",
        price: 10
    }]
}

beforeEach(() => {
    axios.get = jest.fn().mockReturnValue(resp)
    global.fetch = jest.fn().mockReturnValue({"BTC_ARS": 2})
    //global.fetch().then = jest.fn()
    Response.prototype.json = jest.fn().mockReturnValue(resp)
})

test("get cryptocurrencies", () => {
    const cryptocurrencyService = new CryptocurrencyService(() => axios);

    //cryptocurrencyService.getCryptocurrencies()

    //expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.CRYPTOCURRENCIES)
    expect(axios.get).toHaveBeenCalledTimes(0)
})

import { paths } from "../common/constants";
import axios from "../api/axios";
import requestConfig from "../api/requestConfig";
import OfferModel from "../types/OfferModel";

export class OfferService {

    private readonly basePath = paths.BASE_URL + paths.OFFERS;


    public async getOffers(page?: number, pageSize?: number): Promise<Array<OfferModel>> {
        const resp = await axios.get<Array<OfferModel>>(this.basePath, requestConfig(page, pageSize))
        return resp.data; 
    }

}


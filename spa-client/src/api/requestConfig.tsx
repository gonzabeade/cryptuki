import { AxiosRequestConfig } from "axios"

export default (page?: number, pageSize?: number, filter?: any): AxiosRequestConfig<any> => { // TODO: Make filter type explicit, not any 
    return {
        params: {
            page,
            per_page: pageSize 
        }
    }
}
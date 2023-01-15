export const paths = {
    BASE_URL: 'http://localhost:8080/webapp/api',
    HOME:'/',
    OFFERS: '/offers/',
    TRADE: '/trades/',
    USERS: '/users/',
    COMPLAINTS: '/complaints/',
    LOCATIONS: '/locations/',
    CRYPTOCURRENCIES: '/cryptocurrencies',
}
export const NEIGHBORHOODS = [
'ALMAGRO'
]
export const sleep = (ms:number) => new Promise((resolve) => setTimeout(resolve, ms));
export enum OFFER_STATUS {
    "Pending"='APR',
    "Sold" = 'SOL',
    "Deleted"='DEL',
    "PausedBySeller"='PSE'
}

export enum TRADE_STATUS {
    "Pending" ='PENDING',
    "Accepted" ='ACCEPTED',
    "Rejected" ='REJECTED',
    "Sold" ='SOLD',
    "Deleted" ='DELETED',

}
  

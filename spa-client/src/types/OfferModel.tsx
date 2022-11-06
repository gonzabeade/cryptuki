export default interface OfferModel {

        comments?: string,
        cryptoCode: string,
        date: Date,
        location: string,
        maxInCrypto: number,
        minInCrypto: number,
        offerId: number,
        offerStatus: string,
        unitPrice: number, 
        url: string, 


        seller: string, // TODO: Change to UserModel 
        trades: string  // TODO: Change to TradesModel[]  

}

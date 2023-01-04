import TransactionModel from "./TransactionModel";
import UserModel from "./UserModel";

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


        seller: UserModel, // TODO: Change to UserModel
        // trades: TransactionModel[]  // TODO: Change to TradesModel[] ESTO CHEQUEAR , ES CIRCULAR, deberia ir en otr pa mi

}

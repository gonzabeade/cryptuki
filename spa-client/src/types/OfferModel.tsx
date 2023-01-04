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


        seller: UserModel, // TODO: Change to UserModel

}

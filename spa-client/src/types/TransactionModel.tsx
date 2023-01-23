import OfferModel from "./OfferModel";
import UserModel from "./UserModel";

export default interface TransactionModel {
    tradeId:number
    status:string,
    buyingQuantity:number,
    lastModified:Date,
    buyer:string,
    seller:string,
    offer:string,
    messages:string,
    self:string,
    qUnseenMessagesBuyer:number,
    qUnseenMessagesSeller:number
}
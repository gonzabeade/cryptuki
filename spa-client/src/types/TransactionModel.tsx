import OfferModel from "./OfferModel";
import UserModel from "./UserModel";

export default interface TransactionModel {
    status:string,
    buyer:UserModel,
    offer:OfferModel
    amount:number
    id:number
    date:Date
    //TODO:lo q falte
}
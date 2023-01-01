import OfferModel from "./OfferModel";

export default interface TransactionModel {
    status:string,
    // icon:JSX.Element
    buyer:string,
    offer:OfferModel
    amount:number
    id:number
    date:Date
    //TODO:lo q falte
}
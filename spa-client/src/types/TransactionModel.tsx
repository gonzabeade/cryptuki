import OfferModel from "./OfferModel";

export default interface TransactionModel {
    status:string,
    icon:JSX.Element
    buyer:string,
    seller:string,
    offer:OfferModel
    //TODO:lo q falte
}
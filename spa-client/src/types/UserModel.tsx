export default interface UserModel {
    //normal
    userId:number,
    username:string,
    rating:number,
    lastLogin:Date,
    locale:string,
    ratingCount:number,
    //uris
    self:string;
    picture:string
    secrets:string;
    complaints:string,
    kycInformation:string,
    offers:string,
    email:string,
    phoneNumber:string,
    status:string,
}



export default interface UserModel {
    userId:number,
    username:string,
    rating:number,
    lastLogin:Date,
    locale:string,

    complaints:string,
    kycInformation:string,
    offers:string,
    self:string;
    secrets:string;
    accessToken: string, 
    refreshToken: string,
    email:string,
    phoneNumber:string,
    ratingCount:number,
    picture:string
}

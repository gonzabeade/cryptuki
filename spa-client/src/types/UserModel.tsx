//TODO hablar esto con @Gonza
export  interface UserSecretsModel{
    //secrets
    email:string,
    phoneNumber:string,
    status:string,
}

export interface UserDTONormal {
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
}

export default interface UserModel extends UserSecretsModel, UserDTONormal {}

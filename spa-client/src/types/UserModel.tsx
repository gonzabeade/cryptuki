export default interface UserModel {
    accessToken: string, 
    refreshToken: string, 
    admin: boolean;
    email:string,
    phoneNumber:string,
    username:string,
    lastLogin:string,
    trades_completed:number,
    rating:number,
    image_url:string
}

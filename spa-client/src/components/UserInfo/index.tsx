import React from 'react';
import RatingStars from "../RatingStars";

type UserInfoProps = {
    username:string,
    email:string,
    phone_number:string,
    last_login:string,
    trades_completed:number,
    rating:number
}
const UserInfo: React.FC<UserInfoProps> = ({username, email, phone_number, last_login, trades_completed, rating}:UserInfoProps) => {

    return (

        <div className="w-full max-w-sm rounded-lg shadow-md bg-gray-800  border-gray-700 ">
            <div className="flex flex-col items-center pb-10 pt-5">
                <h1 className="mb-1 text-sm font-medium text-white italic">Counterpart</h1>
                <h5 className="mb-1 text-xl font-medium text-white">{username}</h5>
                <span className="text-sm text-gray-400">{email}</span>
                <span className="text-sm text-gray-400">{phone_number}</span>
                <span className="text-sm text-gray-400">Last Active: {last_login}</span>
                <span className="text-sm text-gray-400">Trades Completed: {trades_completed}</span>

                <RatingStars rating={rating}/>
            </div>
        </div>

    );
};

export default UserInfo;

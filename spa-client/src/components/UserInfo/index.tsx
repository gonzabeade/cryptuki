import React from 'react';
import RatingStars from "../RatingStars";
import i18n from "../../i18n";

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
                <h1 className="mb-1 text-sm font-medium text-white italic">{i18n.t('counterPart')}</h1>
                <h5 className="mb-1 text-xl font-medium text-white">{username}</h5>
                <span className="text-sm text-gray-400">{email}</span>
                <span className="text-sm text-gray-400">{phone_number}</span>
                <span className="text-sm text-gray-400">{i18n.t('lastTimeActive')}: {last_login}</span>
                <span className="text-sm text-gray-400">{i18n.t('tradeQuantity')}: {trades_completed}</span>

                <RatingStars rating={rating/2}/>
            </div>
        </div>

    );
};

export default UserInfo;

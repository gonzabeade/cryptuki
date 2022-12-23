import React from 'react';
import UserInfo from "../../components/UserInfo";

const BuyOffer = () => {
    return (
       <UserInfo username={"mdedeu"}
                 email={"mdedeu@itba.edu.ar"}
                 phone_number={"1234141"}
                 last_login={"ayer"}
                 trades_completed={3}
                 rating={4.9}
       />
    );
};

export default BuyOffer;
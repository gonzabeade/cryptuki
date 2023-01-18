import React from 'react';

type data={
    username:string,
    last_login: Date
}


const KycPreview = ({username,last_login}:data) => {
    return (
        <div className="flex flex-col bg-white shadow rounded-lg p-3 m-5 font-sans font-bold">
            <div className="w-full mt-2 text-xl text-start"><b>Usuario:</b> {username}</div>
            <div className="w-full mt-2 text-xl text-start"><b>Ultimo login:</b> {last_login.toString().substring(0, 10)}</div>
            <div className="mx-auto my-3">
                <a href={`/admin/kyc/${username}`}
                   className=" text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto">Ver</a>
            </div>
        </div>
    );
};

export default KycPreview;
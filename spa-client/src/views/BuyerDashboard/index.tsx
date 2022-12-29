import React from 'react';
import UserProfileCards from "../../components/UserProfileCards";
import StatusCards from "../../components/StatusCards/StatusCards";

const BuyerDashboard = () => {
    return (
        <div className="flex h-full w-full px-20 my-10">

            <div className="flex flex-col h-full mx-20 w-1/5">
                <div className="">
                  <UserProfileCards username={"mdedeu"} phoneNumber={1234566} email={"mdedeu@itba.edu.ar"} rating={4.32} tradeQuantity={4}/>
                </div>
            </div>

            {/*//  Middle Panel: trade */}
            <div className="flex flex-col h-full mr-20 w-3/5">
                <div className="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-3xl font-semibold font-sans text-polar">Trade Proposals</h1>
                </div>
                    <StatusCards active={"pending"} base_url={"/buyer/mdedeu"} callback={()=>console.log("a")}/>
                <div  className="flex flex-col justify-center w-full mx-auto mt-10">
                   {/*foreach trade*/}
                </div>

                {/*<h2 className="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noBuyingProposalSend"/></h2>*/}
                {/* No trades */}

                <div className="flex flex-col mt-3">
                 {/*paginator*/}
                </div>
        </div>

    </div>
    );
};

export default BuyerDashboard;
import React, {useState} from 'react';
import {KycModel} from "../../types/KycModel";
import TransactionList from "../../components/TransactionList";
import TransactionModel from "../../types/TransactionModel";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";
import UserProfileCards from "../../components/UserProfileCards";


const SellerDashboard = () => {
    const [kyc, setKyc] = useState<KycModel>({status:''});
    const [lastTransactions, setLastTransactions] = useState<TransactionModel[]>([]);
    const [offers, setOffers] = useState<OfferModel[]>([]);

    return (
        <div className="flex h-full w-full px-10 my-10">

            {/*// Left Panel: chat and seller stats*/}
            <div className="flex flex-col h-full mx-10 px-10 w-1/3">
                <div>
                  <UserProfileCards username={"mdedeu"} phoneNumber={123457} email={"mdedeu@itba.edu.ar"} rating={4.5} tradeQuantity={2}/>
                </div>
                {!kyc && <>
                    <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-6 font-sans font-bold">
                        <img className="w-5 h-5 mr-4 my-auto " src="attention"/>
                        <p>
                            Validate your identity
                        </p>
                    </div>
                    <div className="mx-auto mt-8">
                        <a href="/kyc"
                           className="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto">
                            Start KYC
                        </a>
                    </div>
                </>}
                {
                    kyc.status !== 'APR' ?
                        <div className="flex flex-row bg-white shadow rounded-lg p-3 mt-3 font-sans font-bold">
                            <img className="w-5 h-5 mr-4 my-auto " src="attention"/>
                            <p>Validation of identity submitted. Please wait </p>
                        </div> :
                        <div className="mt-5">
                            <div className="py-4 bg-white rounded-lg shadow-md">
                                <div className="flex justify-between items-center mb-2 px-4 pt-2">
                                    <h5 className="text-xl font-bold leading-none text-polar">Last Transactions </h5>
                                </div>
                                <div className="px-4">
                                    <ul role="list" className="divide-y divide-gray-200">
                                        {/*{lastTransactions.map()  CREATE COMPONENT}*/}

                                        {/*    <c:forEach items="${tradeList}" var="trade">*/}
                                        {/*        <li className="py-2">*/}
                                        {/*            <a className="flex items-center space-x-4 hover:bg-gray-100 rounded-lg p-1 cursor-pointer" href="<c:url value="/chat?tradeId=${trade.tradeId}"/> ">*/}
                                        {/*            <div className="flex-shrink-0">*/}
                                        {/*                <c:if test="${trade.status == 'PENDING'}">*/}
                                        {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#ebcb8b" stroke-width="2">*/}
                                        {/*                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />*/}
                                        {/*                    </svg>*/}
                                        {/*                </c:if>*/}
                                        {/*                <c:if test="${trade.status == 'REJECTED'}">*/}
                                        {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#bf616a" stroke-width="2">*/}
                                        {/*                        <path stroke-linecap="round" stroke-linejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />*/}
                                        {/*                    </svg>*/}
                                        {/*                </c:if>*/}
                                        {/*                <c:if test="${trade.status == 'SOLD'}">*/}
                                        {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#e5e7eb" stroke-width="2">*/}
                                        {/*                        <path stroke-linecap="round" stroke-linejoin="round" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />*/}
                                        {/*                    </svg>*/}
                                        {/*                </c:if>*/}
                                        {/*                <c:if test="${trade.status == 'ACCEPTED'}">*/}
                                        {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#a3be8c" stroke-width="2">*/}
                                        {/*                        <path stroke-linecap="round" stroke-linejoin="round" d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />*/}
                                        {/*                    </svg>*/}
                                        {/*                </c:if>*/}
                                        {/*                <c:if test="${trade.status == 'DELETED'}">*/}
                                        {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#60a5fa" stroke-width="2">*/}
                                        {/*                        <path stroke-linecap="round" stroke-linejoin="round" d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636" />*/}
                                        {/*                    </svg>*/}

                                        {/*                </c:if>*/}

                                        {/*                <%--                                                    <img className="w-8 h-8 rounded-full" src="<c:url value="/profilepic/${trade.buyer.username.get()}"/>" alt="Profile pic">--%>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="flex-1 min-w-0">*/}
                                        {/*                <div className="flex flex-row justify-between">*/}
                                        {/*                    <p className="text-sm font-medium text-polar-600 truncate"> <c:out value="${trade.buyer.username.get()}"/> </p>*/}
                                        {/*                    <h1 className="text-sm  text-polar truncate"><messages:message code="${trade.status}"/> <messages:message code="for"/> <fmt:formatNumber type="number" maxFractionDigits="10" value="${trade.quantity / trade.offer.unitPrice}"/> <c:out value="${trade.offer.crypto.code}"/> </h1>*/}
                                        {/*                </div>*/}

                                        {/*                <p className="text-sm text-gray-500 truncate"><messages:message code="${trade.status}.madeYouAnOffer"/> </p>*/}
                                        {/*            </div>*/}
                                        {/*        </a>*/}
                                        {/*    </li>*/}
                                        {/*</c:forEach>*/}
                                    </ul>
                                </div>
                            </div>
                        </div>

                    //if not empty, render last transactions
                }

                <div className="mx-auto mt-5">
                    <a href="/upload"
                       className="py-2 pr-4 pl-3 text-lg text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto cursor-pointer">Upload
                        Advertisment</a>
                </div>
            </div>

            <div className="flex flex-col h-full mr-20 w-4/5">
                <div className="shadow-xl w-full h-1/8 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                    <h1 className="text-center text-4xl font-semibold font-sans text-polar">Your uploaded offers</h1>
                </div>
                <div className="flex flex-col w-full  mt-5">
                    <div className="flex w-full ">
                        {/* seller filters*/}
                    </div>
                    <div className="flex flex-wrap w-full mx-auto justify-center mt-2">
                        {/* CREATE COMPONENT OFFER and foreach for my offers*/}
                        {/*<c:forEach var="offer" items="${offerList}">*/}
                        {/*    <%--    Tarjeta de anuncio--%>*/}
                        {/*    <c:set var="offer" value="${offer}" scope="request"/>*/}
                        {/*    <jsp:include page="../../components/offer.jsp"/>*/}
                        {/*</c:forEach>*/}
                    </div>
                    {offers.length >= 1 && <div className="mx-auto">
                        <Paginator totalPages={10} actualPage={1} callback={() => console.log("messi")}/>
                    </div>}
                </div>
            </div>
        </div>
    );
};

export default SellerDashboard;
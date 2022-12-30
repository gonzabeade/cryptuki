import React from 'react';
import TransactionModel from "../../types/TransactionModel";
import OfferModel from "../../types/OfferModel";
import UserProfileCards from "../../components/UserProfileCards";

const Receipt: React.FC<TransactionModel> = ({
                                                 status,
                                                 icon,
                                                 buyer,
                                                 seller,
                                                 offer
                                             }) => {
    return (
        <>
            <div className="flex flex-row divide-x-2 divide-polard mt-5">
                <div className="flex flex-col w-3/5 h-screen">
                    <div className="flex flex-col mx-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" className=" mx-auto h-20 w-20" fill="none"
                             viewBox="0 0 24 24" stroke="#A3BE8C" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                        <h1 className="p-4 text-polard font-bold text-3xl font-sans mx-5 text-center ">
                            The exchange was successful.
                        </h1>
                        <h3 className="mx-auto text-polard text-center text-xl text-gray-500">
                            Thanks for using Cryptuki
                        </h3>
                    </div>
                    <div className="flex flex-col">
                        <h1 className="mx-auto text-polard font-bold text-2xl mt-10 mb-5">
                            Transaction Data
                        </h1>
                        <div
                            className="py-5 px-14 mx-auto rounded-lg bg-stormd/[0.9] flex  border-2 border-polard flex-col">
                            <div className="flex flex-row px-30">
                                <div className="flex flex-col">
                                    <h1 className="text-polard font-bold text-xl">
                                        You paid
                                    </h1>

                                    <div className="flex flex-row">
                                        <h2 className="text-xl  font-sans text-polar text-left my-auto">110000.34 ARS
                                        </h2>
                                    </div>
                                </div>
                                <div className="mx-10 my-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none"
                                         viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                                    </svg>
                                </div>
                                <div className="flex flex-col">
                                    <h1 className="text-polard font-bold text-xl">
                                        You received
                                    </h1>

                                    <div className="flex flex-row">
                                        <h2 className="text-xl font-sans text-polar text-left my-auto">
                                            0000.2
                                        </h2>
                                        <h1 className="text-xl  font-sans text-polar text-left my-auto ml-2">
                                            BTC
                                        </h1>
                                    </div>
                                </div>
                            </div>
                            <div className="flex flex-col my-10 px-30">
                                <h4 className="text-lg font-polard font-bold mx-auto">
                                    Transaction confirmation time
                                </h4>
                                <h2 className="text-xl font-sans text-polar text-center my-auto ">
                                    30-12-2022 12:01
                                </h2>
                            </div>
                        </div>
                    </div>


                    <div className="flex flex-row mt-10">


                        <a className="bg-frost text-white p-3 font-sans rounded-lg mx-auto  w-40 text-center"
                           href="/">
                            Home
                        </a>
                        <a className="bg-nred text-white p-3 font-sans rounded-lg mx-auto w-40 text-center"
                           href="/">
                            I had a problem
                        </a>
                    </div>
                </div>
            </div>
            <div className="flex flex-col w-2/5">
                <div className="flex flex-col ml-32">
                    <UserProfileCards username={"mdedeuSeller"} phoneNumber={1345252} email={"a@gmail.com"} rating={2.4} tradeQuantity={2}/>

                    <div className="flex flex-col mx-auto mt-10">

                        {/*rate component*/}
                        {/*<c:if*/}
                        {/*    test="${(trade.buyer.username.get() == username && trade.sellerRated == false) || (trade.offer.seller.username.get() == username && trade.buyerRated == false)}">*/}
                        {/*    <h1 className="text-polard font-sans  font-bold text-center text-3xl mx-auto">*/}
                        {/*        <messages:message code="rate"/>*/}
                        {/*        <c:out*/}
                        {/*            value="${trade.offer.seller.username.get() == username ? trade.buyer.username.get(): trade.offer.seller.username.get()}"/>*/}
                        {/*    </h1>*/}
                        {/*    <c:url value="/rate" var="postUrl"/>*/}
                        {/*    <form:form modelAttribute="ratingForm" action="${postUrl}" method="post">*/}

                        {/*        <form:hidden path="tradeId" value="${trade.tradeId}"/>*/}
                        {/*        <div className="flex flex-col">*/}
                        {/*            <form:errors path="rating" cssClass="mx-auto text-red-400"/>*/}
                        {/*            <form:label path="rating" cssClass="mx-auto">*/}
                        {/*                <messages:message code="ratingConditions"/>*/}
                        {/*            </form:label>*/}
                        {/*            <div className="flex flex-row mx-auto mt-3">*/}
                        {/*                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star1"*/}
                        {/*                      onClick="setRatingAndSend(1)" onMouseLeave="leaveHoverOnRating(1)"*/}
                        {/*                      onMouseOver="hoverOnRating(1)"></span>*/}
                        {/*                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star2"*/}
                        {/*                      onClick="setRatingAndSend(2)" onMouseLeave="leaveHoverOnRating(2)"*/}
                        {/*                      onMouseOver="hoverOnRating(2)"></span>*/}
                        {/*                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star3"*/}
                        {/*                      onClick="setRatingAndSend(3)" onMouseLeave="leaveHoverOnRating(3)"*/}
                        {/*                      onMouseOver="hoverOnRating(3)"></span>*/}
                        {/*                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star4"*/}
                        {/*                      onClick="setRatingAndSend(4)" onMouseLeave="leaveHoverOnRating(4)"*/}
                        {/*                      onMouseOver="hoverOnRating(4)"></span>*/}
                        {/*                <span className=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star5"*/}
                        {/*                      onClick="setRatingAndSend(5)" onMouseLeave="leaveHoverOnRating(5)"*/}
                        {/*                      onMouseOver="hoverOnRating(5)"></span>*/}

                        {/*            </div>*/}
                        {/*            <div className="flex flex-row">*/}
                        {/*                <form:hidden path="rating" value="0"/>*/}
                        {/*                <button type="submit" id="sendRating"*/}
                        {/*                        className="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress hidden">*/}
                        {/*                    <messages:message code="send"/>*/}
                        {/*                </button>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}

                        {/*    </form:form>*/}
                        {/*</c:if>*/}
                        {/*<c:if test="${rated == true }">*/}
                        {/*    <div className="mb-5 mt-5">*/}
                        {/*        <c:set var="ratingSent">*/}
                        {/*            <messages:message code="ratingSent"/>*/}
                        {/*        </c:set>*/}
                        {/*        <jsp:include page="../components/confirmationToggle.jsp">*/}
                        {/*            <jsp:param name="title" value="${ratingSent}"/>*/}
                        {/*        </jsp:include>*/}
                        {/*    </div>*/}
                        {/*</c:if>*/}
                    </div>
                </div>
            </div>
        </>
    )
};

export default Receipt;
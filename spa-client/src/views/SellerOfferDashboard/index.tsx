import React from 'react';
import OfferCardProfile from "../../components/OfferCardProfile";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";

type SellerOfferDashboardProps ={
    offer:OfferModel,
    selected:string
}
const SellerOfferDashboard:React.FC<SellerOfferDashboardProps> = ({offer, selected}) => {
    return (<>
            <div className="flex flex-row h-full w-full px-20 my-10">
                <div className="flex flex-col h-3/5 w-1/5 pr-2">
                    <div className="flex flex-col w-full py-3 rounded-lg px-5 pt-4 rounded-lg bg-[#FAFCFF]">
                        <h1 className="font-sans w-full mx-auto text-center text-2xl font-bold">
                            Trade proposals received
                        </h1>
                    </div>
                    <OfferCardProfile offer={offer}/>

                    <a href="/"
                       className="rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
                        Back
                    </a>
                </div>
                <div className="flex flex-col w-4/5">
                    {/*TODO componente */}
                    <div className="flex flex-row  rounded-lg px-5 rounded-lg  justify-between">
                        <div
                            className="flex mr-5 bg-white rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto w-full ">
                                <p className={`py-2 px-4 font-bold text-polar text-center ${selected === 'all' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    All
                                </p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-nyellow rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'pending' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Pending</p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-ngreen rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'accepted' ? 'decoration-frostdr underline underline-offset-8' : 'text-l'}`}>
                                    Accepted</p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-nred rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'rejected' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Rejected</p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-gray-200 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selected === 'sold' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Sold </p>
                            </a>
                        </div>
                        <div
                            className=" flex mr-5 bg-blue-400 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <a href="/" className="my-auto mx-auto">
                                <p className={`py-2  px-4 font-bold text-polar text-center ${selected === 'deleted' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Deleted by you</p>
                            </a>
                        </div>
                    </div>
                    <div className="flex flex-col">
                        <div className="flex flex-wrap pl-3 mt-10">
                            {/*TODO foreach de trades,*/}
                            {/*<c:forEach var="trade" items="${trades}">*/}
                            {/*    <fmt:formatNumber type="number" maxFractionDigits="0"*/}
                            {/*                      value="${trade.buyer.rating /2 == 0? 1: trade.buyer.rating/2 }" var="stars"/>*/}
                            {/*    <div name="trade-${offer.offerId}-${trade.status}"*/}
                            {/*         className="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mr-5 mb-5 w-80">*/}

                            {/*        <div className="flex font-sans h-fit w-full mt-5">*/}
                            {/*            <c:if test="${trade.status == 'PENDING' }">*/}
                            {/*                <div className="bg-nyellow  w-full text-white  text-center p-2"><messages:message*/}
                            {/*                    code="pending"/></div>*/}
                            {/*            </c:if>*/}

                            {/*            <c:if test="${trade.status == 'REJECTED' }">*/}
                            {/*                <div className="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message*/}
                            {/*                    code="rejected"/></div>*/}
                            {/*            </c:if>*/}

                            {/*            <c:if test="${trade.status == 'ACCEPTED' }">*/}
                            {/*                <div className="bg-ngreen w-full text-white text-center p-2"><messages:message*/}
                            {/*                    code="accepted"/></div>*/}
                            {/*            </c:if>*/}

                            {/*            <c:if test="${trade.status == 'SOLD' }">*/}
                            {/*                <div className="bg-gray-400 w-full text-white text-center p-2"><messages:message*/}
                            {/*                    code="sold"/></div>*/}
                            {/*            </c:if>*/}
                            {/*            <c:if test="${trade.status == 'DELETED' }">*/}
                            {/*                <div className="bg-blue-400 w-full text-polar text-center p-2"><messages:message*/}
                            {/*                    code="takenBack"/></div>*/}
                            {/*            </c:if>*/}
                            {/*        </div>*/}

                            {/*        <div className="flex flex font-sans my-3  w-56 mx-auto text-semibold">*/}
                            {/*            <h1 className="mx-auto">*/}
                            {/*                <fmt:formatNumber type="number" maxFractionDigits="6"*/}
                            {/*                                  value="${trade.quantity/offer.unitPrice}"/>*/}
                            {/*                <c:out value=" ${offer.crypto.code}"/> ⟶ <c:out*/}
                            {/*                value="${trade.quantity} "/>ARS</h1>*/}
                            {/*        </div>*/}

                            {/*        <div className="flex flex-col">*/}
                            {/*            <div className="flex">*/}
                            {/*                <h1 className="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>*/}
                            {/*                <h1 className="font-sans font-semibold"><c:out*/}
                            {/*                    value="${trade.buyer.username.get()}"/></h1>*/}
                            {/*            </div>*/}
                            {/*            <div className="flex">*/}
                            {/*                <h1 className="font-sans mr-2"><messages:message code="email"/>:</h1>*/}
                            {/*                <h1 className="font-sans font-semibold"><c:out value="${trade.buyer.email}"/></h1>*/}
                            {/*            </div>*/}
                            {/*            <div className="flex">*/}
                            {/*                <h1 className="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>*/}
                            {/*                <h1 className="font-sans font-semibold"><c:out*/}
                            {/*                    value="${trade.buyer.phoneNumber}"/></h1>*/}
                            {/*            </div>*/}
                            {/*            <c:if test="${trade.buyer.ratingCount > 0}">*/}
                            {/*                <div className="flex">*/}
                            {/*                    <div className="flex flex-row">*/}
                            {/*                        <h4 className="text-gray-400 font-sans"><messages:message code="rating"/>: </h4>*/}
                            {/*                        <div className="my-auto ml-2">*/}
                            {/*                            <c:forEach begin="0" end="${stars-1}">*/}
                            {/*                                <span className="fa fa-star" style="color: orange"></span>*/}
                            {/*                            </c:forEach>*/}
                            {/*                            <c:forEach begin="${stars}" end="4">*/}
                            {/*                                <span className="fa fa-star" style="color: gray"></span>*/}
                            {/*                            </c:forEach>*/}
                            {/*                        </div>*/}
                            {/*                    </div>*/}
                            {/*                </div>*/}
                            {/*            </c:if>*/}

                            {/*        </div>*/}
                            {/*        <c:if test="${(trade.status =='SOLD')}">*/}
                            {/*            <a className="mx-auto bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40 mt-5"*/}
                            {/*               href="<c:url value="/receiptDescription/${trade.tradeId}"/>">*/}
                            {/*                <messages:message code="help"/>*/}
                            {/*            </a>*/}
                            {/*            <div className="flex flex-row mx-auto">*/}

                            {/*                <a href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>"*/}
                            {/*                   className="mx-2 rounded-full my-auto">*/}
                            {/*                    <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>*/}
                            {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 mx-auto" fill="none"*/}
                            {/*                         viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">*/}
                            {/*                        <path stroke-linecap="round" stroke-linejoin="round"*/}
                            {/*                              d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>*/}
                            {/*                    </svg>*/}
                            {/*                </a>*/}
                            {/*                <c:if test="${trade.qUnseenMessagesSeller > 0}">*/}
                            {/*                    <div className="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">*/}
                            {/*                        <p className="text-xs"><c:out value="${trade.qUnseenMessagesSeller}"/></p>*/}
                            {/*                    </div>*/}
                            {/*                </c:if>*/}
                            {/*            </div>*/}
                            {/*        </c:if>*/}

                            {/*        <%--                            CASE - PENDING--%>*/}
                            {/*        <c:if test="${trade.status == 'PENDING'}">*/}
                            {/*            <div className="flex flex-row">*/}
                            {/*                <c:url value="/rejectOffer?tradeId=${trade.tradeId}" var="postUrl"/>*/}
                            {/*                <form:form action="${postUrl}" method="post"*/}
                            {/*                           cssClass="flex justify-center mx-auto my-3">*/}
                            {/*                    <button type="submit"*/}
                            {/*                            className="bg-red-400 text-white p-3 rounded-md font-sans mr-4">*/}
                            {/*                        <messages:message*/}
                            {/*                            code="rejectTrade"/></button>*/}
                            {/*                </form:form>*/}
                            {/*                <c:url value="/acceptOffer?tradeId=${trade.tradeId}" var="postUrl"/>*/}
                            {/*                <form:form action="${postUrl}" method="post"*/}
                            {/*                           cssClass="flex justify-center mx-auto my-3">*/}
                            {/*                    <button type="submit"*/}
                            {/*                            className="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message*/}
                            {/*                        code="acceptTrade"/></button>*/}
                            {/*                </form:form>*/}
                            {/*            </div>*/}
                            {/*            <div className="flex flex-row mx-auto">*/}

                            {/*                <a href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>"*/}
                            {/*                   className="mx-2 rounded-full my-auto">*/}
                            {/*                    <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>*/}
                            {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 mx-auto" fill="none"*/}
                            {/*                         viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">*/}
                            {/*                        <path stroke-linecap="round" stroke-linejoin="round"*/}
                            {/*                              d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>*/}
                            {/*                    </svg>*/}
                            {/*                </a>*/}
                            {/*                <c:if test="${trade.qUnseenMessagesSeller > 0}">*/}
                            {/*                    <div className="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">*/}
                            {/*                        <p className="text-xs"><c:out value="${trade.qUnseenMessagesSeller}"/></p>*/}
                            {/*                    </div>*/}
                            {/*                </c:if>*/}
                            {/*            </div>*/}


                            {/*        </c:if>*/}

                            {/*        <%--                            CASE - ACCEPTED--%>*/}
                            {/*        <c:if test="${trade.status == 'ACCEPTED' }">*/}
                            {/*            <c:url value="/markAsSold?tradeId=${trade.tradeId}" var="formUrl"/>*/}

                            {/*            <form:form action="${formUrl}" method="post"*/}
                            {/*                       cssClass="flex justify-center mx-auto my-3">*/}
                            {/*                <button type="submit"*/}
                            {/*                        className="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">*/}
                            {/*                    <messages:message code="soldTrade"/></button>*/}

                            {/*            </form:form>*/}

                            {/*            <div className="flex flex-row mx-auto ">*/}
                            {/*                <a href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>"*/}
                            {/*                   className="mx-2 rounded-full my-auto">*/}
                            {/*                    <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>*/}
                            {/*                    <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 mx-auto" fill="none"*/}
                            {/*                         viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">*/}
                            {/*                        <path stroke-linecap="round" stroke-linejoin="round"*/}
                            {/*                              d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>*/}
                            {/*                    </svg>*/}
                            {/*                </a>*/}
                            {/*                <c:if test="${trade.qUnseenMessagesSeller > 0}">*/}
                            {/*                    <div className=" flex flex-row w-2 h-2 bg-frostl font-sans rounded-full bg-frost">*/}
                            {/*                    </div>*/}
                            {/*                </c:if>*/}
                            {/*            </div>*/}
                            {/*        </c:if>*/}

                            {/*        <%--                            CASE - REJECTED--%>*/}
                            {/*        <c:if test="${!(trade.status =='ACCEPTED') && !(trade.status == 'PENDING')}">*/}
                            {/*            <div className="flex h-2/5 my-2"></div>*/}
                            {/*        </c:if>*/}
                            {/*    </div>*/}
                            {/*</c:forEach>*/}
                        </div>
                        <h1 className="mx-auto text-center">
                            <Paginator totalPages={10} actualPage={1} callback={() => console.log("a")}/>
                        </h1>
                    </div>
                </div>
            </div>
        </>
    );
};

export default SellerOfferDashboard;
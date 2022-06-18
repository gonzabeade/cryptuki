<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication property="name" var="username"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/sellerDashboard.js"/>"></script>
    <script src="<c:url value="/public/js/pagination.js"/> "></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>"/>
</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../../components/seller/sellerHeader.jsp"/>
<div class="flex flex-row h-full w-full px-20 my-10">

    <!-- Left Panel: chat and seller stats  -->
    <div class="flex flex-col h-3/5 w-1/5 pr-2">
        <div class="flex flex-col w-full py-3 rounded-lg px-5 pt-4 rounded-lg bg-[#FAFCFF]">
            <h1 class="font-sans w-full mx-auto text-center text-2xl font-bold"><messages:message
                    code="offersReceived"/></h1>
        </div>
        <c:set var="offer" value="${offer}"/>
        <c:if test="${offer.offerStatus == 'DEL'}">
            <c:url var="url" value="#"/>
        </c:if>
        <c:if test="${offer.offerStatus != 'DEL'}">
            <c:url var="url" value="/seller/associatedTrades/${offer.offerId}"/>
        </c:if>

        <a href="${url}" class="z-10 flex flex-col p-3 bg-[#FAFCFF] rounded-lg w-full my-5 mx-auto">
            <h1 class="text-center text-3xl font-semibold font-polard font-sans"><messages:message code="offer"/>#<c:out
                    value="${offer.offerId}"/></h1>

            <div class="flex flex-col mx-auto mt-5">
                <h1 class="font-bold text-lg text-center"><messages:message code="acceptedRange"/></h1>
                <p class="text-center"><fmt:formatNumber type="number" maxFractionDigits="6"
                                                         value="${offer.minInCrypto}"/> <c:out
                        value="${offer.crypto.code}"/> - <fmt:formatNumber type="number" maxFractionDigits="6"
                                                                           value="${offer.maxInCrypto}"/> <c:out
                        value="${offer.crypto.code}"/></p>
            </div>
            <div class="flex flex-col mx-auto">
                <h1 class="font-bold text-lg"><messages:message code="unitPrice"/></h1>
                <p class="text-center"><c:out value="${offer.unitPrice}"/>ARS</p>
            </div>
            <div class="flex flex-col mx-auto mb-10">
                <h1 class="font-bold text-lg"><messages:message code="location"/></h1>
                <p class="text-center"><messages:message code="Location.${offer.location}"/></p>
            </div>
            <c:if test="${offer.offerStatus == 'DEL'}">
                <div class="bg-nred m-1 p-2 text-white text-center"><messages:message code="offerDeleted"/></div>
            </c:if>
            <c:if test="${offer.offerStatus!= 'DEL'}">
                <div class="flex flex-row justify-between">
                    <c:url value="/offer/modify/${offer.offerId}" var="getUrl"/>
                    <form method="get" action="${getUrl}" class="rounded-xl text-center bg-frostdr w-1/2 p-2 mr-2 hover:bg-frostdr/[0.7]">
                        <button type="submit" class=" text-white"><messages:message code="edit"/></button>
                    </form>
                    <c:url value="/offer/delete/${offer.offerId}" var="postUrl"/>
                    <form method="post" action="${postUrl}" class="rounded-xl text-center bg-nred w-1/2 p-2 ml-2 z-30 ">
                        <button type="submit" class=" text-white"><messages:message code="delete"/></button>
                    </form>
                </div>
            </c:if>
            <form class="rounded-xl bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer shadow-lg" action="/pause/${offer.offerId}">
                <button type="submit" >
                    <messages:message code="pauseOffer"/>
                </button>
            </form>

        </a>


        <a href="/seller/" class="rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
            <messages:message code="goBack"/>
        </a>


    </div>
    <div class="flex flex-col w-4/5">

        <%--        Filters--%>

        <div class="flex flex-row  rounded-lg px-5 rounded-lg  justify-between">
            <div class="flex mr-5 bg-white rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-12">
                <a href="<c:url value="/seller/associatedTrades/${offer.offerId}"/>" class="my-auto mx-auto w-full ">
                    <p class=" py-2 px-4 font-bold text-polar text-center <c:out value="${empty status ?'decoration-frostdr underline underline-offset-8':'text-l '}" />">
                        <messages:message code="allTrades"/> </p>
                </a>
            </div>
            <div class="mr-5 bg-nyellow rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-12">
                <a href="<c:url value="/seller/associatedTrades/pending/${offer.offerId}"/>" class="my-auto mx-auto">
                    <p class="py-2 px-4 font-bold text-polar  text-center <c:out value="${status=='PENDING'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">
                        <messages:message code="pendingTrades"/></p>
                </a>
            </div>
            <div class="mr-5 bg-ngreen rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-12">
                <a href="<c:url value="/seller/associatedTrades/accepted/${offer.offerId}"/>" class="my-auto mx-auto">
                    <p class="py-2 px-4 font-bold text-polar  text-center <c:out value="${status=='ACCEPTED'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">
                        <messages:message code="acceptedTrades"/></p>
                </a>
            </div>
            <div class="mr-5 bg-nred rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-12">
                <a href="<c:url value="/seller/associatedTrades/rejected/${offer.offerId}"/>" class="my-auto mx-auto">
                    <p class="py-2 px-4 font-bold text-polar  text-center <c:out value="${status=='REJECTED'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">
                        <messages:message code="rejectedTrades"/></p>
                </a>
            </div>
            <div class="mr-5 bg-gray-200 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-12">
                <a href="<c:url value="/seller/associatedTrades/completed/${offer.offerId}"/>" class="my-auto mx-auto">
                    <p class="py-2 px-4 font-bold text-polar  text-center <c:out value="${status=='SOLD'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">
                        <messages:message code="soldTrades"/></p>
                </a>
            </div>
            <div class="mr-5 bg-blue-400 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-12" >
                <a href="<c:url value="/seller/associatedTrades/deletedByUser/${offer.offerId}"/>" class="my-auto mx-auto">
                    <p class="py-2  px-4 font-bold text-polar text-center <c:out value="${status=='DELETED'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">
                        <messages:message code="deletedByUserTrades"/></p>
                </a>
            </div>
        </div>
            <%--Middle Pannel --%>
        <div class="flex flex-col  w-5/6">
            <%--                    ELIJAN UNA CARD DISTINTA POR CADA CASO NO HAGAN CIFS!!!--%>
            <div class="flex flex-wrap pl-3">
                <c:forEach var="trade" items="${trades}">
                    <fmt:formatNumber type="number" maxFractionDigits="0"
                                      value="${trade.buyer.rating /2 == 0? 1: trade.buyer.rating/2 }" var="stars"/>
                    <div name="trade-${offer.offerId}-${trade.status}"
                         class="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between m-5 w-80">

                        <div class="flex font-sans h-fit w-full mt-5">
                            <c:if test="${trade.status == 'PENDING' }">
                                <div class="bg-nyellow  w-full text-white  text-center p-2"><messages:message
                                        code="pending"/></div>
                            </c:if>

                            <c:if test="${trade.status == 'REJECTED' }">
                                <div class="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message
                                        code="rejected"/></div>
                            </c:if>

                            <c:if test="${trade.status == 'ACCEPTED' }">
                                <div class="bg-ngreen w-full text-white text-center p-2"><messages:message
                                        code="accepted"/></div>
                            </c:if>

                            <c:if test="${trade.status == 'SOLD' }">
                                <div class="bg-gray-400 w-full text-white text-center p-2"><messages:message
                                        code="sold"/></div>
                            </c:if>
                        </div>

                        <div class="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                            <h1 class="mx-auto">
                                <fmt:formatNumber type="number" maxFractionDigits="6"
                                                  value="${trade.quantity/offer.unitPrice}"/>
                                <c:out value=" ${offer.crypto.code}"/> ⟶ <c:out
                                    value="${trade.quantity} "/>ARS</h1>
                        </div>

                        <div class="flex flex-col">
                            <div class="flex">
                                <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>
                                <h1 class="font-sans font-semibold"><c:out
                                        value="${trade.buyer.username.get()}"/></h1>
                            </div>
                            <div class="flex">
                                <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
                                <h1 class="font-sans font-semibold"><c:out value="${trade.buyer.email}"/></h1>
                            </div>
                            <div class="flex">
                                <h1 class="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>
                                <h1 class="font-sans font-semibold"><c:out
                                        value="${trade.buyer.phoneNumber}"/></h1>
                            </div>
                            <div class="flex">
                                <div class="flex flex-row">
                                    <h4 class="text-gray-400 font-sans"><messages:message code="rating"/>: </h4>
                                    <div class="my-auto ml-2">
                                        <c:forEach begin="0" end="${stars-1}">
                                            <span class="fa fa-star" style="color: orange"></span>
                                        </c:forEach>
                                        <c:forEach begin="${stars}" end="4">
                                            <span class="fa fa-star" style="color: gray"></span>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${(trade.status =='SOLD')}">
                            <a class="mx-auto bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40 mt-5"
                               href="<c:url value="/receiptDescription/${trade.tradeId}"/>">
                                <messages:message code="help"/>
                            </a>
                        </c:if>

                            <%--                            CASE - PENDING--%>
                        <c:if test="${trade.status == 'PENDING'}">
                            <div class="flex flex-row">
                                <c:url value="/rejectOffer?tradeId=${trade.tradeId}" var="postUrl"/>
                                <form:form action="${postUrl}" method="post"
                                           cssClass="flex justify-center mx-auto my-3">
                                    <button type="submit"
                                            class="bg-red-400 text-white p-3 rounded-md font-sans mr-4">
                                        <messages:message
                                                code="rejectTrade"/></button>
                                </form:form>
                                <c:url value="/acceptOffer?tradeId=${trade.tradeId}" var="postUrl"/>
                                <form:form action="${postUrl}" method="post"
                                           cssClass="flex justify-center mx-auto my-3">
                                    <button type="submit"
                                            class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message
                                            code="acceptTrade"/></button>
                                </form:form>
                            </div>
                            <div class="flex flex-row mx-auto">

                                <a href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>"
                                   class="mx-2 rounded-full my-auto">
                                        <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 mx-auto" fill="none"
                                         viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>
                                    </svg>
                                </a>
                                <c:if test="${trade.qUnseenMessagesSeller > 0}">
                                    <div class="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">
                                        <p class="text-xs"><c:out value="${trade.qUnseenMessagesSeller}"/></p>
                                    </div>
                                </c:if>
                            </div>


                        </c:if>

                            <%--                            CASE - ACCEPTED--%>
                        <c:if test="${trade.status == 'ACCEPTED' }">
                            <c:url value="/markAsSold?tradeId=${trade.tradeId}" var="formUrl"/>

                            <form:form action="${formUrl}" method="post"
                                       cssClass="flex justify-center mx-auto my-3">
                                <button type="submit"
                                        class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">
                                    <messages:message code="soldTrade"/></button>

                            </form:form>

                            <div class="flex flex-row mx-auto ">
                                <a href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>"
                                   class="mx-2 rounded-full my-auto">
                                        <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 mx-auto" fill="none"
                                         viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>
                                    </svg>
                                </a>
                                <c:if test="${trade.qUnseenMessagesSeller > 0}">
                                    <div class=" flex flex-row w-2 h-2 bg-frostl font-sans rounded-full bg-frost">
                                    </div>
                                </c:if>
                            </div>
                        </c:if>

                            <%--                            CASE - REJECTED--%>
                        <c:if test="${!(trade.status =='ACCEPTED') && !(trade.status == 'PENDING')}">
                            <div class="flex h-2/5 my-2"></div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>

        </div>

    </div>
</div>
</body>

<script>
    function updateStatus( status, tradeId) {
        document.getElementById('newStatus-'+tradeId).setAttribute('value',status)
    }
</script>
</html>


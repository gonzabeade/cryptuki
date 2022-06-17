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
                <h1 class="font-sans w-full mx-auto text-center text-2xl font-bold"><messages:message code="offersReceived"/> </h1>
            </div>
<%--            <div class="flex flex-col mt-4 rounded-lg px-5 py-4 rounded-lg bg-[#FAFCFF]">--%>
<%--                <label class="my-auto" for="offerId"><messages:message code="searchByOfferId"/> </label>--%>
<%--                <div class="flex flex-row">--%>
<%--                    <input id="offerId" placeholder="Id" type="number" class="border-2 border-gray-700 p-1 w-3/5 rounded-lg" onchange="addQueryParam('offerId')" path="filterByOfferId"/>--%>
<%--&lt;%&ndash;                        <a class="mx-auto" href="<c:url value="/seller/trade/${status.toString()}"/>"><img class='object-contain w-2 h-2 rounded-lg' src="<c:url value="/public/images/lupa.png"/>" alt="logo"></a>&ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>
                <c:set var="offer" value="${offer}"/>
            <c:if test="${offer.offerStatus == 'DEL'}">
                <c:url var="url" value="#"/>
            </c:if>
            <c:if test="${offer.offerStatus != 'DEL'}">
                <c:url  var="url" value="/seller/associatedTrades/${offer.offerId}"/>
            </c:if>

            <a  href="${url}" class="z-10 flex flex-col p-3 bg-[#FAFCFF] rounded-lg w-60 my-5 mx-auto">
                <h1 class="text-center text-3xl font-semibold font-polard font-sans"><messages:message code="offer"/>#<c:out value="${offer.offerId}"/></h1>

                <div class="flex flex-col mx-auto mt-5">
                    <h1 class="font-bold text-lg text-center"><messages:message code="acceptedRange"/></h1>
                    <p class="text-center"><fmt:formatNumber type="number" maxFractionDigits="6" value="${offer.minInCrypto}"/> <c:out value="${offer.crypto.code}"/> - <fmt:formatNumber type="number" maxFractionDigits="6" value="${offer.maxInCrypto}"/> <c:out value="${offer.crypto.code}"/></p>
                </div>
                <div class="flex flex-col mx-auto">
                    <h1 class="font-bold text-lg"><messages:message code="unitPrice"/></h1>
                    <p class="text-center"><c:out value="${offer.unitPrice}"/>ARS</p>
                </div>
                <div class="flex flex-col mx-auto mb-10">
                    <h1 class="font-bold text-lg"><messages:message code="location"/> </h1>
                    <p class="text-center"><c:out value="${offer.location}"/></p>
                </div>
                <c:if test="${offer.offerStatus == 'DEL'}">
                    <div class="bg-nred m-1 p-2 text-white text-center"><messages:message code="offerDeleted"/></div>
                </c:if>
                <c:if test="${offer.offerStatus!= 'DEL'}">
                    <div class="flex flex-row justify-between">
                        <c:url value="/offer/modify/${offer.offerId}" var="getUrl"/>
                        <form method="get" action="${getUrl}" class="rounded-xl text-center bg-frostdr w-1/2 p-2 mx-2">
                            <button type="submit" class=" text-white"><messages:message code="edit"/></button>
                        </form>
                        <c:url value="/offer/delete/${offer.offerId}" var="postUrl"/>
                        <form method="post" action="${postUrl}" class="rounded-xl text-center bg-storm w-1/2 p-2 mx-2 z-30">
                            <button type="submit" class=" "><messages:message code="delete"/></button>
                        </form>
                    </div>
                </c:if>

            </a>


        </div>

<%--        Filters--%>
            <div class="flex flex-col w-1/5 rounded-lg px-5 rounded-lg mx-auto">
                <div class="mb-2 bg-nyellow rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200">
                    <a href="<c:url value="/seller/associatedTrades/pending/${offer.offerId}"/>">
                        <p class="py-2 px-4 font-bold text-polar <c:out value="${status=='PENDING'?'decoration-frostdr underline underline-offset-8':'text-l '}" />"><messages:message code="pendingTrades"/> </p>
                    </a>
                </div>
                <div class="my-2 bg-ngreen rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200">
                    <a href="<c:url value="/seller/associatedTrades/accepted/${offer.offerId}"/>">
                        <p class="py-2 px-4 font-bold text-polar <c:out value="${status=='ACCEPTED'?'decoration-frostdr underline underline-offset-8':'text-l '}" />"><messages:message code="acceptedTrades"/> </p>
                    </a>
                </div>
                <div class="my-2 bg-nred rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200">
                    <a href="<c:url value="/seller/associatedTrades/rejected/${offer.offerId}"/>">
                        <p class="py-2 px-4 font-bold text-polar <c:out value="${status=='REJECTED'?'decoration-frostdr underline underline-offset-8':'text-l '}" />"><messages:message code="rejectedTrades"/></p>
                    </a>
                </div>
                <div class="my-2 bg-gray-200 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200">
                    <a href="<c:url value="/seller/associatedTrades/completed/${offer.offerId}"/>">
                        <p class="py-2 px-4 font-bold text-polar <c:out value="${status=='SOLD'?'decoration-frostdr underline underline-offset-8':'text-l '}" />"><messages:message code="soldTrades"/></p>
                    </a>
                </div>
                <div class="my-2 bg-blue-400 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200">
                    <a href="<c:url value="/seller/associatedTrades/deletedByUser/${offer.offerId}"/>">
                        <p class="py-2  px-4 font-bold text-polar <c:out value="${status=='DELETED'?'decoration-frostdr underline underline-offset-8':'text-l '}" />"><messages:message code="deletedByUserTrades"/> </p>
                    </a>
                </div>
            </div>


        <!-- Middle panel -->
        <div class="flex flex-col h-5/6 w-5/6">
                <%--                    ELIJAN UNA CARD DISTINTA POR CADA CASO NO HAGAN CIFS!!!--%>
                <div class="flex flex-wrap h-3/5 w-full pl-3">
                        <c:forEach var="trade" items="${trades}">
<%--                            <fmt:formatNumber var="quantityInCrypto" value="${trade.quantity / trade.offer.unitPrice}" maxFractionDigits="4" />--%>
<%--                            <jsp:include page="../../components/seller/tradeCards/plainTradeCard.jsp">--%>
<%--                                <jsp:param name="status" value="${status}"></jsp:param>--%>
<%--                                <jsp:param name="focusOfferId" value="${focusOffer.offerId}"></jsp:param>--%>
<%--                                <jsp:param name="quantityInCrypto" value="${quantityInCrypto}"></jsp:param>--%>
<%--                                <jsp:param name="crypto" value="${trade.offer.crypto.code}"></jsp:param>--%>
<%--                                <jsp:param name="quantityInArs" value="${trade.quantity}"></jsp:param>--%>
<%--                                <jsp:param name="buyerUsername" value="${trade.buyer.username.get()}"></jsp:param>--%>
<%--                                <jsp:param name="buyerUsername" value="${trade.buyer.username.get()}"></jsp:param>--%>
<%--                                <jsp:param name="buyerEmail" value="${trade.buyer.email}"></jsp:param>--%>
<%--                                <jsp:param name="buyerPhoneNumber" value="${trade.buyer.phoneNumber}"></jsp:param>--%>
<%--                                <jsp:param name="buyerRating" value="${trade.buyer.rating}"></jsp:param>--%>
<%--                                <jsp:param name="page" value="${page}"></jsp:param>--%>
<%--                                <jsp:param name="offerId" value="${trade.offer.offerId}"></jsp:param>--%>
<%--                                <jsp:param name="tradeId" value="${trade.tradeId}"></jsp:param>--%>
<%--                            </jsp:include>--%>
                            <div>
                                Trade component
                            </div>
                    </c:forEach>
                </div>

            </div>

    </body>
</html>


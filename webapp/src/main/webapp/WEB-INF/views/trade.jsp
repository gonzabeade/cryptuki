<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/header.jsp"/>
<div class="flex flex-row divide-x-2 divide-polard mt-10">

    <div class="flex flex-col w-3/5 mt-10">
            <c:if test="${status.equals('PENDING')}">
                <div class="flex">
                    <h2 class="bg-amber-300 mx-auto my-auto font-sans font-semibold text-polard text-4xl text-center"><messages:message code="ProposingPending"/></h2>
                    <c:if test="${buying}">
                        <form method="post" action="<c:url value="/deleteTrade/${tradeId}"/>">
                            <button class="bg-red-300 text-white  mt-4 mb-4 p-3 rounded-md font-sans mx-40" type="submit"><messages:message code="removeTrade"/></button>
                        </form>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${status.equals('REJECTED')}">
                <div class="flex">
                    <h2 class="bg-red-300 mx-auto my-auto font-sans font-semibold text-polard text-4xl text-center"><messages:message code="ProposingRejected"/></h2>
                    <c:if test="${buying}">
                        <form method="post" action="<c:url value="/deleteTrade/${tradeId}"/>">
                            <button class="bg-red-300 text-white  mt-4 mb-4 p-3 rounded-md font-sans mx-40" type="submit"><messages:message code="removeTrade"/></button>
                        </form>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${status.equals('ACCEPTED')}">
                <h2 class=" bg-ngreen mx-auto font-sans font-semibold text-polard text-4xl text-center"><messages:message code="ProposingAccepted"/></h2>
            </c:if>

            <div class="flex flex-col w-full mt-10">
                <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.commercialName}"/>" class="w-20 h-20 mx-auto">
                <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.commercialName}"/></h1>
                <h2 class="font-sans font-medium text-polard text-2xl text-center"><messages:message code="to"/> <c:out value="${offer.askingPrice}"/> ARS </h2>
                <div class="flex flex-row mt-3 font-sans justify-between">
                    <h2 class="font-sans mx-40"><b><messages:message code="minimum"/></b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.minQuantity}"/> ARS </h2>
                    <h2 class="font-sans mx-40"> <b><messages:message code="maximum"/></b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.maxQuantity}"/> ARS </h2>
                </div>
            </div>
            <div class="flex flex-row mt-10 mx-auto">
                <div class="flex flex-col mx-10">
                    <c:if test="${buying}">
                        <h1 class="text-center"><messages:message code="youPay"/></h1>
                    </c:if>
                    <c:if test="${!buying}">
                        <h1 class="text-center"><messages:message code="youReceive"/></h1>
                    </c:if>
                    <h1 class="text-center text-3xl font-semibold font-polard">${amount} ARS</h1>
                </div>
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 my-auto" fill="none" viewBox="0 0 24 24" stroke="black" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3" />
                </svg>
                <div class="flex flex-col mx-10">
                    <c:if test="${!buying}">
                        <h1 class="text-center"><messages:message code="youPay"/></h1>
                    </c:if>
                    <c:if test="${buying}">
                        <h1 class="text-center"><messages:message code="youReceive"/></h1>
                    </c:if>
                    <h1 class="text-center text-3xl font-semibold font-polard"><fmt:formatNumber type="number" maxFractionDigits="10" value="${amount / offer.askingPrice }"/> ${offer.crypto.code}</h1>
                </div>
            </div>
            <div class="flex w-full mt-10 justify-around">
                <c:url value="/changeStatus" var="postUrl"/>
                <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post">
                    <form:hidden  path="newStatus" value="${newStatus}"/>
                    <form:hidden  path="tradeId" value="${tradeId}"/>
                <c:if test="${!buying}">
                        <c:if test="${!status.equals('ACCEPTED')}">
                             <button onclick="updateStatus('REJECTED')" type="submit" class="bg-red-300 text-white  mt-4 mb-4 p-3 rounded-md font-sans mx-40 "><messages:message code="rejectTrade"/></button>
                            <button onclick="updateStatus('ACCEPTED')" type="submit" class="bg-ngreen text-white  mt-4 mb-4 p-3 rounded-md font-sans mx-40"><messages:message code="acceptTrade"/></button>
                        </c:if>
                        <c:if test="${status.equals('ACCEPTED')}">
                        <c:url value="/closeTrade" var="postUrl"/>
                        <form:form modelAttribute="soldTradeForm" action="${postUrl}" method="post">
                            <form:hidden  path="offerId" value="${offer.id}"/>
                            <form:hidden  path="trade" value="${tradeId}"/>
                            <button type="submit" class="bg-ngreen text-white  mt-4 mb-4 p-3 rounded-md font-sans mx-40"><messages:message code="soldTrade"/></button>
                        </form:form>
                        </c:if>
                </c:if>
                </form:form>

            </div>


        </div>

    <div class="flex w-2/5 ">
        <div class="ml-32">
            <c:if test="${buying}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../components/sellerInfo.jsp">
                    <jsp:param name="email" value="${offer.seller.email}"/>
                    <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
                    <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                    <jsp:param name="lastLogin" value="${otherLastLogin.relativeTime}"/>
                    <jsp:param name="rating" value="${offer.seller.rating}"/>
                    <jsp:param name="message" value="${offer.comments}"/>
                </jsp:include>
            </c:if>
            <c:if test="${!buying}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../components/buyerInfo.jsp">
                    <jsp:param name="email" value="${trade.user.email}"/>
                    <jsp:param name="phone" value="${trade.user.phoneNumber}"/>
                    <jsp:param name="trades" value="${trade.user.ratingCount}"/>
                    <jsp:param name="lastLogin" value="${otherLastLogin.relativeTime}"/>
                    <jsp:param name="rating" value="${trade.user.rating}"/>
                </jsp:include>
            </c:if>
        </div>
    </div>
</div>
</body>
<script>
    function updateStatus(newStatusName) {
        document.getElementById('newStatus').setAttribute('value',newStatusName)
    }
</script>
</html>


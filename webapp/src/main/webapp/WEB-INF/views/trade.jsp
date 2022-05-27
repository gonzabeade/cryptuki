<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

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
    <div class="flex flex-col w-3/5">
        <div class="flex flex-col mx-auto mt-10">
                <div>
                    <c:if test="${status.equals('PENDING')}">
                        <div class="bg-amber-300">
                            <h2 class="font-sans font-semibold text-polard text-2xl text-center"><messages:message code="ProposingPending"/></h2>
                        </div>
                    </c:if>
                    <c:if test="${status.equals('REJECTED')}">
                        <div class="bg-red-300">
                            <h2 class="font-sans font-semibold text-polard text-2xl text-center"><messages:message code="ProposingRejected"/></h2>
                        </div>
                    </c:if>
                    <c:if test="${status.equals('ACCEPTED')}">
                        <div class="bg-ngreen">
                            <h2 class="font-sans font-semibold text-polard text-2xl text-center"><messages:message code="ProposingAccepted"/></h2>
                        </div>
                    </c:if>
                    <c:if test="${status.equals('SOLD')}">
                        <div class="bg-amber-300">
                            <h2 class="font-sans font-semibold text-polard text-2xl text-center"><messages:message code="ProposingDone"/></h2>
                        </div>
                    </c:if>

                </div>
            <div class="flex flex-col mt-10">
                <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.commercialName}"/>" class="w-20 h-20 mx-auto">
                <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.commercialName}"/></h1>
                <h2 class="font-sans font-medium text-polard text-2xl text-center"><messages:message code="to"/> <c:out value="${offer.askingPrice}"/> ARS </h2>
                <div class="flex flex-row mt-3 font-sans justify-between">
                    <h2 class="font-sans mx-2"><b><messages:message code="minimum"/></b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.minQuantity}"/> ARS </h2>
                    <h2 class="font-sans"> <b><messages:message code="maximum"/></b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.maxQuantity}"/> ARS </h2>
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
            <div class="mx-auto mt-10">
                <c:url value="/changeStatus" var="postUrl"/>
                <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post">
                    <form:hidden  path="newStatus" value="${newStatus}"/>
                    <form:hidden  path="tradeId" value="${tradeId}"/>

                <c:if test="${buying}">
                    <button onclick="updateStatus('CANCELED')" type="submit" class="bg-red-300 text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto"><messages:message code="removeTrade"/></button>
                </c:if>
                <c:if test="${!buying}">
                    <div class="flex justify-between">
                        <button onclick="updateStatus('REJECTED')" type="submit" class="bg-red-300 text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto"><messages:message code="rejectTrade"/></button>
                        <button onclick="updateStatus('ACCEPTED')" type="submit" class="bg-ngreen text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto"><messages:message code="acceptTrade"/></button>
                    </div>
                </c:if>
                </form:form>
                </div>


        </div>
    </div>

    <% request.setCharacterEncoding("UTF-8"); %>
    <div class="flex flex-row w-2/5">
        <jsp:include page="../components/sellerInfo.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
            <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
            <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
            <jsp:param name="lastLogin" value="${sellerLastLogin.relativeTime}"/>
            <jsp:param name="message" value="${offer.comments}"/>
            <jsp:param name="rating" value="${offer.seller.rating}"/>
        </jsp:include>
    </div>

</div>
</body>
<script>
    function updateStatus(newStatusName) {
        document.getElementById('newStatus').setAttribute('value',newStatusName)
    }
</script>
</html>


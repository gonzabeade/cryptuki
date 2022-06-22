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
<jsp:include page="../components/buyer/buyerHeader.jsp"/>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5">
        <div class="mx-auto">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-60 w-60 ml-14" fill="none" viewBox="0 0 24 24" stroke="#A3BE8C" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
            </svg>
            <h1 class="text-ngreen text-4xl text-center"><messages:message code="done"/>!</h1>
        </div>
        <div class="mx-auto  text-polard text-2xl mt-10">
            <messages:message code="sellerWillTransfer"/> <b><fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity/offer.unitPrice}"/> <c:out value="${offer.crypto.code}"/></b> <messages:message code="toYourWallet"/>.
        </div>
        <div class="mt-10 mx-auto flex flex-col">
            <h1 class="text-polard font-extrabold text-2xl font-sans"><messages:message code="offerInformation"/>:</h1>
            <div class="mx-auto">
                <h1 class="text-polard font-bold font-sans text-center text-lg"><messages:message code="cryptocurrency"/></h1>
                <p class="text-polard font-sans text-center"><c:out value="${offer.crypto.commercialName}"></c:out></p>
            </div>
            <div class="mx-auto">
                <h1 class="text-polard font-bold font-sans text-center text-lg"><messages:message code="absolutePriceByCurrency"/></h1>
                <p class="text-polard font-sans text-center"><c:out value="${offer.unitPrice}"/> ARS</p>
            </div>
            <div class="mx-auto">
                <h1 class="text-polard font-bold font-sans text-center text-lg"><messages:message code="trasactionDate"/></h1>
                <p class="text-polard font-sans text-center"><c:out value="${trade.startDate.get()}"/></p>
            </div>
        </div>
        <div class="flex flex-row mt-10">
            <a class="bg-nred text-white p-3 font-sans rounded-lg mx-auto" href="<c:url value="/complain?tradeId=${trade.tradeId}"/> "><messages:message code="iHadAProblema"/></a>
            <a class="bg-frost text-white p-3 font-sans rounded-lg mx-auto" href="<c:url  value="/"/>"><messages:message code="returnHome"/></a>
        </div>


    </div>
    <% request.setCharacterEncoding("UTF-8"); %>
    <div class="flex flex-row w-2/5">
        <div class="flex flex-col mx-auto">
            <jsp:include page="../components/sellerInfo.jsp">
                <jsp:param name="email" value="${offer.seller.email}"/>
                <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
                <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                <jsp:param name="lastLogin" value="${sellerLastLogin.relativeTime}"/>
                <jsp:param name="message" value="${offer.comments}"/>
                <jsp:param name="rating" value="${offer.seller.rating}"/>
            </jsp:include>
            <% request.setCharacterEncoding("UTF-8"); %>
            <jsp:include page="../components/buyerInfo.jsp">
                <jsp:param name="email" value="${user.email}"/>
                <jsp:param name="trades" value="${user.ratingCount}"/>
                <jsp:param name="phone" value="${user.phoneNumber}"/>
                <jsp:param name="lastLogin" value="${buyerLastLogin.relativeTime}"/>
                <jsp:param name="rating" value="${user.rating}"/>
            </jsp:include>
        </div>

    </div>
</div>
</body>
</html>



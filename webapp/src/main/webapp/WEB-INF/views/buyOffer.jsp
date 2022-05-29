<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/buyer/buyerHeader.jsp"/>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5">
        <div class="flex">
            <div class="flex flex-col mx-auto mt-10">
                <h2 class="font-sans font-semibold text-polard text-2xl text-center"><messages:message code="aboutToBuy"/></h2>
                <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.commercialName}"/>" class="w-20 h-20 mx-auto">
                <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.commercialName}"/></h1>
                <h2 class="font-sans font-medium text-polard text-2xl text-center"><messages:message code="to"/> <c:out value="${offer.askingPrice}"/> ARS </h2>
                <div class="flex flex-row mt-3 font-sans ">
                    <h2 class="font-sans mx-2"><b><messages:message code="minimum"/>:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.minQuantity}"/> ARS </h2>
                    <h2 class="font-sans"> <b><messages:message code="maximum"/>:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.maxQuantity}"/> ARS </h2>
                </div>
                <c:choose>
                    <c:when test="${offer.location == null}">
                        <h2 class="pt-2 font-sans text-center"><b><messages:message code="location"/>:</b> <messages:message code="unknown"/></h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="pt-2 font-sans text-center"><b><messages:message code="location"/>:</b> <c:out value="${offer.location}"/></h2>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div>
            <% request.setCharacterEncoding("UTF-8"); %>
            <jsp:include page="../components/buyForm.jsp">
                <jsp:param name="offer_id" value="${offer.id}"/>
                <jsp:param name="coin" value="${offer.crypto.code}"/>
                <jsp:param name="price" value="${offer.askingPrice}"/>
                <jsp:param name="minCoinAmount" value="${offer.minQuantity}"/>
                <jsp:param name="maxCoinAmount" value="${offer.maxQuantity}"/>
                <jsp:param name="userEmail" value="${userEmail}"/>
            </jsp:include>
        </div>
    </div>
    <% request.setCharacterEncoding("utf-8"); %>
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
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>

</div>


</body>
</html>

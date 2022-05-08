<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="modifiedOffer"><messages:message code="modifiedOffer"/></c:set>
<c:set var="createdOffer"><messages:message code="createdOffer"/></c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/feedback.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<c:if test="${creation == true}">
    <% request.setCharacterEncoding("UTF-8"); %>
    <jsp:include page="../components/confirmationToggle.jsp">
        <jsp:param name="title" value="${createdOffer}"/>
    </jsp:include>
</c:if>
<c:if test="${edit == true}">
    <% request.setCharacterEncoding("UTF-8"); %>
    <jsp:include page="../components/confirmationToggle.jsp">
        <jsp:param name="title" value="${modifiedOffer}"/>
    </jsp:include>
</c:if>
<div class="flex flex-col mt-10">
    <div class="flex flex-col">
        <div class="flex">
            <div class="flex flex-col mx-auto mt-10">
                <div class="flex flex-row justify-center">
                    <h2 class="font-sans font-semibold text-polard text-4xl text-center my-auto"><messages:message code="offer"/> # <c:out value="${offer.id}"/></h2>
                    <c:if test="${isAdmin || offer.seller.email == userEmail}">
                        <div class="flex flex-row mx-auto my-4">
                            <a class="active:cursor-progress my-auto mx-3" href="<c:url value="/modify/${offer.id}"/>">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="#2E3440" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                                </svg>
                            </a>
                            <div class="my-auto">
                                <c:url value="/delete/${offer.id}" var="deleteUrl"/>
                                <form:form method="post" action="${deleteUrl}" cssClass="flex my-auto mx-3">
                                    <button type="submit">
                                        <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="#2E3440" stroke-width="2">
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                        </svg>
                                    </button>
                                </form:form>
                            </div>
                        </div>
                    </c:if>

                </div>
               <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.commercialName}"/>" class="w-20 h-20 mx-auto mt-4">
                <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.commercialName}"/></h1>
                <h2 class="font-sans font-medium text-polard text-2xl text-center"><messages:message code="to"/>  <c:out value="${offer.askingPrice}"/> ARS </h2>
                <div class="flex flex-row mt-3 font-sans ">
                    <h2 class="font-sans mx-2"><b><messages:message code="minimum"/>:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.minQuantity}"/> ARS </h2>
                    <h2 class="font-sans"> <b><messages:message code="maximum"/>:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.maxQuantity}"/> ARS </h2>
                </div>
            </div>
        </div>

    </div>
    <div class="flex flex-row mt-10">
        <jsp:include page="../components/seller_info.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
            <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
            <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
            <jsp:param name="lastLogin" value="${sellerLastLogin.relativeTime}"/>
            <jsp:param name="message" value="${offer.comments}"/>
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
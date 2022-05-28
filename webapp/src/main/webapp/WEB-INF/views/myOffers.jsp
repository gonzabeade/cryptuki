<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
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
<div class="flex flex-col">
    <div class="flex justify-between mt-10 mb-5">
        <div class="flex mt-10 mb-5 ml-20">
            <c:if test="${!noOffers}">
                <h2 class="text-center text-3xl font-semibold font-sans text-polar my-auto"><messages:message code="yourOffers"/></h2>
            </c:if>
        </div>
    </div>
    <div  class="flex flex-col justify-center">
        <c:forEach var="offer" items="${offerList}">
            <li class="list-none mx-20">
                <% request.setCharacterEncoding("utf-8"); %>
                <c:set  var="accepted_payments" value="${offer.paymentMethods}" scope="request"/>
                <c:set  var="owner" value="${offer.seller.username.isPresent() ? offer.seller.username.get() : offer.seller.email}" scope="request"/>

                <jsp:include page="../components/card.jsp">
                    <jsp:param name="currency" value="${offer.crypto.code}"/>
                    <jsp:param name="owner" value="${owner}"/>
                    <jsp:param name="asking_price" value="${offer.askingPrice}"/>
                    <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                    <jsp:param name="offerId" value="${offer.id}"/>
                    <jsp:param name="minCoinAmount" value="${offer.minQuantity}"/>
                    <jsp:param name="maxCoinAmount" value="${offer.maxQuantity}"/>
                    <jsp:param name="userEmail" value="${userEmail}"/>
                    <jsp:param name="lastLogin" value="${offer.seller.lastLogin.toLocalDate()}"/>
                    <jsp:param name="lastLoginTime" value="${offer.seller.lastLogin.toLocalTime().hour}:${offer.seller.lastLogin.toLocalTime().minute}"/>
                    <jsp:param name="minutesSinceLastLogin" value="${offer.seller.minutesSinceLastLogin}"/>
                    <jsp:param name="rating" value="${offer.seller.rating}"/>

                </jsp:include>
            </li>
        </c:forEach>
    </div>

    <c:if test="${noOffers}">
        <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noOffersUploaded"/></h2>
        <a href="<c:url value="/"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message code="startSelling"/></a>
    </c:if>

    <c:if test="${!noOffers}">
        <div class="flex flex-col">
            <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../components/paginator.jsp">
                <jsp:param name="activePage" value="${activePage}"/>
                <jsp:param name="pages" value="${pages}"/>
                <jsp:param name="baseUrl" value="/myoffers"/>
            </jsp:include>
            <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: ${pages}</h1>
        </div>
    </c:if>
    <div class="shape-blob"></div>
    <div class="shape-blob one"></div>
    <div class="shape-blob two"></div>
    <div class="shape-blob" style="left: 50%"></div>
    <div class="shape-blob" style="left: 5%; top: 80%"></div>
</div>
</body>
</html>



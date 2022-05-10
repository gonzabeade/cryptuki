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
    <script src="<c:url value="/public/js/feedback.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5 h-screen">
        <div class=" mx-10 flex flex-col  p-5">
            <div class="mb-5 mt-5">
                <c:if test="${rated == true }">
                    <c:set var="ratingSent"><messages:message code="ratingSent"/></c:set>
                    <jsp:include page="../components/confirmationToggle.jsp">
                        <jsp:param name="title" value="${ratingSent}"/>
                    </jsp:include>
                </c:if>
            </div>
            <h1 class=" mt-10text-polard font-extrabold text-4xl font-sans mx-5 text-center "><messages:message code="transactionInformation"/>:</h1>
            <div class="flex flex-col mt-10">
                <div class="mx-auto">
                    <h1 class="text-polard font-sans text-center text-3xl"><messages:message code="youBought"/>:</h1>
                </div>
                <div class="flex mx-auto">
                    <div class="mr-3">
                        <h1 class="text-3xl text-polard font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity/offer.askingPrice}"/></h1>
                    </div>
                    <div class="mr-3">
                        <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.code}"/>" class="w-8 h-8"/>
                    </div>
                    <div>
                        <h1 class="ml-2 text-3xl text-polard font-sans font-semibold"><c:out value="${offer.crypto.code}"/></h1>
                    </div>
                </div>
            </div>
            <div class="flex flex-col mt-5">
                <div class="mx-auto">
                    <h1 class="text-polard font-sans text-center text-3xl"><messages:message code="inExchangeOf"/>:</h1>
                </div>
                <div>
                    <h1 class="text-polard font-sans font-bold text-center text-3xl">${trade.quantity}$ARS</h1>
                </div>
            </div>
            <div class="flex flex-col mt-5">
                <div class="mx-auto">
                    <h1 class="text-polard  font-sans text-center text-3xl"><messages:message code="trasactionDate"/>:</h1>
                </div>
                <div>
                    <h1 class="text-polard font-sans  font-bold text-center text-3xl mx-auto">${trade.startDate.get().toString()}</h1>
                </div>
            </div>
        </div>
        <div class="flex flex-row mt-10">
            <a class="bg-frost text-white p-3 font-sans rounded-lg mx-auto  w-40 text-center" href="<c:url  value="/user"/>"><messages:message code="goBack"/></a>
            <a class="bg-nred text-white p-3 font-sans rounded-lg mx-auto w-40 text-center" href="<c:url value="/complain?tradeId=${trade.tradeId}"/> "><messages:message code="iHadAProblema"/></a>
        </div>
        <div class="flex flex-col mx-auto mt-10">

            <c:if test="${(trade.buyerUsername == username && trade.ratedBuyer == false) || (trade.sellerUsername == username && trade.ratedSeller == false)}">
                <h1 class="text-polard font-sans  font-bold text-center text-3xl mx-auto"><messages:message code="rate"/> ${trade.sellerUsername == username ? trade.buyerUsername: trade.sellerUsername}</h1>
                <c:url value="/rate" var="postUrl"/>
                <form:form modelAttribute="ratingForm" action="${postUrl}" method="post" >

                    <form:hidden path="tradeId" value="${trade.tradeId}"/>
                    <div class="flex flex-col">
                        <form:errors path="rating" cssClass="mx-auto text-red-400"/>
                        <form:label path="rating" cssClass="mx-auto"><messages:message code="ratingConditions"/></form:label>
                        <form:input path="rating"  type="number" cssClass="p-3 w-16 rounded-lg mx-auto mt-5 none"/>
                        <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress"><messages:message code="send"/> </button>
                    </div>

                </form:form>
            </c:if>
        </div>


    </div>
    <div class="flex flex-row w-2/5">
        <div class="flex flex-col ml-32">

            <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../components/seller_info.jsp">
                <jsp:param name="email" value="${offer.seller.email}"/>
                <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
                <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                <jsp:param name="lastLogin" value="${sellerLastLogin.relativeTime}"/>
            </jsp:include>
            <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../components/buyer_info.jsp">
                <jsp:param name="email" value="${user.email}"/>
                <jsp:param name="trades" value="${user.ratingCount}"/>
                <jsp:param name="phone" value="${user.phoneNumber}"/>
                <jsp:param name="lastLogin" value="${buyerLastLogin.relativeTime}"/>
            </jsp:include>
        </div>

    </div>
</div>
</body>
</html>




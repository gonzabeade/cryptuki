<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="pages" scope="request" type="java.lang.Long"/>
<jsp:useBean id="offerList" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="activePage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.lang.Iterable"/>
<%--<jsp:useBean id="paymentMethods" scope="request" type="java.lang.Iterable"/>--%>
<html>
<head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script src="https://cdn.tailwindcss.com"></script>
            <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
            <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
            <script src="<c:url value="/public/js/pagination.js"/>" ></script>
            <script src="<c:url value="/public/js/apiCryptoPrices.js"/>"></script>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
            <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
            <link rel="stylesheet" href="<c:url value="/public/css/slider.css"/>">
            <title>cryptuki</title>
            <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>
<body class="bg-storml overflow-x-hidden">

<% request.setCharacterEncoding("UTF-8"); %>

<sec:authorize access="isAuthenticated()">
    <jsp:include page="../components/buyer/buyerHeader.jsp"/>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <jsp:include page="../components/anon/anonymousHeader.jsp"/>
</sec:authorize>
<c:url var="getUrl" value="/buyer/market"/>
<form:form  modelAttribute="landingForm" cssClass="flex flex-row divide-x" method="get" action="${getUrl}" >
    <form:hidden path="page" value="${activePage}"/>
    <div class="flex flex-col w-1/5 ">
    <c:set var="cryptocurrencies" value="${cryptocurrencies}" scope="request"/>
    <c:set var="locations" value="${locations}" scope="request"/>
    <c:set var="selectedCoins" value="${selectedCoins}" scope="request"/>
     <h1 class="text-2xl font-polard mt-10 font-bold mx-auto">Filtrar</h1>
    <jsp:include page="../components/cryptoFilters.jsp"/>
    <jsp:include page="../components/ubicationFilter.jsp"/>
    <jsp:include page="../components/priceFilter.jsp"/>
    <div class="flex">
            <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" id="link">Aplicar Filtros</button>
    </div>
    </div>

    <div class="flex flex-col w-4/5 mt-10">
        <div class="flex flex-row justify-between">
            <div class="flex flex-row mx-5">
                <p class="my-auto font-sans font-bold"><messages:message code="orderBy"/></p>
                <form:select path="orderCriteria" class="ml-5 rounded-sm p-2 my-auto " onchange="sendGet()">
                    <form:option value="2"><messages:message code="priceLowToHigh"/></form:option>
                    <form:option value="0"><messages:message code="dateOrder"/> </form:option>
                    <form:option value="1"><messages:message code="ratingFilter"/></form:option>
                    <form:option value="3"><messages:message code="priceHighToLow"/></form:option>
                </form:select>
            </div>
            <h1 class="text-right text-gray-400 mx-5"><messages:message code="youGot"/> ${offerCount} <messages:message code="results"/></h1>
        </div>

        <ol class="min-w-full">
        <div>
            <c:forEach var="offer" items="${offerList}">
                <li>
<%--                    <c:set  var="accepted_payments" value="${offer.paymentMethods}" scope="request"/>--%>
                    <c:set  var="owner" value="${offer.seller.username.isPresent() ? offer.seller.username.get() : offer.seller.email}" scope="request"/>
                    <% request.setCharacterEncoding("UTF-8"); %>
                    <jsp:include page="../components/card.jsp">
                        <jsp:param name="currency" value="${offer.crypto.code}"/>
                        <jsp:param name="owner" value="${owner}"/>
                        <jsp:param name="asking_price" value="${offer.unitPrice}"/>
                        <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                        <jsp:param name="offerId" value="${offer.offerId}"/>
                        <jsp:param name="minCoinAmount" value="${offer.minInCrypto}"/>
                        <jsp:param name="maxCoinAmount" value="${offer.maxInCrypto}"/>
                        <jsp:param name="userEmail" value="${userEmail}"/>
                        <jsp:param name="lastLogin" value="${offer.seller.lastLogin.toLocalDate()}"/>
                        <jsp:param name="lastLoginTime" value="${offer.seller.lastLogin.toLocalTime().toString()}"/>
                        <jsp:param name="minutesSinceLastLogin" value="${offer.seller.minutesSinceLastLogin}"/>
                        <jsp:param name="rating" value="${offer.seller.rating}"/>
                        <jsp:param name="location" value="${offer.location}"/>
                    </jsp:include>
                </li>
            </c:forEach>
            <div class="flex flex-col">
                <% request.setCharacterEncoding("UTF-8"); %>
                <jsp:include page="../components/paginator.jsp">
                    <jsp:param name="activePage" value="${activePage}"/>
                    <jsp:param name="pages" value="${pages}"/>
                    <jsp:param name="baseUrl" value="/buyer/market"/>
                </jsp:include>
                <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: ${pages}</h1>
            </div>
        </div>
    </ol>
    </div>
        </form:form>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>

<script>
window.onload = async function onStart() {
        await setCryptoPrice();
        analyze(${selectedCoins});
}
function analyze(selectedCoins, coins){
   if(selectedCoins !=  null){
       selectedCoins.forEach((coin)=>{
           coin[1].click()
       })
   }else{

   }
}
</script>


</body>
</html>
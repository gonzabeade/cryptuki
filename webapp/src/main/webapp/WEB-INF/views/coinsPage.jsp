<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/apiCryptoPrices.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
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
<h1 class="text-center text-4xl font-semibold font-sans text-polar mt-20 mb-20"><messages:message code="getToKnowMainCryptocurrencies"/></h1>
<div class="mx-48">
    <ol class="min-w-50%">
        <div class="flex justify-center flex-wrap">
            <% request.setCharacterEncoding("UTF-8"); %>
            <c:forEach var="coin" items="${coinList}">
                <li class="m-10">
                    <jsp:include page="../components/cryptoCard.jsp">
                        <jsp:param name="code" value="${coin.code}"/>
                        <jsp:param name="commercialName" value="${coin.commercialName}"/>
                    </jsp:include>
                </li>
            </c:forEach>
        </div>
    </ol>
</div>

<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
<script>
    window.onload =  async function wrapper(){
        await setCryptoPrice();
    }
</script>
</html>
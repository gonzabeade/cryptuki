<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/formValidations.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/header.jsp"/>
<div class="flex flex-col mt-5 mb-10">

    <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="myTransactions"/>: </h2>
    <div  class="flex flex-col justify-center mx-auto mt-10">
        <c:forEach var="trade" items="${tradeList}">
            <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../components/tradeCard.jsp">
                <jsp:param name="username" value="${username}"/>
                <jsp:param name="sellerUsername" value="${trade.sellerUsername}"/>
                <jsp:param name="buyerUsername" value="${trade.buyerUsername}"/>
                <jsp:param name="quantity" value="${trade.quantity}"/>
                <jsp:param name="cryptoCurrencyCode" value="${trade.cryptoCurrency.code}"/>
                <jsp:param name="askedPrice" value="${trade.askedPrice}"/>
                <jsp:param name="tradeId" value="${trade.tradeId}"/>
                <jsp:param name="tradeStatus" value="${trade.status.toString()}"/>

            </jsp:include>
        </c:forEach>
    </div>


    <div class="flex flex-col mt-3">
        <% request.setCharacterEncoding("utf-8"); %>
        <jsp:include page="../components/paginator.jsp">
            <jsp:param name="activePage" value="${activePage}"/>
            <jsp:param name="pages" value="${pages}"/>
            <jsp:param name="baseUrl" value="/user"/>
        </jsp:include>
        <h1 class="mx-auto text-gray-400 mx-auto mt-3"><messages:message code="totalPageAmount"/>: ${pages}</h1>
    </div>


</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>
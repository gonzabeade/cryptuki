<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
</head>
<body class="bg-storml">
<jsp:include page="../components/header.jsp"/>
<div class=" flex justify-center mx-10">
    <jsp:include page="../components/welcome_message.jsp"/>
</div>
<div class="flex justify-center mx-60">
    <ol class="min-w-full">
        <div>
            <c:forEach var="offer" items="${offerList}">
                <li>
                    <c:set  var="accepted_payments" value="${offer.getPaymentMethods()}" scope="request"/>
                    <jsp:include page="../components/card.jsp">
                        <jsp:param name="currency" value="${offer.coin_id}"/>
                        <jsp:param name="user" value="${offer.seller.email}"/>
                        <jsp:param name="asking_price" value="${offer.askingPrice}"/>
                        <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                        <jsp:param name="offerId" value="${offer.id}"/>
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
</html>
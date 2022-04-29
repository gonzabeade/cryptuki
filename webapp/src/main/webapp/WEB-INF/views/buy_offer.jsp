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
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
    <div class="flex">
        <div class="flex flex-col mx-auto mt-10">
            <h2 class="font-sans font-semibold text-polard text-2xl text-center">Estás por comprar</h2>
            <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.name}"/>" class="w-20 h-20 mx-auto">
            <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.name}"/></h1>
            <h2 class="font-sans font-medium text-polard text-2xl text-center">a  <c:out value="${offer.askingPrice}"/> ARS </h2>

        </div>
    </div>
    <div>
        <jsp:include page="../components/buy_form.jsp">
            <jsp:param name="offer_id" value="${offer.id}"/>
            <jsp:param name="price" value="${offer.askingPrice}"/>
            <jsp:param name="coin" value="${offer.crypto.code}"/>
        </jsp:include>
        <jsp:include page="../components/seller_info.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
        </jsp:include>
    </div>

</body>
</html>

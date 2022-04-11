<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>P2P Crypto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">

</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp"/>
    <div class="flex">
        <div class="flex flex-col mx-auto mt-10">
            <h2 class="font-sans font-semibold text-polard text-2xl text-center">Estás por comprar</h2>
            <h1 class="text-center text-4xl font-bold">${offer.askingPrice} ARS / ${offer.crypto.name}</h1>
        </div>
    </div>
    <div>
        <jsp:include page="../components/buy_form.jsp">
            <jsp:param name="offer_id" value="${offer.id}"/>
        </jsp:include>
        <jsp:include page="../components/seller_info.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
        </jsp:include>
    </div>

</body>
</html>

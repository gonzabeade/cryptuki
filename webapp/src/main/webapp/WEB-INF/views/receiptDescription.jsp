<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5 h-screen">
        <div class="mt-10 mx-10 flex flex-col bg-stormd/[0.9] border-2 border-polard rounded-lg p-5">
            <h1 class="text-polard font-extrabold text-4xl font-sans mx-5 text-center ">Datos de la transacción:</h1>
            <div class="flex">
                <div class="mx-5">
                    <h1 class="text-polard font-bold font-sans text-center text-3xl">Compraste:</h1>
                </div>
                <div class="flex">
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
            <div class="flex">
                <div class="mx-5">
                    <h1 class="text-polard font-bold font-sans text-center text-3xl">A cambio de:</h1>
                </div>
                <div>
                    <h1 class="text-polard font-sans text-center text-3xl">${trade.quantity}$ARS</h1>
                </div>
            </div>
            <div class="flex">
                <div class="mx-5">
                    <h1 class="text-polard font-bold font-sans text-center text-3xl">Fecha de la transaccion:</h1>
                </div>
                <div>
                    <h1 class="text-polard font-sans text-center text-3xl">${trade.startDate.get().toString()}</h1>
                </div>
            </div>
        </div>
        <div class="flex flex-row mt-10">
            <a class="bg-nred text-white p-3 font-sans rounded-lg mx-auto" href="<c:url value="/contact?tradeId=${trade.tradeId}"/> ">Tuve un problema</a>
            <a class="bg-frost text-white p-3 font-sans rounded-lg mx-auto" href="<c:url  value="/user"/>">Volver</a>
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




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
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5">
        <div class="flex flex-col">
            <div class="flex flex-col mx-auto mt-10">
                <h2 class="font-sans font-semibold text-polard text-2xl text-center">Estás comprando</h2>
                <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.commercialName}"/>" class="w-20 h-20 mx-auto">
                <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.commercialName}"/></h1>
                <h2 class="font-sans font-medium text-polard text-2xl text-center">a  <c:out value="${offer.askingPrice}"/> ARS </h2>
                <div class="flex flex-row mt-3 font-sans ">
                    <h2 class="font-sans mx-2"><b>Mínimo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.minQuantity}"/> ARS </h2>
                    <h2 class="font-sans"> <b>Máximo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.maxQuantity}"/> ARS </h2>
                </div>
            </div>
            <div class="flex flex-row mt-10 mx-auto">
                <div class="flex flex-col mx-10">
                    <h1 class="text-center">Pagas</h1>
                    <h1 class="text-center text-3xl font-semibold font-polard">${amount} ARS</h1>
                </div>
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 my-auto" fill="none" viewBox="0 0 24 24" stroke="black" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3" />
                </svg>
                <div class="flex flex-col mx-10">
                    <h1 class="text-center">Recibis</h1>
                    <h1 class="text-center text-3xl font-semibold font-polard"><fmt:formatNumber type="number" maxFractionDigits="10" value="${amount / offer.askingPrice }"/> ${offer.crypto.code}</h1>
                </div>
            </div>
            <c:url value="/trade" var="postUrl"/>
            <form:form  modelAttribute="tradeForm" action="${postUrl}" method="post">
                <form:hidden path="amount" value="${amount / offer.askingPrice }"/>
                <form:hidden path="offerId" value="${ offer.id}"/>
                <div class="flex flex-col mx-auto mt-7">
                    <form:errors path="wallet" cssClass="text-red-400 mx-auto"/>
                    <form:label path="wallet" cssClass="text-xl font-sans font-polard font-semibold mx-auto">Pega la dirección donde querés recibir los ${offer.crypto.code}</form:label>
                    <form:input  cssClass="rounded-lg p-3 mx-auto mt-3" type="text" path="wallet"/>
                </div>
                <div class="mt-10 p-10 rounded-lg bg-stormd/[0.9] flex flex-row justify-center mx-auto border-2 border-polard mx-20">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="black" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <h1 class="mx-2 text-lg my-auto">Depositale al vendedor</h1>
                </div>
                <div class="flex flex-row justify-between">
                    <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="javascript:history.back()">Cancelar trade</a>
                    <button class="bg-ngreen text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" type="submit">Ya le pague</button>
                    <a class="bg-nred text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="<c:url value="/contact"/>">Tuve un problema</a>
                </div>
            </form:form>



        </div>
    </div>
    <div class="flex flex-row w-2/5">
        <jsp:include page="../components/seller_info.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
            <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
            <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>


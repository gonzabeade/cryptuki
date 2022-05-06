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
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-col mt-10">
    <div class="flex flex-col">
        <div class="flex">
            <div class="flex flex-col mx-auto mt-10">
                <h2 class="font-sans font-semibold text-polard text-2xl text-center">Oferta # <c:out value="${offer.id}"/></h2>
                <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.commercialName}"/>" class="w-20 h-20 mx-auto">
                <h1 class="text-center text-4xl font-bold"><c:out value="${offer.crypto.commercialName}"/></h1>
                <h2 class="font-sans font-medium text-polard text-2xl text-center">a  <c:out value="${offer.askingPrice}"/> ARS </h2>
                <div class="flex flex-row mt-3 font-sans ">
                    <h2 class="font-sans mx-2"><b>Mínimo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.minQuantity}"/> ARS </h2>
                    <h2 class="font-sans"> <b>Máximo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice * offer.maxQuantity}"/> ARS </h2>
                </div>
            </div>
        </div>
        <div class="flex flex-row  mx-auto mt-10">
            <a class="bg-frostdr text-white  my-auto p-3 rounded-md font-sans mx-auto active:cursor-progress" href="<c:url value="/modify/${offer.id}"/>">Editar</a>
            <c:url value="/delete/${offer.id}" var="deleteUrl"/>
            <form:form method="post" action="${deleteUrl}" cssClass="flex my-auto mx-3">
                <button type="submit" class="bg-nred text-white text-center mx-auto p-3 rounded-md font-sans">Eliminar anuncio</button>
            </form:form>
        </div>

    </div>
    <div class="flex flex-row mt-10">
        <jsp:include page="../components/seller_info.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
            <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
            <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
            <jsp:param name="lastLogin" value="${sellerLastLogin.relativeTime}"/>
            <jsp:param name="message" value="${offer.comments}"/>
        </jsp:include>
    </div>

    <div class="shape-blob"></div>
    <div class="shape-blob one"></div>
    <div class="shape-blob two"></div>
    <div class="shape-blob" style="left: 50%"></div>

    <div class="shape-blob" style="left: 5%; top: 80%"></div>

</div>


</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="pages" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="offerList" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="activePage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="paymentMethods" scope="request" type="java.lang.Iterable"/>
<html>
<head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script src="https://cdn.tailwindcss.com"></script>
            <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
            <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
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
<div class="flex">
<div class=" flex mx-auto mt-10 bg-[#FAFCFF]/[0.9] p-4 rounded-full drop-shadow-md divide-x">
    <div class="flex flex-col my-auto mx-3">
        <label for="coin"  class="font-sans text-sm font-semibold text-center">Criptomoneda</label>
        <select name="coin" id="coin" class="bg-transparent p-2 mx-2" onchange="addQueryParam(this.id)">
            <option disabled selected>Selecciona una opción</option>
            <c:forEach items="${cryptocurrencies}" var="coin">
                <option value="<c:out value="${coin.code}"/>"><c:out value="${coin.commercialName}"/></option>
            </c:forEach>
        </select>
    </div>
    <div class="flex flex-col my-auto justify-center mx-3">
        <label for="pm" class="font-sans text-sm font-semibold  ml-2 text-center">Medio de Pago</label>
        <select name="pm" id="pm" class="bg-transparent p-2 mx-2" onchange="addQueryParam(this.id)">
            <option disabled selected>Selecciona una opción</option>
            <c:forEach items="${paymentMethods}" var="paymentMethod">
                <option value="${paymentMethod.name}"><c:out value="${paymentMethod.description}"/></option>
            </c:forEach>
        </select>
     </div>
    <div class="flex flex-col my-auto justify-center mx-3">
        <label for="price" class="font-sans text-sm font-semibold ml-2 text-center">Quiero comprar...</label>
        <div class="flex flex-row ">
            <input type="number" id="price" class="bg-transparent border-1 border-polard mx-2 p-2" onchange="addQueryParam(this.id)">
            <h1 class="font-sans font-semibold my-auto">ARS</h1>
        </div>
    </div>

    <a href="<c:url value="/"/>" class="drop-shadow-lg bg-frost rounded-full p-3 ml-4 my-auto" id="link" rel="search">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
        </svg>
    </a>
</div>
</div>
<div class="flex flex-row justify-center mt-3">
    <button onclick="resetAllFilters()" class="justify-start text-polard font-regular hidden" id="reset">Limpiar filtros</button>
</div>
<div class=" flex justify-center mx-20">
    <% request.setCharacterEncoding("UTF-8"); %>
    <jsp:include page="../components/welcome_message.jsp"/>
</div>
<div class="flex flex-col justify-center mx-60">
    <h1 class="text-right text-gray-400 mx-5">Obtuviste ${offerCount} resultado/s</h1>
    <ol class="min-w-full">
        <div>
            <c:forEach var="offer" items="${offerList}">
                <li>
                    <c:set  var="accepted_payments" value="${offer.paymentMethods}" scope="request"/>
                    <% request.setCharacterEncoding("UTF-8"); %>
                    <jsp:include page="../components/card.jsp">
                        <jsp:param name="currency" value="${offer.crypto.code}"/>
                        <jsp:param name="owner" value="${offer.seller.email}"/>
                        <jsp:param name="asking_price" value="${offer.askingPrice}"/>
                        <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                        <jsp:param name="offerId" value="${offer.id}"/>
                        <jsp:param name="minCoinAmount" value="${offer.minQuantity}"/>
                        <jsp:param name="maxCoinAmount" value="${offer.maxQuantity}"/>
                        <jsp:param name="userEmail" value="${userEmail}"/>
                        <jsp:param name="isAdmin" value="${isAdmin}"/>
                    </jsp:include>
                </li>
            </c:forEach>
        </div>
    </ol>
    <div class="flex flex-col">
        <% request.setCharacterEncoding("UTF-8"); %>
       <jsp:include page="../components/paginator.jsp">
           <jsp:param name="activePage" value="${activePage}"/>
           <jsp:param name="pages" value="${pages}"/>
       </jsp:include>
        <h1 class="mx-auto text-gray-400 mx-auto">Total de páginas: ${pages}</h1>
    </div>
</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>





</body>
</html>
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
<div class="flex flex-row mt-10 divide-x">
    <div class="flex flex-col mx-10 w-1/5">

        <h1 class="text-2xl font-bold font-sans"><messages:message code="filters"/> </h1>
        <div class="flex flex-col mx-3">
            <label for="coin"  class="font-sans text-sm font-semibold text-center"><messages:message code="cryptocurrency"/></label>
            <select name="coin" id="coin" class="bg-white p-2 mx-2 mt-2 rounded-lg" onchange="addQueryParam(this.id)">
                <option disabled selected><messages:message code="chooseAnOption"/></option>
                <c:forEach items="${cryptocurrencies}" var="coin">
                    <option value="<c:out value="${coin.code}"/>"><c:out value="${coin.commercialName}"/></option>
                </c:forEach>
            </select>
        </div>
        <div class="flex flex-col mx-3 mt-6">
            <label for="location" class="font-sans text-sm font-semibold ml-2 text-center"><messages:message
                    code="location"/></label>
            <select name="location" id="location" class="bg-white p-2 mx-2 mt-2 rounded-lg" onchange="addQueryParam(this.id)">
                <option disabled selected><messages:message code="chooseAnOption"/></option>
                <option value="AGRONOMIA">Agronomía</option>
                <option value="ALMAGRO">Almagro</option>
                <option value="BALVANERA">Balvanera</option>
                <option value="BARRACAS">Barracas</option>
                <option value="BELGRANO">Belgrano</option>
                <option value="BOEDO">Boedo</option>
                <option value="CABALLITO">Caballito</option>
                <option value="CHACARITA">Chacarita</option>
                <option value="COGHLAN">Coghlan</option>
                <option value="COLEGIALES">Colegiales</option>
                <option value="CONSTITUCION">Constitución</option>
                <option value="FLORES">Flores</option>
                <option value="FLORESTA">Floresta</option>
                <option value="LA_BOCA">La Boca</option>
                <option value="LA_PATERNAL">La Paternal</option>
                <option value="LINIERS">Liniers</option>
                <option value="MATADEROS">Mataderos</option>
                <option value="MONTE_CASTRO">Monte Castro</option>
                <option value="MONTSERRAT">Montserrat</option>
                <option value="NUEVA_POMPEYA">Nueva Pompeya</option>
                <option value="NUÑEZ">Nuñez</option>
                <option value="PALERMO">Palermo</option>
                <option value="PARQUE_AVELLANEDA">Parque Avellaneda</option>
                <option value="PARQUE_CHACABUCO">Parque Chacabuco</option>
                <option value="PARQUE_CHAS">Parque Chas</option>
                <option value="PARQUE_PATRICIOS">Parque Patricios</option>
                <option value="PUERTO_MADERO">Puerto Madero</option>
                <option value="RECOLETA">Recoleta</option>
                <option value="RETIRO">Retiro</option>
                <option value="SAAVEDRA">Saavedra</option>
                <option value="SAN_CRISTOBAL">San Cristóbal</option>
                <option value="SAN_NICOLAS">San Nicolás</option>
                <option value="SAN_TELMO">San Telmo</option>
                <option value="VERSALLES">Versalles</option>
                <option value="VILLA_CRESPO">Villa Crespo</option>
                <option value="VILLA_DEVOTO">Villa Devoto</option>
                <option value="VILLA_GENERAL_MITRE">Villa General Mitre</option>
                <option value="VILLA_LUGANO">Villa Lugano</option>
                <option value="VILLA_LURO">Villa Luro</option>
                <option value="VILLA_ORTUZAR">Villa Ortúzar</option>
                <option value="VILLA_PUEYRREDÓN">Villa Pueyrredón</option>
                <option value="VILLA_REAL">Villa Real</option>
                <option value="VILLA_RIACHUELO">Villa Riachuelo</option>
                <option value="VILLA_SANTA_RITA">Villa Santa Rita</option>
                <option value="VILLA_SOLDATI">Villa Soldati</option>
                <option value="VILLA_URQUIZA">Villa Urquiza</option>
                <option value="VILLA_DEL_PARQUE">Villa Del Parque</option>
                <option value="VELEZ_SARSFIELD">Vélez Sarsfield</option>
            </select>
        </div>
        <div class="flex flex-row justify-center mt-3">
            <button onclick="resetAllFilters()" class="justify-start text-polard font-regular hidden" id="reset"><messages:message code="cleanFilters"/></button>
        </div>
        <a href="<c:url value="/buyer/market"/>" id="link" rel="search"
           class="py-2 px-6  text-white rounded-lg bg-frost  mx-auto"><messages:message
                code="filter"/>
        </a>
        <h1 class="text-2xl font-bold font-sans mt-5"><messages:message code="marketPrice"/> </h1>
        <div class="flex flex-col">
            <jsp:include page="../components/cryptoCard.jsp">
                <jsp:param name="code" value="BTC"/>
                <jsp:param name="commercialName" value="Bitcoin"/>
            </jsp:include>
            <jsp:include page="../components/cryptoCard.jsp">
                <jsp:param name="code" value="ETH"/>
                <jsp:param name="commercialName" value="Ethereum"/>
            </jsp:include>
            <jsp:include page="../components/cryptoCard.jsp">
                <jsp:param name="code" value="USDT"/>
                <jsp:param name="commercialName" value="Tether"/>
            </jsp:include>
            <jsp:include page="../components/cryptoCard.jsp">
                <jsp:param name="code" value="DAI"/>
                <jsp:param name="commercialName" value="Dai"/>
            </jsp:include>
        </div>

    </div>
    <div class="flex flex-col w-4/5">
        <div class="flex flex-row justify-between">
            <div class="flex flex-row mx-5">
                <%--            <div class="my-auto mr-5" onclick="sendGet()">--%>
                <%--                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">--%>
                <%--                    <path stroke-linecap="round" stroke-linejoin="round" d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z" />--%>
                <%--                </svg>--%>
                <%--            </div>--%>
                <p class="my-auto font-sans font-bold"><messages:message code="orderBy"/></p>
                <select name="orderingCriterion" id="orderingCriterion" class="ml-5 rounded-sm p-2 my-auto " onchange="addQueryParam(this.id);addDirection(this.id);sendGet()">
                    <option value="0"><messages:message code="dateOrder"/> </option>
                    <%--                <option value="1"><messages:message code="lastLogin"/></option>--%>
                    <option value="1"><messages:message code="ratingFilter"/></option>
                    <option value="2"><messages:message code="priceLowToHigh"/></option>
                    <option value="3"><messages:message code="priceHighToLow"/></option>
                </select>
                <%--            <div id="orderingDirection" name="desc" class="mx-2 my-auto hidden"--%>
                <%--                 onclick="hide('desc');addQueryParamOrder(this.id, 1);sendGet();">--%>
                <%--                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"--%>
                <%--                     stroke="currentColor" stroke-width="2">--%>
                <%--                    <path stroke-linecap="round" stroke-linejoin="round" d="M16 17l-4 4m0 0l-4-4m4 4V3"/>--%>
                <%--                </svg>--%>
                <%--            </div>--%>
                <%--            <div class="mx-2 my-auto hidden" name="asc" id="orderingDirection"--%>
                <%--                 onclick="hide('asc');addQueryParamOrder(this.id, 0);sendGet();">--%>
                <%--                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">--%>
                <%--                    <path stroke-linecap="round" stroke-linejoin="round" d="M8 7l4-4m0 0l4 4m-4-4v18" />--%>
                <%--                </svg>--%>
                <%--            </div>--%>
                <%--            <select id="orderDirection" name="orderDirection">--%>
                <%--                <option value="0">--%>
                <%--                    DESC--%>
                <%--                </option>--%>
                <%--                <option value="1">--%>
                <%--                    ASC--%>
                <%--                </option>--%>
                <%--            </select>--%>

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
<%--<div class="flex">--%>
<%--<div class=" flex mx-auto mt-10 bg-[#FAFCFF]/[0.9] p-4 rounded-full drop-shadow-md divide-x">--%>
<%--    <div class="flex flex-col my-auto mx-3">--%>
<%--        <label for="coin"  class="font-sans text-sm font-semibold text-center"><messages:message code="cryptocurrency"/></label>--%>
<%--        <select name="coin" id="coin" class="bg-transparent p-2 mx-2" onchange="addQueryParam(this.id)">--%>
<%--            <option disabled selected><messages:message code="chooseAnOption"/></option>--%>
<%--            <c:forEach items="${cryptocurrencies}" var="coin">--%>
<%--                <option value="<c:out value="${coin.code}"/>"><c:out value="${coin.commercialName}"/></option>--%>
<%--            </c:forEach>--%>
<%--        </select>--%>
<%--    </div>--%>
<%--    &lt;%&ndash;--%>
<%--    <div class="flex flex-col my-auto justify-center mx-3">--%>
<%--        <label for="pm" class="font-sans text-sm font-semibold  ml-2 text-center"><messages:message code="paymentMethod"/></label>--%>
<%--        <select name="pm" id="pm" class="bg-transparent p-2 mx-2" onchange="addQueryParam(this.id)">--%>
<%--            <option disabled selected><messages:message code="chooseAnOption"/></option>--%>
<%--            <c:forEach items="${paymentMethods}" var="paymentMethod">--%>
<%--                <option value="${paymentMethod.name}"><c:out value="${paymentMethod.description}"/></option>--%>
<%--            </c:forEach>--%>
<%--        </select>--%>
<%--     </div>--%>
<%--     &ndash;%&gt;--%>

<%--    <div class="flex flex-col my-auto justify-center mx-3">--%>
<%--        <label for="location" class="font-sans text-sm font-semibold ml-2 text-center"><messages:message code="location"/></label>--%>
<%--        <div class="flex flex-row mx-auto ">--%>
<%--            <input type="text" id="location" class="bg-transparent border-b-2 border-gray-200 mx-2 p-2" onchange="addQueryParam(this.id)">--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div class="flex flex-col my-auto justify-center mx-3">--%>
<%--        <label for="price" class="font-sans text-sm font-semibold ml-2 text-center"><messages:message code="wantToBuy"/></label>--%>
<%--        <div class="flex flex-row ">--%>
<%--            <input type="number" id="price" class="bg-transparent border-b-2 border-gray-200 mx-2 p-2" onchange="addQueryParam(this.id)">--%>
<%--            <h1 class="font-sans font-semibold my-auto">ARS</h1>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <a href="<c:url value="/buyer/market"/>" class="drop-shadow-lg bg-frost rounded-full p-3 ml-4 my-auto" id="link" rel="search">--%>
<%--        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">--%>
<%--        <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>--%>
<%--        </svg>--%>
<%--    </a>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="flex flex-row justify-center mt-3">--%>
<%--    <button onclick="resetAllFilters()" class="justify-start text-polard font-regular hidden" id="reset"><messages:message code="cleanFilters"/></button>--%>
<%--</div>--%>
<%--<div class=" flex justify-center mx-20">--%>
<%--    <% request.setCharacterEncoding("UTF-8"); %>--%>
<%--    <jsp:include page="../components/welcomeMessage.jsp"/>--%>
<%--</div>--%>

<%--<jsp:include page="../components/footer.jsp"/>--%>

<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
<script>

</script>




</body>
</html>
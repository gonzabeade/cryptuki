<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="pages" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="offerList" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="activePage" scope="request" type="java.lang.Integer"/>
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
<jsp:include page="../components/header.jsp"/>
<div class=" flex justify-center mx-20">
    <jsp:include page="../components/welcome_message.jsp"/>
</div>
<div class="flex flex-col justify-center mx-60">
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
                        <jsp:param name="coinAmount" value="${offer.coinAmount}"/>
                    </jsp:include>
                </li>
            </c:forEach>
        </div>
    </ol>
    <div>
        <div class="absolute left-[37%] my-6">
            <c:if  test="${activePage > 0}">
                <a href="<c:url value="/?page=${activePage - 1}"/>"  class="font-bold font-sans text-polard ">Anterior</a>
            </c:if>
        </div>
        <div class="flex flex-row mx-40 justify-center ">
        <c:forEach var = "i" begin = "${activePage - 1 < 0 ? activePage : activePage - 1 }" end = "${(activePage + 1 > pages - 1 )? pages - 1 : activePage + 1 }">
            <c:choose>
                <c:when test="${activePage == i }">
                    <a href="<c:url value="/?page=${i}"/>" class="bg-stormd border-2 border-polard active:text-white-400 px-3 py-1 mx-4 my-5 rounded-full "><c:out value="${i+1}"/></a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/?page=${i}"/>" class="bg-storm active:text-white-400 px-3 py-1 mx-4 my-5 rounded-full"><c:out value="${i+1}"/></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        </div>
        <div class="absolute right-[37%] -mt-[50px]">
            <c:if test="${activePage < pages-1}">
                <a href="<c:url value="/?page=${activePage + 1}"/>" class="font-bold font-sans text-polard">Siguiente</a>
            </c:if>
        </div>




    </div>
</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>





</body>
</html>
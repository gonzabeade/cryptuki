<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
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
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp"/>
<h1 class="text-center text-4xl font-semibold font-sans text-polar mt-20 mb-20">Conocé las principales criptomonedas del mercado.</h1>
<div class="mx-48">
    <ol class="min-w-50%">
        <div class="flex justify-center flex-wrap">
            <c:forEach var="coin" items="${coinList}">
                <li class="m-10">
                    <jsp:include page="../components/crypto_card.jsp">
                        <jsp:param name="code" value="${coin.code}"/>
                        <jsp:param name="name" value="${coin.name}"/>
                        <jsp:param name="marketPrice" value="${coin.marketPrice}"/>
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
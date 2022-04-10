<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">

</head>
<body class="bg-storml">
<jsp:include page="../components/header.jsp"/>
<div class="flex justify-center mx-48">
    <ol class="min-w-full">
        <div>
            <c:forEach var="coin" items="${coinList}">
                <li>
                    <jsp:include page="../components/CoinCard.jsp">
                        <jsp:param name="code" value="${coin.code}"/>
                        <jsp:param name="name" value="${coin.name}"/>
                        <jsp:param name="marketPrice" value="${coin.marketPrice}"/>
                    </jsp:include>
                </li>
            </c:forEach>
        </div>
    </ol>
</div>

</body>
</html>
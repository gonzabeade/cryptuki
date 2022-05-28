<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>"/>
</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../../components/seller/sellerHeader.jsp"/>
<div class="flex h-full w-full px-20 my-10">
    <!-- Left Panel: chat and seller stats -->
    <div class="flex flex-col h-full mx-20 w-1/5">
        <div class="h-20">
            <jsp:include page="../../components/seller/sellerStatsCard.jsp"/>
        </div>
        <div class="my-auto pt-15">
            <jsp:include page="../../components/seller/sellerChatCard.jsp"/>
        </div>
    </div>

    <!-- Right Panel: uploaded offers -->
    <div class="flex flex-col h-full mr-20 w-2/5">
        <jsp:include page="../../components/seller/sellerOfferCard.jsp"/>
        <jsp:include page="../../components/seller/sellerOfferCard.jsp"/>
        <jsp:include page="../../components/seller/sellerOfferCard.jsp"/>
        <jsp:include page="../../components/seller/sellerOfferCard.jsp"/>
        <jsp:include page="../../components/seller/sellerOfferCard.jsp"/>
        <jsp:include page="../../components/seller/sellerOfferCard.jsp"/>
    </div>

    <div class="flex flex-col h-full mr-10 w-1/5">
        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>
        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>
        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>
        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>
    </div>

</div>




</body>


</html>

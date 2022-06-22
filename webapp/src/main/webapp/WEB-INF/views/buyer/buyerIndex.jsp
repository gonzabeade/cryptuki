<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="name" var="username"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/pagination.js"/> "></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../../components/buyer/buyerHeader.jsp"/>
<div class="flex h-full w-full px-20 my-10">
    <!-- Left Panel: chat and seller stats -->
    <div class="flex flex-col h-full mx-20 w-1/5">
        <div class="">
            <jsp:include page="../../components/profile/userStatsCard.jsp">
                <jsp:param name="username" value="${username}"/>
                <jsp:param name="email" value="${user.email}"/>
                <jsp:param name="phoneNumber" value="${user.phoneNumber}"/>
                <jsp:param name="rating" value="${user.rating}"/>
                <jsp:param name="ratingCount" value="${user.ratingCount}"/>
                <jsp:param name="isBuyer" value="${true}"/>
            </jsp:include>
        </div>
<%--        <div class="my-5">--%>
<%--        </div>--%>
    </div>

    <!-- Middle Panel: trade -->
    <div class="flex flex-col h-full mr-20 w-3/5">
        <div class="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
            <h1 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="buyOrdersCreated"/></h1>
        </div>


<%--        <jsp:include page="../../components/tradeFilter.jsp">--%>
<%--            <jsp:param name="url" value="/buyer"/>--%>
<%--        </jsp:include>--%>
        <jsp:include page="../../components/buyer/buyerTradeFilter.jsp"/>


    <div  class="flex flex-col justify-center w-full mx-auto mt-10">
            <c:forEach var="trade" items="${tradeList}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../../components/BuyingTradeCard.jsp">
                    <jsp:param name="username" value="${username}"/>
                    <jsp:param name="askedPrice" value="${trade.offer.unitPrice}"/>
                    <jsp:param name="unseenMessages" value="${trade.qUnseenMessagesBuyer}"/>
                    <jsp:param name="quantity" value="${trade.quantity}"/>
                    <jsp:param name="tradeStatus" value="${trade.status.toString()}"/>
                    <jsp:param name="cryptoCurrencyCode" value="${trade.offer.crypto.code}"/>
                    <jsp:param name="tradeId" value="${trade.tradeId}"/>
                </jsp:include>
            </c:forEach>
        </div>

        <c:if test="${noBuyingTrades}">
            <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noBuyingProposalSend"/></h2>
<%--            <a href="<c:url value="/"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message code="startBuying"/></a>--%>
        </c:if>

        <c:if test="${!noBuyingTrades}">
            <div class="flex flex-col mt-3">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../../components/paginator.jsp">
                    <jsp:param name="activePage" value="${activePage}"/>
                    <jsp:param name="pages" value="${pages}"/>
                    <jsp:param name="baseUrl" value="/buyer/"/>
                </jsp:include>
                <c:if test="${pages >0}">
                    <h1 class="mx-auto text-gray-400 mx-auto mt-3"><messages:message code="totalPageAmount"/>: ${pages}</h1>
                </c:if>
              </div>
        </c:if>
    </div>

    <!-- Right Panel: crypto dashboard -->

<%--    <div class="flex flex-col h-full mr-10 w-1/5">--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--    </div>--%>

</div>




</body>


</html>

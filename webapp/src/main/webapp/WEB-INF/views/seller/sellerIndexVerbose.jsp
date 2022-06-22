<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<sec:authentication property="name" var="username"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/sellerDashboard.js"/>"></script>
    <script src="<c:url value="/public/js/pagination.js"/> "></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>"/>
</head>
<body class="bg-storml overflow-x-hidden">
    <jsp:include page="../../components/seller/sellerHeader.jsp"/>
    <div class="flex h-full w-full px-10 my-10">

        <!-- Left Panel: chat and seller stats -->
        <div class="flex flex-col h-full mx-10 w-1/3">
            <div>
                <jsp:include page="../../components/profile/userStatsCard.jsp">
                    <jsp:param name="username" value="${user.username.get()}"/>
                    <jsp:param name="email" value="${user.email}"/>
                    <jsp:param name="phoneNumber" value="${user.phoneNumber}"/>
                    <jsp:param name="rating" value="${user.rating}"/>
                    <jsp:param name="ratingCount" value="${user.ratingCount}"/>
                </jsp:include>
            </div>
            <c:choose>
                <c:when test="${user.kyc == null }">
                    <div class="flex flex-row bg-white shadow rounded-lg p-3 mt-6 font-sans font-bold">
                        <img class="w-5 h-5 mr-4 my-auto " src="<c:url value = "/public/images/attention.png"/>">
                        <p><messages:message code="validateYourIdentityExplanation"/></p>
                    </div>
                    <div class="mx-auto mt-8">
                        <a href="<c:url value="/kyc"/>"
                           class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto"><messages:message code="validateYourIdentity"/></a>
                    </div>
                </c:when>
                <c:when test="${user.kyc.status != 'APR'}">
                    <div class="flex flex-row bg-white shadow rounded-lg p-3 mt-3 font-sans font-bold">
                        <img class="w-5 h-5 mr-4 my-auto " src="<c:url value = "/public/images/attention.png"/>">
                        <p><messages:message code="validateYourIdentityPending"/></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${! empty tradeList}">
                        <div class="mt-5">
                            <div class="py-4 bg-white rounded-lg shadow-md">
                                <div class="flex justify-between items-center mb-2 px-4 pt-2">
                                    <h5 class="text-xl font-bold leading-none text-polar"><messages:message code="lastTransactions"/> </h5>
                                </div>
                                <div class="px-4">
                                    <ul role="list" class="divide-y divide-gray-200">
                                        <c:forEach items="${tradeList}" var="trade">
                                            <li class="py-2">
                                                <a class="flex items-center space-x-4 hover:bg-gray-100 rounded-lg p-1 cursor-pointer" href="<c:url value="/chat?tradeId=${trade.tradeId}"/> ">
                                                    <div class="flex-shrink-0">
                                                        <c:if test="${trade.status == 'PENDING'}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#ebcb8b" stroke-width="2">
                                                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                                                            </svg>
                                                        </c:if>
                                                        <c:if test="${trade.status == 'REJECTED'}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#bf616a" stroke-width="2">
                                                                <path stroke-linecap="round" stroke-linejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                                            </svg>
                                                        </c:if>
                                                        <c:if test="${trade.status == 'SOLD'}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#e5e7eb" stroke-width="2">
                                                                <path stroke-linecap="round" stroke-linejoin="round" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                                            </svg>
                                                        </c:if>
                                                        <c:if test="${trade.status == 'ACCEPTED'}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#a3be8c" stroke-width="2">
                                                                <path stroke-linecap="round" stroke-linejoin="round" d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                                                            </svg>
                                                        </c:if>
                                                        <c:if test="${trade.status == 'DELETED'}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="#60a5fa" stroke-width="2">
                                                                <path stroke-linecap="round" stroke-linejoin="round" d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636" />
                                                            </svg>

                                                        </c:if>

                                                    <%--                                                    <img class="w-8 h-8 rounded-full" src="<c:url value="/profilepic/${trade.buyer.username.get()}"/>" alt="Profile pic">--%>
                                                    </div>
                                                    <div class="flex-1 min-w-0">
                                                        <div class="flex flex-row justify-between">
                                                            <p class="text-sm font-medium text-polar-600 truncate"> <c:out value="${trade.buyer.username.get()}"/> </p>
                                                            <h1 class="text-sm  text-polar truncate"><messages:message code="${trade.status}"/> <messages:message code="for"/> <fmt:formatNumber type="number" maxFractionDigits="10" value="${trade.quantity / trade.offer.unitPrice}"/> <c:out value="${trade.offer.crypto.code}"/> </h1>
                                                        </div>

                                                        <p class="text-sm text-gray-500 truncate"><messages:message code="${trade.status}.madeYouAnOffer"/> </p>
                                                    </div>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <div class="mx-auto mt-5">
                                            <a href="<c:url value="/offer/upload"/>"
                                               class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto"><messages:message
                                                    code="uploadAdvertisement"/></a>
                                        </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Middle panel -->
        <div class="flex flex-col h-full mr-20 w-4/5">
            <div class="shadow-xl w-full h-1/8 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                <h1 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="uploadedAdvertisements"/> </h1>
            </div>
            <div class="flex flex-col w-full  mt-5">
                <div class="flex w-full ">
                    <jsp:include page="../../components/seller/sellerOfferFilter.jsp">
                        <jsp:param name="status" value="${status}"/>
                    </jsp:include>
                </div>
                <div class="flex flex-wrap w-full mx-auto justify-center mt-2">
                        <c:forEach var="offer" items="${offerList}">
                            <%--    Tarjeta de anuncio--%>
                            <c:set var="offer" value="${offer}" scope="request"/>
                         <jsp:include page="../../components/offer.jsp"/>
                        </c:forEach>
                </div>
                <div class="mx-auto">
                    <jsp:include page="../../components/paginator.jsp">
                        <jsp:param name="activePage" value="${activePage}"/>
                        <jsp:param name="pages" value="${pages}"/>
                        <jsp:param name="baseUrl" value="/seller/"/>
                    </jsp:include>
                </div>


            </div>


        </div>
    </div>
</body>


</html>



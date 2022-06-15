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
    <div class="flex h-full w-full px-20 my-10">

        <!-- Left Panel: chat and seller stats -->
        <div class="flex flex-col h-full mx-20 w-1/5">
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
                <c:when test="${user.kyc == null}">
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
                    <div class="mt-5">
                        <div class="py-4 bg-[#FAFCFF] rounded-lg shadow-md">
                            <div class="flex justify-between items-center mb-2 px-4 pt-2">
                                <h5 class="text-xl font-bold leading-none text-polar">Última actividad</h5>
                                <a href="#" class="text-sm font-medium text-blue-600 hover:underline dark:text-blue-500">
                                    Ver toda
                                </a>
                            </div>
                            <div class="px-4">
                                <ul role="list" class="divide-y divide-gray-200">
                                    <c:forEach items="${offerList}">
                                        <li class="py-2">
                                            <div class="flex items-center space-x-4 hover:bg-gray-100 rounded-lg p-1 cursor-pointer">
                                                <div class="flex-shrink-0">
                                                    <img class="w-8 h-8 rounded-full" src="https://picsum.photos/700/800" alt="Neil image">
                                                </div>
                                                <div class="flex-1 min-w-0">
                                                    <p class="text-sm font-medium text-polar-600truncate"> gonzabeade </p>
                                                    <p class="text-sm text-gray-500 truncate">Ha hecho una orden de compra</p>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="mx-auto mt-5">
                        <a href="<c:url value="/offer/upload"/>"
                           class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto"><messages:message
                                code="uploadAdvertisement"/></a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Middle panel -->
        <div class="flex flex-col h-full mr-20 w-3/5">
            <div class="shadow-xl w-full h-1/8 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
                <h1 class="text-center text-4xl font-semibold font-sans text-polar">Anuncios subidos</h1>
            </div>
            <jsp:include page="../../components/seller/sellerOfferFilter.jsp"></jsp:include>
            <div class="flex flex-row w-full h-3/5 mt-5">
                <div class="flex flex-col h-3/5 w-full">
                    <div class="h-4/5 w-full flex flex-wrap justify-around">
                        <c:forEach var="offer" items="${offerList}">
                            <%--    Tarjeta de anuncio--%>
                            <div class="h-2/5 w-2/5 mx-3 shadow-xl mb-5 flex flex-col rounded-lg mx-5 py-3 px-2 bg-[#FAFCFF] hover:bg-gray-100 cursor-pointer">
                                    <div class="flex flex-row w-full justify-between">
                                        <div class="font-bold font-sans text-2xl my-auto">Anuncio 4</div>
                                        <div class="flex flex-row my-auto justify-end align-end">
                                            <img class="w-6 h-6" src="<c:url value = "/public/images/ETH.png"/>">
                                            <div class="font-bold font-sans text-l">1 ETH ➡ 540 ARS</div>
                                        </div>
                                    </div>
                                    <div class="flex flex-row my-auto h-full my-auto">
                                        <div class="flex flex-row">
                                            <div class="font-sans font-gray-100 text-s my-auto italic">Aceptas entre 2 ETH y 4 ETH.</div>
                                        </div>
                                        <div class="flex rounded-lg bg-storml h-6 w-6 hover:bg-stormd ml-3 my-auto">
                                            <img class="m-auto h-4 w-4" src="<c:url value="/public/images/edit.png"/>"/>
                                        </div>
                                        <div class="flex rounded-lg bg-storml h-6 w-6 my-auto hover:bg-stormd ml-1 my-auto">
                                            <img class="m-auto h-4 w-4" src="<c:url value="/public/images/pause.png"/>"/>
                                        </div>
                                        <div class="flex rounded-lg bg-storml h-6 w-6 hover:bg-stormd ml-1 my-auto">
                                            <img class="m-auto h-4 w-4" src="<c:url value="/public/images/delete.png"/>"/>
                                        </div>
                                    </div>
                            </div>

                        </c:forEach>
                    </div>
                    <div class="mx-auto mt-40">
                        <jsp:include page="../../components/paginator.jsp">
                            <jsp:param name="activePage" value="${activePage}"/>
                            <jsp:param name="pages" value="${pages}"/>
                            <jsp:param name="baseUrl" value="/buyer/"/>
                        </jsp:include>
                    </div>
                </div>


            </div>


        </div>
    </div>
</body>


</html>



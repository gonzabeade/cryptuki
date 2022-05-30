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
        <div>
            <jsp:include page="../../components/seller/sellerStatsCard.jsp">
                <jsp:param name="username" value="${username}"/>
                <jsp:param name="email" value="${user.email}"/>
                <jsp:param name="phoneNumber" value="${user.phoneNumber}"/>
                <jsp:param name="rating" value="${user.rating}"/>
                <jsp:param name="ratingCount" value="${user.ratingCount}"/>
            </jsp:include>
        </div>
        <div class="mx-auto mt-10">
            <a href="<c:url value="/seller/upload"/>"
               class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto">Subir anuncio</a>
        </div>
<%--        <div class="my-5">--%>
<%--            <jsp:include page="../../components/seller/sellerChatCard.jsp"/>--%>
<%--        </div>--%>
    </div>

    <!-- Middle Panel: trade -->

    <div class="flex flex-wrap h-full mr-20 w-3/5">

        <c:forEach var="offer" items="${offerList}">
            <div class="flex flex-row my-5 mx-5 h-2/5">

                    <%--    Tarjeta de anuncio--%>
                <div class="shadow-xl w-[270px] flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] z-20 justify-center items-center content-start">

                    <div class="flex flex-col items-center mt-5 mb-5">
                        <h1 class="text-[30px] font-bold font-sans"><c:out value="Anuncio #${offer.id}"/></h1>
                    </div>

                    <div class="flex flex-row items-center">
                        <h1 class="text-xl font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="2" value="${offer.askingPrice}"/> ARS </h1>
                        <p class="my-auto mx-2"> <messages:message code="for"/> </p>
                        <h1 class="text-xl font-sans font-semibold my-auto"><c:out value="${offer.crypto.code}"/></h1>
                        <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>" alt="<c:out value="${offer.crypto.code}"/>" class="w-5 h-5 mx-2 my-auto"/>
                    </div>

                    <div class="flex flex-col justify-start -ml-5 mb-3">
                        <div class="flex flex-col font-sans mt-3">
                            <p><c:out value="Min: ${offer.minQuantity}"/><c:out value=" ${offer.crypto.code}"/></p>
                        </div>
                        <div class="flex flex-col font-sans mt-3">
                            <p><c:out value="Max: ${offer.maxQuantity}"/><c:out value=" ${offer.crypto.code}"/></p>
                        </div>
                        <div class="flex flex-col font-sans mt-3">
                            <p>Fecha: <c:out value="${offer.date.toLocalDate()}"/></p>
                        </div>
                        <div class="flex flex-col font-sans mt-3">
                            <p>Ubicación: <c:out value="Caballito"/></p>
                        </div>
                    </div>


                    <div class="flex flex-row mx-auto mt-5">
                        <a href="<c:url value="/modify/${offer.id}"/>" class="flex rounded-lg bg-storml h-8 w-8 mr-3 hover:bg-stormd">
                            <img class="m-auto h-5 w-5" src="<c:url value="/public/images/edit.png"/>"/>
                        </a>

                        <form action="<c:url value="/delete/${offer.id}"/>" method="post">
                            <button class="flex rounded-lg bg-storml  h-8 w-8 hover:bg-stormd">
                                <img class="m-auto h-5 w-5" src="<c:url value="/public/images/delete.png"/>"/>
                            </button>
                        </form>

                    </div>
                </div>

                <div id="<c:out value="${offer.id}" />" class="flex flex-row shadow-xl rounded-lg  w-[900px] bg-gray-100 -ml-2 z-10 p-5 overflow-x-scroll overflow-y-hidden hidden">
                    <c:forEach var="trade" items="${offer.associatedTrades}">
                        <div class="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mx-5">

                            <div class="flex font-sans h-fit w-full mt-5">
                                <c:if test="${trade.status == 'PENDING' }">
                                    <div class="bg-nyellow  w-full text-white  text-center p-2"><messages:message code="pending"/> </div>
                                </c:if>

                                <c:if test="${trade.status == 'REJECTED' }">
                                    <div class="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message code="rejected"/> </div>
                                </c:if>

                                <c:if test="${trade.status == 'ACCEPTED' }">
                                    <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/> </div>
                                </c:if>

                                <c:if test="${trade.status == 'SOLD' }">
                                    <div class="bg-gray-400 w-full text-white text-center p-2"><messages:message code="sold"/> </div>
                                </c:if>
                            </div>

                            <div class="flex flex font-sans my-3  w-52 mx-auto text-semibold">
                                <h1 class="mx-auto"> <c:out value="${offer.minQuantity}"/><c:out value=" ${offer.crypto.code}"/> ⟶ <c:out value="${trade.quantity} "/>ARS</h1>
                            </div>

                            <div class="flex flex-col">
                                <div class="flex">
                                    <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>
                                        <%--            <h1 class="font-sans font-semibold"><c:out value="${param.buyerUsername}"/></h1>--%>
                                    <h1 class="font-sans font-semibold"><c:out value="${trade.buyerUsername}"/></h1>
                                </div>
                                <div class="flex">
                                    <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
                                        <%--            <h1 class="font-sans font-semibold"><c:out value="${param.buyerMail}"/></h1>--%>
                                    <h1 class="font-sans font-semibold"><c:out value="??????"/></h1>
                                </div>
                                <div class="flex">
                                    <h1 class="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>
                                    <h1 class="font-sans font-semibold"><c:out value="??????"/></h1>
                                </div>
                                <div class="flex">
                                    <h1 class="font-sans mr-2"><messages:message code="rating"/>:</h1>
                                    <h1 class="font-sans font-semibold"><c:out value="?????"/></h1>
                                </div>
                            </div>

<%--                            CASE - PENDING--%>
                            <c:if test="${trade.status.equals('PENDING')}">
                                <c:url value="/seller/changeStatus" var="postUrl"/>
                                <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post" cssClass="flex justify-center mx-auto my-3">
                                    <form:hidden path="newStatus"  id="newStatus-${trade.tradeId}" value="${trade.status}"/>
                                    <form:hidden path="tradeId" value="${trade.tradeId}"/>

                                    <button onclick="updateStatus('REJECTED', ${trade.tradeId})" type="submit"
                                            class="bg-red-400 text-white p-3  rounded-md font-sans mr-4"><messages:message
                                            code="rejectTrade"/></button>
                                    <button onclick="updateStatus('ACCEPTED', ${trade.tradeId})" type="submit"
                                            class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message
                                            code="acceptTrade"/></button>
                                </form:form>
                            </c:if>

                                <%--                            CASE - ACCEPTED--%>
                            <c:if test="${trade.status.equals('ACCEPTED')}">
                                <c:url value="/closeTrade" var="formUrl"/>
                                <form:form modelAttribute="soldTradeForm" action="${formUrl}" method="post" cssClass="flex justify-center mx-auto my-3" >
                                    <form:hidden path="offerId" value="${offer.id}"/>
                                    <form:hidden path="trade" value="${trade.tradeId}"/>
                                    <button type="submit"
                                            class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">
                                        <messages:message code="soldTrade"/></button>
                                </form:form>
                            </c:if>

                                <%--                            CASE - REJECTED--%>
                            <c:if test="${!(trade.status.equals('ACCEPTED')) && !(trade.status.equals('PENDING'))}">
                                <div class="flex h-2/5 my-2"></div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>

                <div class="flex flex-col w-10 -ml-5 h-full" onClick="toggle(<c:out value="${offer.id}" />)">
                    <div class="my-auto bg-gray-400 shadow-xl rounded-lg h-5/6 hover:bg-gray-300 hover:-mr-7">
                    </div>
                </div>

            </div>
        </c:forEach>



        <div  class="flex flex-col justify-center w-full mx-auto mt-10">
            <c:forEach var="trade" items="${tradeList}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../../components/horizontalSellingTradeCard.jsp">
                    <jsp:param name="username" value="${username}"/>
                    <jsp:param name="askedPrice" value="${trade.askedPrice}"/>
                    <jsp:param name="quantity" value="${trade.quantity}"/>
                    <jsp:param name="tradeStatus" value="${trade.status.toString()}"/>
                    <jsp:param name="cryptoCurrencyCode" value="${trade.cryptoCurrency.code}"/>
                    <jsp:param name="tradeId" value="${trade.tradeId}"/>
                    <jsp:param name="offerId" value="${trade.offerId}"/>
                </jsp:include>
            </c:forEach>
        </div>

        <c:if test="${noSellingTrades}">
            <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noSellingProposalReceived"/></h2>
            <a href="<c:url value="/seller/myoffers"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message code="seeAdvertisements"/></a>
        </c:if>

        <c:if test="${!noSellingTrades}">
            <div class="flex flex-col mt-3">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../../components/paginator.jsp">
                    <jsp:param name="activePage" value="${activePage}"/>
                    <jsp:param name="pages" value="${pages}"/>
                    <jsp:param name="baseUrl" value="/mytrades"/>
                </jsp:include>
                <h1 class="mx-auto text-gray-400 mx-auto mt-3"><messages:message code="totalPageAmount"/>: ${pages}</h1>
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


<script>
    function toggle(id){
        if (document.getElementById(id).classList.contains("hidden")) {
            document.getElementById(id).classList.remove("hidden");
        } else {
            document.getElementById(id).classList.add("hidden");
        }
    }
</script>

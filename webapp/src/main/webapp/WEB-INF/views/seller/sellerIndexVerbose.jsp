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
    <script src="<c:url value="/public/js/filterLink.js"/> "></script>
    <script src="<c:url value="/public/js/sellerDashboard.js"/>"></script>
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
            <jsp:include page="../../components/seller/sellerStatsCard.jsp">
                <jsp:param name="username" value="${username}"/>
                <jsp:param name="email" value="${user.email}"/>
                <jsp:param name="phoneNumber" value="${user.phoneNumber}"/>
                <jsp:param name="rating" value="${user.rating}"/>
                <jsp:param name="ratingCount" value="${user.ratingCount}"/>
            </jsp:include>
        </div>
        <c:choose>
            <c:when test="${isKycValidated}">
                <div class="mx-auto mt-10">
                    <a href="<c:url value="/seller/upload"/>"
                       class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto"><messages:message
                            code="uploadAdvertisement"/></a>
                </div>
            </c:when>
            <c:when test="${kyc.present}">
                <div class="flex flex-row bg-white shadow rounded-lg p-3 mt-3 font-sans font-bold">
                    <img class="w-5 h-5 mr-4 my-auto " src="<c:url value = "/public/images/attention.png"/>">
                    <p><messages:message code="validateYourIdentityPending"/></p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="flex flex-row bg-white shadow rounded-lg p-3 mt-6 font-sans font-bold">
                    <img class="w-5 h-5 mr-4 my-auto " src="<c:url value = "/public/images/attention.png"/>">
                    <p><messages:message code="validateYourIdentityExplanation"/></p>
                </div>
                <div class="mx-auto mt-8">
                    <a href="<c:url value="/kyc"/>"
                       class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto"><messages:message code="validateYourIdentity"/></a>
                </div>
            </c:otherwise>
        </c:choose>

        <%--        <div class="my-5">--%>
        <%--            <jsp:include page="../../components/seller/sellerChatCard.jsp"/>--%>
        <%--        </div>--%>
    </div>

    <!-- Middle Panel: trade -->

    <div class="flex flex-col h-full mr-20 w-3/5">

        <div class="shadow-xl w-full h-1/8 mb-4 flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] justify-start">
            <h1 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message
                    code="offersCreated"/></h1>
        </div>


        <div class="flex flex-wrap h-full mr-20 w-full">
            <c:forEach var="offer" items="${offerList}">

                <div class="flex flex-row ml-[35%]">
                    <div class="bg-nyellow mr-2 p-2 rounded-t-xl shadow" id="tab-${offer.id}-PENDING" onclick="showPending(${offer.id})">Pendientes</div>

                    <div class="bg-ngreen/[0.6]  text-gray-500 mr-2 p-2 rounded-t-xl shadow" id="tab-${offer.id}-ACCEPTED" onclick="showAccepted(${offer.id})">Aceptadas</div>
                    <div class="bg-nred/[0.6]  text-gray-500 mr-2  p-2 rounded-t-xl shadow" id="tab-${offer.id}-REJECTED" onclick="showRejected(${offer.id})">Rechazadas</div>

                    <div class="bg-gray-500/[0.6]  text-gray-500 p-2 mr-2 rounded-t-xl shadow" id="tab-${offer.id}-SOLD" onclick="showSold(${offer.id})">Finalizadas</div>
                </div>
                <div class="flex flex-col w-full mb-5">
                <div class="flex flex-row mx-5">
                        <%--    Tarjeta de anuncio--%>
                    <div class="shadow-xl flex flex-col rounded-lg px-7 bg-[#FAFCFF] z-20 justify-center items-center content-start w-[300px] ">

                        <div class="flex flex-col items-center mt-5 mb-2">
                            <h1 class="text-2xl font-bold font-sans"><messages:message code="offer"/><c:out value="#${offer.id}"/></h1>
                        </div>
                        <span class="text-center"><messages:message code="price"/></span>
                        <div class="flex flex-row ">
                            <h1 class="text-lg font-bold font-sans text-center"><fmt:formatNumber type="number"
                                                                                      maxFractionDigits="2"
                                                                                      value="${offer.askingPrice}"/>
                                ARS </h1>
                            <p class="my-auto mx-2"><messages:message code="for"/></p>
                            <h1 class="text-xl font-sans font-semibold my-auto"><c:out
                                    value="${offer.crypto.code}"/></h1>
                            <img src="<c:url value="/public/images/${offer.crypto.code}.png"/>"
                                 alt="<c:out value="${offer.crypto.code}"/>" class="w-5 h-5 mx-2 my-auto"/>
                        </div>

                        <div class="flex flex-col justify-start mb-3">
                            <div class="flex flex-col font-sans mt-3">
                                <p><c:out value="Min: ${offer.minQuantity}"/><c:out value=" ${offer.crypto.code}"/></p>
                            </div>
                            <div class="flex flex-col font-sans mt-3">
                                <p><c:out value="Max: ${offer.maxQuantity}"/><c:out value=" ${offer.crypto.code}"/></p>
                            </div>
                            <div class="flex flex-col font-sans mt-3">
                                <p><messages:message code="date"/> <c:out value="${offer.date.toLocalDate()}"/></p>
                            </div>
                            <div class="flex flex-col font-sans mt-3">
                                <p><messages:message code="offerLocation"/>:
                                    <c:if test="${empty offer.location}">
                                        <messages:message code="unknown"/>
                                    </c:if>
                                    <c:if test="${!(empty offer.location)}">
                                        <c:out value="${offer.location}"/>
                                    </c:if>
                                </p>
                            </div>
                        </div>


                        <div class="flex flex-row mx-auto mt-2 ">
                            <a href="<c:url value="/modify/${offer.id}"/>"
                               class="flex mr-5">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="#2E3440" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                                </svg>
                            </a>

                            <form action="<c:url value="/delete/${offer.id}"/>" method="post">
                                <button class="flex">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="#2E3440" stroke-width="2">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                    </svg>
                                </button>
                            </form>
                        </div>
                    </div>

                    <div id="offerId-<c:out value="${offer.id}" />"
                         class="flex flex-row shadow-xl rounded-lg bg-gray-100 -ml-2 z-10 p-5 overflow-x-scroll overflow-y-hidden w-full">
                            <h2 id="noResults-${offer.id}" class="hidden text-center text-3xl font-semibold font-sans text-polar my-auto mx-auto"><messages:message
                                    code="noSellingProposalReceived"/></h2>
                        <c:forEach var="trade" items="${offer.associatedTrades}">
                            <div name="trade-${offer.id}-${trade.status}" class="hidden bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mx-5 ">

                                <div class="flex font-sans h-fit w-full mt-5">
                                    <c:if test="${trade.status == 'PENDING' }">
                                        <div class="bg-nyellow  w-full text-white  text-center p-2"><messages:message
                                                code="pending"/></div>
                                    </c:if>

                                    <c:if test="${trade.status == 'REJECTED' }">
                                        <div class="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message
                                                code="rejected"/></div>
                                    </c:if>

                                    <c:if test="${trade.status == 'ACCEPTED' }">
                                        <div class="bg-ngreen w-full text-white text-center p-2"><messages:message
                                                code="accepted"/></div>
                                    </c:if>

                                    <c:if test="${trade.status == 'SOLD' }">
                                        <div class="bg-gray-400 w-full text-white text-center p-2"><messages:message
                                                code="sold"/></div>
                                    </c:if>
                                </div>

                                <div class="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                                    <h1 class="mx-auto">
                                        <fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity/offer.askingPrice}"/>
                                        <c:out value=" ${offer.crypto.code}"/> ⟶ <c:out
                                            value="${trade.quantity} "/>ARS</h1>
                                </div>

                                <c:if test="${!(trade.status =='SOLD')}">
                                    <div class="flex flex-col">
                                        <div class="flex">
                                            <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>
                                            <h1 class="font-sans font-semibold"><c:out value="${trade.buyerUsername}"/></h1>
                                        </div>
                                        <div class="flex">
                                            <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
                                            <h1 class="font-sans font-semibold"><c:out value="${trade.user.email}"/></h1>
                                        </div>
                                        <div class="flex">
                                            <h1 class="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>
                                            <h1 class="font-sans font-semibold"><c:out
                                                    value="${trade.user.phoneNumber}"/></h1>
                                        </div>
                                        <div class="flex">
                                            <h1 class="font-sans mr-2"><messages:message code="rating"/>:</h1>
                                            <h1 class="font-sans font-semibold"><c:out value="${trade.user.rating}"/></h1>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${(trade.status =='SOLD')}">
                                    <a class="mx-auto bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/receiptDescription/${trade.tradeId}"/>">
                                        <messages:message code="help"/>
                                    </a>
                                </c:if>

                            <%--                            CASE - PENDING--%>
                                <c:if test="${trade.status == 'PENDING'}">
                                    <c:url value="/seller/changeStatus" var="postUrl"/>
                                    <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post"
                                               cssClass="flex justify-center mx-auto my-3">
                                        <form:hidden path="newStatus" id="newStatus-${trade.tradeId}"
                                                     value="${trade.status}"/>
                                        <form:hidden path="tradeId" value="${trade.tradeId}"/>

                                        <button onclick="updateStatus('REJECTED', ${trade.tradeId})" type="submit"
                                                class="bg-red-400 text-white p-3  rounded-md font-sans mr-4">
                                            <messages:message
                                                    code="rejectTrade"/></button>
                                        <button onclick="updateStatus('ACCEPTED', ${trade.tradeId})" type="submit"
                                                class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message
                                                code="acceptTrade"/></button>
                                    </form:form>
                                    <div class="flex flex-row w-full justify-end">
                                        <div class=" flex flex-row w-[80px] bg-frostl align-middle my-auto font-sans rounded-full bg-frost" >
                                            <c:if test="${trade.qUnseenMessagesSeller > 0}">
                                                <img class="my-auto ml-3 mr-4 h-5 w-5" src="<c:url value="/public/images/msg.png"/>"/>
                                                <div class="align-middle"><c:out value="${trade.qUnseenMessagesSeller}"/></div>
                                            </c:if>
                                        </div>
                                        <a  href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>" class="mx-2 rounded-full my-auto">
                                                <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>
                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 mx-auto" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
                                            </svg>
                                        </a>
                                    </div>



                                </c:if>

                                    <%--                            CASE - ACCEPTED--%>
                                <c:if test="${trade.status == 'ACCEPTED' }">
                                    <c:url value="/closeTrade" var="formUrl"/>

                                    <form:form modelAttribute="soldTradeForm" action="${formUrl}" method="post"
                                               cssClass="flex justify-center mx-auto my-3">
                                        <form:hidden path="offerId" value="${trade.offer.id}"/>
                                        <form:hidden path="trade" value="${trade.tradeId}"/>
                                        <button type="submit"
                                                class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">
                                            <messages:message code="soldTrade"/></button>

                                    </form:form>

                                    <div class="flex flex-row w-full justify-end">
                                        <div class=" flex flex-row w-[80px] bg-frostl align-middle my-auto font-sans rounded-full bg-frost" >
                                            <c:if test="${trade.qUnseenMessagesSeller > 0}">
                                                <img class="my-auto ml-3 mr-4 h-5 w-5" src="<c:url value="/public/images/msg.png"/>"/>
                                                <div class="align-middle"><c:out value="${trade.qUnseenMessagesSeller}"/></div>
                                            </c:if>
                                        </div>
                                        <a  href="<c:url value="${'/chat?tradeId='.concat(trade.tradeId)}"/>" class="mx-2 rounded-full my-auto">
                                                <%--                                        <span><messages:message code="chatWithBuyer"/> </span>--%>
                                            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 mx-auto" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
                                            </svg>
                                        </a>
                                    </div>
                                </c:if>

                                    <%--                            CASE - REJECTED--%>
                                <c:if test="${!(trade.status =='ACCEPTED') && !(trade.status == 'PENDING')}">
                                    <div class="flex h-2/5 my-2"></div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="flex flex-col w-10 -ml-5 h-full justify-center"
                         onClick="toggle(<c:out value="${offer.id}" />) ">
                            <%--                    <div class="flex rotate-90 whitespace-nowrap">hola mundo</div>--%>
                                    <%--                                <div class="flex bg-gray-400 shadow-xl rounded-lg h-5/6 hover:bg-gray-300 hover:-mr-7 my-auto"></div>--%>

<%--                                <c:if test="${offer.status.toString() == 'PSE'}">--%>
                                    <div class="flex bg-gray-400 shadow-xl rounded-lg h-5/6 my-auto">
                                    </div>
<%--                                </c:if>--%>
<%--                                <c:if test="${offer.status.toString() == 'APR'}">--%>
<%--                                    <div class="flex bg-ngreen shadow-xl rounded-lg h-5/6 my-auto">--%>
<%--                                    </div>--%>
<%--                                </c:if>--%>
                            </div>
                </div>
                </div>
            </c:forEach>
            <div class="mx-auto">

                <c:if test="${noSellingTrades}">
                    <a href="<c:url value="/seller/upload"/>"
                       class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message
                            code="uploadAdvertisement"/></a>
                </c:if>
            </div>
                    <c:if test="${!noSellingTrades}">
                        <div class="flex flex-col mt-3 mx-auto">
                            <% request.setCharacterEncoding("utf-8"); %>
                            <jsp:include page="../../components/paginator.jsp">
                                <jsp:param name="activePage" value="${activePage}"/>
                                <jsp:param name="pages" value="${pages}"/>
                                <jsp:param name="baseUrl" value="/seller/"/>
                            </jsp:include>
                            <h1 class="mx-auto text-gray-400 mx-auto mt-3"><messages:message
                                    code="totalPageAmount"/>: ${pages}</h1>
                        </div>
                    </c:if>


            <!-- Right Panel: crypto dashboard -->

            <%--    <div class="flex flex-col h-full mr-10 w-1/5">--%>
            <%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
            <%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
            <%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
            <%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
            <%--    </div>--%>
</body>


</html>


<script>
    function toggle(id){
        // if (document.getElementById(id).classList.contains("hidden")) {
        //     document.getElementById(id).classList.remove("hidden");
        // } else {
        //     document.getElementById(id).classList.add("hidden");
        // }
    }
    function updateStatus( status, tradeId) {
        document.getElementById('newStatus-'+tradeId).setAttribute('value',status)
    }
    window.onload= function filterPending() {

        let pendingTabs= document.querySelectorAll("[id$=PENDING]")
        console.log(pendingTabs)
        pendingTabs.forEach(function (tab) {
            tab.click();
        })

    }
</script>

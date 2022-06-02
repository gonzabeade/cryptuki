<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../../components/buyer/buyerHeader.jsp"/>
<div class="flex flex-row h-full w-full mt-7 justify-around">
    <div class="container w-1/2 h-4/5 bg-[#FAFCFF] border-gray-200">
        <div class=" border rounded">
            <div>
                <div class="w-full">
                    <div class="relative flex items-center p-3 border-b border-gray-300">
                        <img class="object-cover w-10 h-10 rounded-full"
                             src="<c:url value="/profilepic/${otherUser.username.get()}"/>" alt="username" />
                        <span class="block ml-2 font-bold text-gray-600"><c:out value="${otherUser.username.get()}"/></span>
    <%--                    Online green button, checkLastLogin--%>
                        <c:if test="${true}">
                            <span class="absolute w-3 h-3 bg-green-600 rounded-full left-10 top-3"></span>
                        </c:if>
                        <h1 class="w-full text-right text-l font-sans font-bold left-">Órden de compra #<c:out value="${trade.tradeId}"></c:out></h1>
                    </div>
                    <div class="relative w-full p-6 overflow-y-auto h-[40rem]">

                        <ul class="space-y-2">
                            <c:forEach items="${trade.messageCollection}" var="message">
                                <c:choose>
                                    <c:when test="${message.sender == otherUser.id}">
                                        <jsp:include page="../../components/chat/leftMessage.jsp">
                                            <jsp:param name="msg" value="${message.message}"/>
                                        </jsp:include>
                                    </c:when>
                                    <c:otherwise>
                                        <jsp:include page="../../components/chat/rightMessage.jsp">
                                            <jsp:param name="msg" value="${message.message}"/>
                                        </jsp:include>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>

                    </div>

                    <div class="flex items-center justify-between w-full p-3 border-t border-gray-300">

                        <c:url value="/chat/send" var="postUrl"/>
                        <form:form modelAttribute="messageForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">
                            <input type="text" placeholder="Message"
                                   class="block w-full py-2 pl-4 mx-3 bg-gray-100 rounded-full outline-none focus:text-gray-700"
                                   name="message" required />
                            <form:input path="tradeId" type="hidden" value="${trade.tradeId}"/>
                            <form:input path="userId" type="hidden" value="${24}"/>


                            <button type="submit">
                                <svg class="w-5 h-5 text-gray-500 origin-center transform rotate-90" xmlns="http://www.w3.org/2000/svg"
                                     viewBox="0 0 20 20" fill="currentColor">
                                    <path
                                            d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z" />
                                </svg>
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%--    Tarjeta con informacion del trade --%>
    <div class="w-1/3">
        <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between mx-auto mb-3">
            Información sobre la órden de compra
        </div>
        <div class="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mx-auto mb-12 ">
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

<%--            &lt;%&ndash;                            CASE - PENDING&ndash;%&gt;--%>
<%--            <c:if test="${trade.status == 'PENDING'}">--%>
<%--                <c:url value="/seller/changeStatus" var="postUrl"/>--%>
<%--                <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post"--%>
<%--                           cssClass="flex justify-center mx-auto my-3">--%>
<%--                    <form:hidden path="newStatus" id="newStatus-${trade.tradeId}"--%>
<%--                                 value="${trade.status}"/>--%>
<%--                    <form:hidden path="tradeId" value="${trade.tradeId}"/>--%>

<%--                    <button onclick="updateStatus('REJECTED', ${trade.tradeId})" type="submit"--%>
<%--                            class="bg-red-400 text-white p-3  rounded-md font-sans mr-4">--%>
<%--                        <messages:message--%>
<%--                                code="rejectTrade"/></button>--%>
<%--                    <button onclick="updateStatus('ACCEPTED', ${trade.tradeId})" type="submit"--%>
<%--                            class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message--%>
<%--                            code="acceptTrade"/></button>--%>
<%--                </form:form>--%>
<%--            </c:if>--%>

<%--            &lt;%&ndash;                            CASE - ACCEPTED&ndash;%&gt;--%>
<%--            <c:if test="${trade.status == 'ACCEPTED' }">--%>
<%--                <c:url value="/closeTrade" var="formUrl"/>--%>
<%--                <form:form modelAttribute="soldTradeForm" action="${formUrl}" method="post"--%>
<%--                           cssClass="flex justify-center mx-auto my-3">--%>
<%--                    <form:hidden path="offerId" value="${offer.id}"/>--%>
<%--                    <form:hidden path="trade" value="${trade.tradeId}"/>--%>
<%--                    <button type="submit"--%>
<%--                            class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">--%>
<%--                        <messages:message code="soldTrade"/></button>--%>
<%--                </form:form>--%>
<%--            </c:if>--%>

<%--            &lt;%&ndash;                            CASE - REJECTED&ndash;%&gt;--%>
<%--            <c:if test="${!(trade.status =='ACCEPTED') && !(trade.status == 'PENDING')}">--%>
<%--                <div class="flex h-2/5 my-2"></div>--%>
<%--            </c:if>--%>
        </div>

        <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between mx-auto mb-5">
            Otras órdenes de compra de este comprador
        </div>
        <div class="flex flex-wrap justify-center">
            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2">
                Ejemplo <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/></div>
            </div>
            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2 ">
                Ejemplo <div class="bg-nyellow w-full text-white text-center p-2"><messages:message code="pending"/></div>
            </div>
            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2 ">
                Ejemplo <div class="bg-nyellow w-full text-white text-center p-2"><messages:message code="pending"/></div>
            </div>
            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2">
                Ejemplo <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/></div>
            </div>
            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2">
                Ejemplo <div class="bg-nred w-full text-white text-center p-2"><messages:message code="rejected"/></div>
            </div>
        </div>
    </div>


</div>
</body>


</html>
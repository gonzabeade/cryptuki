<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication property="name" var="username"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mx-5">

    <div class="flex font-sans h-fit w-full mt-5">
        <c:if test="${param.tradeStatus == 'PENDING' }">
            <div class="bg-nyellow  w-full text-white  text-center p-2"><messages:message code="pending"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'REJECTED' }">
            <div class="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message code="rejected"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'ACCEPTED' }">
            <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'SOLD' }">
            <div class="bg-gray-400 w-full text-white text-center p-2"><messages:message code="sold"/> </div>
        </c:if>
    </div>

<%--    <div class="flex flex font-sans justify-between my-1">--%>
<%--        <c:if test="${!(param.tradeStatus == 'SOLD')}">--%>
<%--            <h1 class="font-sans"><messages:message code="youWouldReceive"/>: </h1>--%>
<%--        </c:if>--%>
<%--        <c:if test="${param.tradeStatus == 'SOLD'}">--%>
<%--            <h1 class="font-sans"><messages:message code="youReceived"/>: </h1>--%>
<%--        </c:if>--%>
<%--        <div class="flex">--%>
<%--           <h3 class="ml-5 font-sans font-semibold"><c:out value="${param.quantity}"/>$ARS</h3>--%>
<%--        </div>--%>
<%--        <h1 class="font-sans"><messages:message code="onExchangeOf"/>: </h1>--%>
<%--        <div class="flex">--%>
<%--            <h1 class="font-sans font-semibold ml-5 mr-2"><fmt:formatNumber type="number" maxFractionDigits="6"--%>
<%--                                                                            value="${param.quantity/param.askedPrice}"/></h1>--%>
<%--            <h1 class="font-sans font-semibold"><c:out value="${param.cryptoCurrencyCode}"/></h1>--%>
<%--            &lt;%&ndash;            <img src="<c:url value="/public/images/${param.cryptoCurrencyCode}.png"/>" alt="<c:out value="${param.cryptoCurrencyCode}"/>" class="w-7 h-7 mx-auto"/>&ndash;%&gt;--%>
<%--        </div>--%>
<%--    </div>--%>
    <div class="flex flex font-sans my-3  w-52 mx-auto text-semibold">
        <h1 class="mx-auto"> 150 DAI ⟶ 3000<c:out value="${param.quantity}"/>ARS</h1>
    </div>

    <div class="flex flex-col">
        <div class="flex">
            <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>
<%--            <h1 class="font-sans font-semibold"><c:out value="${param.buyerUsername}"/></h1>--%>
            <h1 class="font-sans font-semibold">pepeViolento</h1>
        </div>
        <div class="flex">
            <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
<%--            <h1 class="font-sans font-semibold"><c:out value="${param.buyerMail}"/></h1>--%>
            <h1 class="font-sans font-semibold">dedeumarcos@gmail.com</h1>
        </div>
        <div class="flex">
            <h1 class="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>
            <h1 class="font-sans font-semibold">1157356243</h1>
        </div>
        <div class="flex">
            <h1 class="font-sans mr-2"><messages:message code="rating"/>:</h1>
            <h1 class="font-sans font-semibold">4.5</h1>
        </div>
    </div>


    <c:if test="${param.tradeStatus.equals('PENDING')}">
        <c:url value="/seller/changeStatus" var="postUrl"/>
        <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post" cssClass="flex justify-center mx-auto my-3">
            <form:hidden path="newStatus"  id="newStatus-${param.tradeId}" value="${param.tradeStatus}"/>
            <form:hidden path="tradeId" value="${param.tradeId}"/>
            <button onclick="updateStatus('REJECTED', ${param.tradeId})" type="submit"
                    class="bg-red-400 text-white p-3  rounded-md font-sans mr-4"><messages:message
                    code="rejectTrade"/></button>
            <button onclick="updateStatus('ACCEPTED', ${param.tradeId})" type="submit"
                    class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message
                    code="acceptTrade"/></button>
        </form:form>
    </c:if>

    <c:if test="${param.tradeStatus.equals('ACCEPTED')}">
        <c:url value="/closeTrade" var="formUrl"/>
        <form:form modelAttribute="soldTradeForm" action="${formUrl}" method="post" cssClass="flex justify-center mx-auto my-3" >
            <form:hidden path="offerId" value="${param.offerId}"/>
            <form:hidden path="trade" value="${param.tradeId}"/>
            <button type="submit"
                    class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">
                <messages:message code="soldTrade"/></button>
        </form:form>
    </c:if>
    <c:if test="${!(param.tradeStatus.equals('ACCEPTED')) && !(param.tradeStatus.equals('PENDING'))}">
        <div class="flex h-2/5 my-2"></div>
    </c:if>
</div>



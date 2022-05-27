<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication property="name" var="username"/>
<div class="shadow-xl flex rounded-lg justify-between py-5 px-24 bg-[#FAFCFF] mt-3">
    <div class="mr-10 my-auto">
        <c:if test="${username == param.buyerUsername}">
            <div class="bg-ngreen w-20 text-white  text-center p-2">
                <messages:message code="buy"/>
            </div>
        </c:if>
        <c:if test="${username == param.sellerUsername}">
            <div class="bg-nred w-20 text-white  text-center p-2">
                <messages:message code="sell"/>
            </div>
        </c:if>
    </div>

    <div class=" mr-10 font-sans justify-center items-center my-auto">
        <c:if test="${username ==  param.buyerUsername }">
            <h1 class="font-sans"><messages:message code="youBoughtFor"/>: </h1>
        </c:if>
        <c:if test="${username == param.sellerUsername}">
            <h1 class="font-sans"><messages:message code="youSoldFor"/>: </h1>
        </c:if>
        <h3 class="font-sans font-semibold"><c:out value="${param.quantity}"/>$AR</h3>
    </div>

    <div class="flex flex-col font-sans mr-10 my-auto">
        <div class="flex flex-col mx-auto">
            <img src="<c:url value="/public/images/${param.cryptoCurrencyCode}.png"/>" alt="<c:out value="${param.cryptoCurrencyCode}"/>" class="w-5 h-5 mx-auto"/>
            <h1 class="font-sans font-semibold"><c:out value="${param.cryptoCurrencyCode}"/></h1>
        </div>
        <h1 class="text-xl mx-auto font-bold font-sans text-center"><fmt:formatNumber type="number" maxFractionDigits="6" value="${param.quantity/param.askedPrice}"/></h1>
    </div>

    <div class="flex flex-col font-sans mr-10 my-auto">
        <c:if test="${param.tradeStatus == 'PENDING' }">
            <div class="bg-amber-300 w-30 text-white  text-center p-2"><messages:message code="pending"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'REJECTED' }">
            <div class="bg-red-300 w-30 text-white  text-center p-2"><messages:message code="rejected"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'ACCEPTED' }">
            <div class="bg-lime-300 w-30 text-white  text-center p-2"><messages:message code="accepted"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'SOLD' }">
            <div class="bg-ngreen w-30 text-white  text-center p-2"><messages:message code="sold"/> </div>
        </c:if>
    </div>

    <div class="flex my-auto">
        <a class="bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/receiptDescription/${param.tradeId}"/>">
        <messages:message code="help"/>
        </a>
    </div>
</div>


<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication property="name" var="username"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="shadow-xl flex rounded-lg  py-5 px-12 bg-[#FAFCFF] mt-3 justify-start">
    <div class="w-3/4 flex flex-row">
        <div class="flex flex-col font-sans my-auto mr-5">
            <c:if test="${param.tradeStatus == 'PENDING' }">
                <div class="bg-nyellow  w-40 text-white  text-center p-2"><messages:message code="pending"/> </div>
            </c:if>

            <c:if test="${param.tradeStatus == 'REJECTED' }">
                <div class="bg-nred/[0.6] w-40 text-white  text-center p-2"><messages:message code="rejected"/> </div>
            </c:if>

            <c:if test="${param.tradeStatus == 'ACCEPTED' }">
                <div class="bg-ngreen w-40 text-white text-center p-2"><messages:message code="accepted"/> </div>
            </c:if>

            <c:if test="${param.tradeStatus == 'SOLD' }">
                <div class="bg-gray-400 w-40 text-white text-center p-2"><messages:message code="sold"/> </div>
            </c:if>
            <c:if test="${param.tradeStatus == 'DELETED' }">
                <div class="bg-blue-400 w-40 text-white text-center p-2"><messages:message code="sold"/> </div>
            </c:if>
        </div>



        <div class=" flex flex-col font-sans justify-center mr-5">
            <c:if test="${param.tradeStatus == 'SOLD'}">
                <h1 class="font-sans"><messages:message code="youBoughtFor"/>: </h1>
            </c:if>
            <c:if test="${! (param.tradeStatus=='SOLD')}">
                <h1 class="font-sans"><messages:message code="youWouldPay"/>: </h1>
            </c:if>
            <h3 class="font-sans font-semibold"><c:out value="${param.quantity}"/>$AR</h3>
        </div>

        <div class="flex flex-col font-sans justify-center ">
            <h1 class="font-sans"><messages:message code="onExchangeOf"/>: </h1>
            <div class="flex">
                <h1 class="text-xl font-sans font-bold"><fmt:formatNumber type="number" maxFractionDigits="6" value="${param.quantity/param.askedPrice}"/></h1>
                <h1 class="text-xl font-sans font-bold mx-2"><c:out value="${param.cryptoCurrencyCode}"/></h1>
                <img src="<c:url value="/public/images/${param.cryptoCurrencyCode}.png"/>" alt="<c:out value="${param.cryptoCurrencyCode}"/>" class="w-7 h-7 mx-auto"/>
            </div>
        </div>

    </div>

    <div class="w-1/4 flex flex-row">
        <div class="flex my-auto ml-1">
            <c:if test="${! (param.tradeStatus == 'SOLD' || param.tradeStatus == 'REJECTED')}">
                <a class="bg-gray-200 text-polard hover:border-polard hover: border-2 p-2 h-16 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/trade?tradeId=${param.tradeId}"/>">
                    <messages:message code="resumeTrade"/>
                </a>
            </c:if>
            <c:if test="${ param.tradeStatus == 'SOLD'}">
                <a class="bg-gray-200 text-polard hover:border-polard hover: border-2 p-2 h-16 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/receiptDescription/${param.tradeId}"/>">
                    <messages:message code="help"/>
                </a>
            </c:if>
        </div>

        <div class="ml-4 flex flex-row  align-middle my-auto font-sans " >
            <c:if test="${param.unseenMessages != '0' && param.unseenMessages!=''}">
                <a href="<c:url value="/trade?tradeId=${param.tradeId}"/>" class="flex flex-row">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 mx-auto" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
                    </svg>
                    <div class=" text-frost align-middle bg-frost rounded-full w-2 h-2"></div>
                </a>
                <div class="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">
                    <p class="text-xs"><c:out value="${param.unseenMessages}"/></p>
                </div>

            </c:if>
        </div>
    </div>

</div>

<script>
    function updateStatus(newStatusName) {
        document.getElementById('newStatus').setAttribute('value',newStatusName)
    }
</script>


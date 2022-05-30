<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication property="name" var="username"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="shadow-xl flex rounded-lg justify-between py-5 px-12 bg-[#FAFCFF] mt-3 mx-10">

    <div class="flex flex-col font-sans my-auto">
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



    <div class=" flex flex-col font-sans justify-center">
            <c:if test="${param.tradeStatus == 'SOLD'}">
                <h1 class="font-sans"><messages:message code="youBoughtFor"/>: </h1>
            </c:if>
            <c:if test="${! (param.tradeStatus=='SOLD')}">
                <h1 class="font-sans"><messages:message code="youWouldPay"/>: </h1>
            </c:if>
        <h3 class="font-sans font-semibold"><c:out value="${param.quantity}"/>$AR</h3>
    </div>

    <div class="flex flex-col font-sans justify-center">
        <h1 class="font-sans"><messages:message code="onExchangeOf"/>: </h1>
        <div class="flex">
            <h1 class="text-xl font-sans font-bold"><fmt:formatNumber type="number" maxFractionDigits="6" value="${param.quantity/param.askedPrice}"/></h1>
            <h1 class="text-xl font-sans font-bold mx-2"><c:out value="${param.cryptoCurrencyCode}"/></h1>
            <img src="<c:url value="/public/images/${param.cryptoCurrencyCode}.png"/>" alt="<c:out value="${param.cryptoCurrencyCode}"/>" class="w-7 h-7 mx-auto"/>
        </div>
    </div>


    <div class="flex my-auto">
       <c:if test="${! (param.tradeStatus == 'SOLD')}">
           <a class="bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/trade?tradeId=${param.tradeId}"/>">
               <messages:message code="help"/>
           </a>
       </c:if>
       <c:if test="${ param.tradeStatus == 'SOLD'}">
           <a class="bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/receiptDescription/${param.tradeId}"/>">
                <messages:message code="help"/>
           </a>
       </c:if>
    </div>




</div>

<script>
    function updateStatus(newStatusName) {
        document.getElementById('newStatus').setAttribute('value',newStatusName)
    }
</script>


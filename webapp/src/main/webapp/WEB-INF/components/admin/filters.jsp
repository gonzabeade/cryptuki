<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<div class="flex flex-col px-10 w-1/3">
    <h1 class="font-sans text-2xl font-bold"><messages:message code="filters"/></h1>
    <div class="flex flex-col mt-7">
        <label for="fromDate" ><messages:message code="from"/></label>
        <input id="fromDate" type="date" class="p-1 rounded-lg" onchange="addQueryParam('fromDate')">
    </div>
    <div class="flex flex-col  mt-7">
        <label for="toDate"><messages:message code="until"/></label>
        <input  id="toDate"  type="date" class="p-1 rounded-lg" onchange="addQueryParam('toDate')">
    </div>
   <div class="flex flex-col  mt-7">
       <label for="offerId"><messages:message code="offerId"/></label>
       <input  id="offerId" type="number" class="p-1 rounded-lg" onchange="addQueryParam('offerId')">
   </div>
   <div class="flex flex-col  mt-7">
      <label  for="tradeId"><messages:message code="transactionId"/></label>
      <input type="number" class="p-1 rounded-lg" id="tradeId" onchange="addQueryParam('tradeId')">
   </div>

    <div class="flex flex-col mt-7">
        <label for="complainer"><messages:message code="username"/></label>
        <input type="text" class="p-1 rounded-lg" id="complainer" onchange="addQueryParam('complainer')">
    </div>
    <a id="link" href="<c:url value="${param.baseUrl}"/>" class="rounded-lg bg-frost text-white mt-7 p-3 text-center"><messages:message code="filter"/></a>
    <div class="flex flex-row justify-center mt-3">
        <button onclick="resetAllAdminFilters()" class="justify-start text-polard font-regular hidden" id="reset"><messages:message code="cleanFilters"/></button>
    </div>
</div>
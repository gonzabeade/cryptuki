<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex flex-col px-10 w-1/3">
    <h1 class="font-sans text-2xl font-bold">Filtros</h1>
    <div class="flex flex-col mt-7">
        <label for="fromDate" >Desde</label>
        <input id="fromDate" type="date" class="p-1 rounded-lg" onchange="addQueryParam('fromDate')">
    </div>
    <div class="flex flex-col  mt-7">
        <label for="toDate">Hasta</label>
        <input  id="toDate"  type="date" class="p-1 rounded-lg" onchange="addQueryParam('toDate')">
    </div>
   <div class="flex flex-col  mt-7">
       <label for="offerId">Id de la oferta</label>
       <input  id="offerId" type="number" class="p-1 rounded-lg" onchange="addQueryParam('offerId')">
   </div>
   <div class="flex flex-col  mt-7">
      <label  for="tradeId"> Id de la transacción</label>
      <input type="number" class="p-1 rounded-lg" id="tradeId" onchange="addQueryParam('tradeId')">
   </div>

    <div class="flex flex-col mt-7">
        <label for="username"> Username</label>
        <input type="text" class="p-1 rounded-lg" id="username" onchange="addQueryParam('username')">
    </div>
    <a id="link" href="<c:url value="${param.baseUrl}"/>" class="rounded-lg bg-frost text-white mt-7 p-3 text-center"> Filtrar</a>

</div>
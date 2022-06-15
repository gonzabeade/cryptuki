<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var = "url" value = "${fn:toLowerCase(param.status)}"/>
<c:url value="/seller/trade/${url}" var="getUrl"/>
<form:form action="${getUrl}" method="get" modelAttribute="tradeFilterForm">
  <form:input class="hidden" path="page" value="${param.page}"></form:input>
  <form:input class="hidden" path="focusOnOfferId" value="${param.offerId}"></form:input>
  <c:set var="highlight" scope="request" value="${param.offerId == param.focusOfferId}"/>
  <c:choose>
    <c:when test="${highlight}">
      <c:set var="border" scope="request" value="border-frostl"/>
    </c:when>
    <c:otherwise>
      <c:set var="border" scope="request" value="border-[#FAFCFF]"/>
    </c:otherwise>
  </c:choose>
  <button type="submit" class="w-[280px] h-[260px] bg-[#FAFCFF]  hover:border-frostd border-2 <c:url value="${border}"/> p-4 cursor-pointer shadow-xl flex flex-col rounded-lg justify-start mx-3">
    <div class="flex flex-row font-sans w-full justify-around">
      <div class="flex flex-col font-sans text-semibold rounded-lg bg-gray-200 border-frostl py-2 px-3">
        <h1 class=""><c:out value="${param.quantityInCrypto}"/> <c:out value="${param.crypto}"/></h1>
        <h1 class="">⤷ <c:out value="${param.quantityInArs}"/> ARS</h1>
      </div>
    </div>
    <div class="flex flex-col mt-3">
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>: </h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerUsername}"/>
        </h1>
      </div>
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerEmail}"/></h1>
      </div>
      <div class="flex">
        <h1 class="font-sans mr-2">Teléfono: </h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerPhoneNumber}"/></h1>
      </div>
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="rating"/>:</h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerRating}"/></h1>
      </div>
    </div>

<%--    HACER LOS CIFS--%>
    <div class="flex flex-row font-sans w-full justify-around mt-4">
      <div class="flex flex-col w-1/2 font-sans text-semibold rounded-lg bg-ngreen border-polar hover:bg-[#B0C0A0] border-2 py-1 px-3 -mt-1 align-middle mx-3">
        Aceptar
      </div>
      <div class="flex flex-col w-1/2 font-sans text-semibold rounded-lg bg-nred border-polar border-2 hover:bg-[#D0616F] py-1 px-3 -mt-1 align-middle mx-3">
        Rechazar
      </div>
    </div>
  </button>
</form:form>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="/seller/trade/${param.status}" var="getUrl"/>
<form:form action="${getUrl}" method="get" modelAttribute="tradeFilterForm">
  <form:input class="hidden" path="page" value="${3}"></form:input>
  <form:input class="hidden" path="focusOnOfferId" value="${4}"></form:input>
  <button type="submit" class="w-[250px] h-[200px] hover:w-[260px] hover:h-[210px] bg-[#FAFCFF] p-4 cursor-pointer shadow-xl flex flex-col rounded-lg justify-between mx-3">
    <div class="flex flex font-sans w-52 mx-auto text-semibold">
      <h1 class="mx-auto"><c:out value="${param.quantityInCrypto}"/> <c:out value="${param.crypto}"/> ⟶ <c:out value="${param.quantityInArs}"/> ARS</h1>
    </div>
    <div class="flex flex-col">
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerUsername}"/></h1>
      </div>
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerMail}"/></h1>
      </div>
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerPhoneNumber}"/></h1>
      </div>
      <div class="flex">
        <h1 class="font-sans mr-2"><messages:message code="rating"/>:</h1>
        <h1 class="font-sans font-semibold"><c:out value="${param.buyerRating}"/></h1>
      </div>
    </div>
  </button>
</form:form>
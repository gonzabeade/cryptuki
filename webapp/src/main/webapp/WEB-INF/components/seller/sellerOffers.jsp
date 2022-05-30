<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<div class="flex flex-col">
  <div class="flex justify-between mt-10 mb-5">
    <div class="flex mt-10 mb-5 ml-20">
      <c:if test="${!noOffers}">
        <h2 class="text-center text-3xl font-semibold font-sans text-polar my-auto"><messages:message code="yourOffers"/></h2>
      </c:if>
    </div>
  </div>
  <div  class="flex flex-col justify-center">
    <c:forEach var="offer" items="${offerList}">
      <li class="list-none mx-20">
        <% request.setCharacterEncoding("utf-8"); %>
        <c:set  var="accepted_payments" value="${offer.paymentMethods}" scope="request"/>
        <c:set  var="owner" value="${offer.seller.username.isPresent() ? offer.seller.username.get() : offer.seller.email}" scope="request"/>

        <jsp:include page="../../components/card.jsp">
          <jsp:param name="currency" value="ETH"/>
          <jsp:param name="owner" value="gonzabeade"/>
          <jsp:param name="asking_price" value="299"/>
          <jsp:param name="trades" value="100"/>
          <jsp:param name="offerId" value="ABCH"/>
          <jsp:param name="minCoinAmount" value="100"/>
          <jsp:param name="maxCoinAmount" value="200"/>
          <jsp:param name="userEmail" value="gonzabeade@gmail.com"/>
          <jsp:param name="lastLogin" value="${offer.seller.lastLogin.toLocalDate()}"/>
          <jsp:param name="lastLoginTime" value="${offer.seller.lastLogin.toLocalTime().hour}:${offer.seller.lastLogin.toLocalTime().minute}"/>
          <jsp:param name="minutesSinceLastLogin" value="${offer.seller.minutesSinceLastLogin}"/>
          <jsp:param name="rating" value="${offer.seller.rating}"/>

        </jsp:include>
      </li>
    </c:forEach>
  </div>

  <c:if test="${noOffers}">
    <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noOffersUploaded"/></h2>
    <a href="<c:url value="/"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message code="startSelling"/></a>
  </c:if>

  <c:if test="${!noOffers}">
    <div class="flex flex-col">
      <% request.setCharacterEncoding("utf-8"); %>
      <jsp:include page="../../components/paginator.jsp">
        <jsp:param name="activePage" value="${activePage}"/>
        <jsp:param name="pages" value="${pages}"/>
        <jsp:param name="baseUrl" value="/myoffers"/>
      </jsp:include>
      <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: ${pages}</h1>
    </div>
  </c:if>

</div>



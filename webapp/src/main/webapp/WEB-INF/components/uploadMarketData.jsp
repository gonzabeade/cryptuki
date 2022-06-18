<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.util.Collection"/>
<div class="p-5 max-w-md  bg-[#FAFCFF] rounded-lg border shadow-md mx-5 mt-5">
  <div class="flex justify-between items-center">
    <h5 class="text-xl  text-polard font-bold leading-none my-auto" ><messages:message code="marketPrice"/></h5>
  </div>
  <div class="flow-root" id="allPossibleCripto">
    <ul role="list" class="divide-y divide-gray-200 ">
      <c:forEach var="coin" items="${cryptocurrencies}">
        <jsp:include page="cryptoCardData.jsp">
          <jsp:param name="commercialName" value="${coin.commercialName}"/>
          <jsp:param name="code" value="${coin.code}"/>
        </jsp:include>
      </c:forEach>

    </ul>
  </div>
</div>
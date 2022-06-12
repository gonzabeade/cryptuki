<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="flex flex-col mx-auto">
    <div class="flex  flex-col mx-auto text-center mb-10 w-full">
        <h1 class="font-sans font-bold text-3xl mt-3"><messages:message code="sellersInfo"/></h1>
        <div class="mt-4 bg-stormd/[0.9] border-2 border-polard rounded-lg mx-auto px-20">
            <h3 class="font-sans text-2xl my-3 text-polard font-semibold"><c:out value="${param.email}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="phoneNumber"/>: </b><c:out value="${param.phone}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="tradeQuantity"/>: </b><c:out value="${param.trades}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="lastLogin"/>: </b><messages:message code="${param.lastLogin}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="rating"/>: </b><fmt:formatNumber type="number" maxFractionDigits="2" value="${param.rating}"/> </h3>
        </div>
    </div>
    <c:if test="${! empty param.message}">
        <div class="flex  flex-col mx-auto text-center mb-10 w-full">
            <h1 class="font-sans font-bold text-3xl mt-3"><messages:message code="sellerMessage"/></h1>
            <div class="font-sans mx-32 text-center mt-3 justify-center"><c:out value="${param.message}"/></div>
        </div>
    </c:if>
</div>

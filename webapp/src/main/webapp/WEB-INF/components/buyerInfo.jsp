<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatNumber type="number" maxFractionDigits="0" value="${param.rating /2  == 0 ? 1: param.rating/2}" var="stars"/>

<div class="flex flex-col">
    <div class="flex  flex-col mx-auto text-center mb-10 w-full">
        <h1 class="font-sans font-bold text-3xl mt-3"><messages:message code="buyerInformation"/></h1>
        <div class="mt-4 bg-stormd/[0.9] border-2 border-polard rounded-lg mx-auto px-20">
            <h3 class="font-sans text-2xl my-3 text-polard font-semibold"><c:out value="${param.email}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="phoneNumber"/>: </b><c:out value="${param.phone}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="tradeQuantity"/>: </b><c:out value="${param.trades}"/></h3>
            <h3 class="font-sans my-3"><b><messages:message code="lastLogin"/>: </b><messages:message code="${param.lastLogin}"/></h3>
            <c:if test="${param.trades > 0 }">
            <div class="flex flex-row p-3">
                <h4 class="text-gray-400 font-sans"> <c:out value="${param.trades}"/> <messages:message code="trades"/> |
                    <messages:message code="rating"/>: </h4>
                <div class="my-auto ml-2">
                    <c:forEach begin="0" end="${stars-1}">
                        <span class="fa fa-star" style="color: orange"></span>
                    </c:forEach>
                    <c:forEach begin="${stars}" end="4">
                        <span class="fa fa-star" style="color: gray"></span>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        </div>
    </div>
</div>

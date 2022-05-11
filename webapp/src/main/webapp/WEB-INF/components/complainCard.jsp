<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<div class="shadow-xl flex  rounded-lg  m-5 p-7 bg-[#FAFCFF] mx-10">

    <div class="basis-1/4">
        <h1 class="font-sans"><messages:message code="status"></h1>
        <c:choose>
            <c:when test="${param.status}=='CLOSED'">
                <h3 class="font-bold font-sans"><messages:message code="closedComplaintState"/></h3>
            </c:when>
            <c:otherwise>
                <h3 class="font-bold font-sans"><messages:message code="openComplaintState"/></h3>
            </c:otherwise>

        </c:choose>
    </div>
    <div class="basis-1/4">
        <h1 class="font-sans"><messages:message code="message"/> </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.message}"/></h3>
    </div>
    <div class="basis-1/4">
        <h1 class="font-sans"><messages:message code="date"/></h1>
        <h3 class="font-bold font-sans"><c:out value="${param.date}"/></h3>
    </div>
    <c:if test="${param.status!='CLOSED'}">
        <div  class="flex basis-1/4 justify-center items-center">
            <a class="mx-36 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center" href="<c:url value="/receiptDescription/${param.tradeId}"/>" />
            <messages:message code="seeTransaction"/>
            </a>
        </div>
    </c:if>
</div>

</div>

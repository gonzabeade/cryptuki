<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<div class="shadow-xl flex  rounded-lg  m-5 p-7 bg-[#FAFCFF] mx-10">

    <div class="basis-1/4">
        <h1 class="font-sans">Estado: </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.status}"/></h3>
    </div>
    <div class="basis-1/4">
        <h1 class="font-sans">Mensaje: </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.message}"/></h3>
    </div>
    <div class="basis-1/4">
        <h1 class="font-sans">Fecha: </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.date}"/></h3>
    </div>
    <c:if test="${Integer.valueOf(param.tradeId) >= 0  && param.status!='CLOSED'}">
        <div  class="flex basis-1/4 justify-center items-center">
            <a class="mx-36 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center" href="<c:url value="/receiptDescription/${param.tradeId}"/>" />
            <messages:message code="seeTransaction"/>
            </a>
        </div>
    </c:if>
    <c:if test="${Integer.valueOf(param.tradeId) < 0}">
        <div  class="flex basis-1/4 justify-center items-center">
            <h1 class="font-sans"><messages:message code="noAssociatedTransaction"/></h1>
        </div>
    </c:if>
</div>

</div>

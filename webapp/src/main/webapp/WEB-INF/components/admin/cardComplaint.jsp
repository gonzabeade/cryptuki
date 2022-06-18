<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<div class="flex flex-col bg-white shadow-lg rounded-lg p-4 w-80 h-52 mx-3 my-3">
    <div class="flex flex-col">
        <div class="flex flex-col my-auto mx-7">
            <h1 class="font-sans font-polard text-xl font-semibold">
                <messages:message code="claim"/> <b><c:out value="${param.complainId}"/></b>
            </h1>
            <h3 class="text-gray-300"> <messages:message code="carriedOutOn"/> <c:out value="${param.date}"/></h3>
        </div>
        <div class="flex flex-col  my-auto mx-7">
            <h1 class="font-sans font-polard text-lg font-semibold">
                <messages:message code="user"/>:  <b><c:out value="${param.complainerUsername}"/></b>
            </h1>
            <h3 class="text-gray-600 w-60 overflow-y-hidden truncate" ><messages:message code="comment"/>: <c:out value="${param.complainerComments}"/></h3>
        </div>
    </div>
    <c:if test="${param.complainStatus != 'CLOSED'}">
    <div class="flex flex-row my-3 mx-auto">

            <c:if test="${param.complainStatus == 'PENDING'}">
                <a  href="<c:url value="/admin/complaint/${param.complainId}"/>" class=" text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto"><messages:message code="see"/></a>
            </c:if>
    </div>
    </c:if>

</div>
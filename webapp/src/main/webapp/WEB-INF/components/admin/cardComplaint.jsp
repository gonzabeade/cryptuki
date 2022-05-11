<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<div class="flex flex-row bg-white shadow-lg h-36 rounded-lg p-4 justify-start">
    <div class="flex flex-row basis-3/4">
        <div class="flex flex-col my-auto mx-7">
            <h1 class="font-sans font-polard text-2xl font-semibold">
                <messages:message code="claim"/> <b><c:out value="${param.complainId}"/></b>
            </h1>
            <h3 class="text-gray-300"> <messages:message code="carriedOutOn"/> <c:out value="${param.date}"/></h3>
        </div>
        <div class="flex flex-col h-28 my-auto mx-7">
            <h1 class="font-sans font-polard text-2xl font-semibold">
                <messages:message code="user"/>:  <b><c:out value="${param.complainerUsername}"/></b>
            </h1>
            <h3 class="text-gray-300 overflow-y-hidden" ><messages:message code="comment"/>: <c:out value="${param.complainerComments}"/></h3>
        </div>
    </div>
    <c:if test="${param.complainStatus != 'CLOSED'}">
    <div class="flex flex-row basis-1/4 my-auto">

            <c:if test="${param.complainStatus == 'PENDING'}">
                <a  href="<c:url value="/admin/complaint/${param.complainId}"/>" class=" w-24 text-center pb-6 px-7 pt-4 rounded-lg bg-stormd max-h-14 text-polard mx-auto"><messages:message code="see"/></a>
            </c:if>
            <c:if test="${param.complainStatus == 'ASSIGNED'}">
                <c:url value="/admin/unassign/${param.complainId}" var="postUrl"/>
                <form:form method="post" action="${postUrl}" cssClass="flex my-auto mx-3">
                <button type="submit" class="text-center pb-6 px-7 pt-4 rounded-lg bg-stormd max-h-14 text-polard mx-auto"><messages:message code="unassignMe"/><button/>
            </form:form>
            </c:if>
            <c:url value="/admin/selfassign/${param.complainId}" var="postUrl"/>
            <form:form method="post" action="${postUrl}" cssClass="flex my-auto mx-3">
            <button type="submit" class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 text-white w-32 text-center"><messages:message code="openClaim"/><button/>
            </form:form>
    </div>
    </c:if>

</div>
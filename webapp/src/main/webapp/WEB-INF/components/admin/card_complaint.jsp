<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="flex flex-row bg-white shadow-lg h-36 rounded-lg p-4 justify-start">
    <div class="flex flex-row basis-3/4">
        <div class="flex flex-col my-auto mx-7">
            <h1 class="font-sans font-polard text-2xl font-semibold">
                Reclamo <b><c:out value="${param.complainId}"/></b>
            </h1>
            <h3 class="text-gray-300"> Efectuado el <c:out value="${param.date}"/></h3>
        </div>
        <div class="flex flex-col my-auto mx-7">
            <h1 class="font-sans font-polard text-2xl font-semibold">
                Usuario:  <b><c:out value="${param.complainerUsername}"/></b>
            </h1>
            <h3 class="text-gray-300">Comentario: <c:out value="${param.complainerComments}"/></h3>
        </div>
    </div>
    <c:if test="${param.complainStatus != 'CLOSED'}">
    <div class="flex flex-row basis-1/4 my-auto">

            <c:if test="${param.complainStatus == 'PENDING'}">
                <a  href="<c:url value="/admin/complaint/${param.complainId}"/>" class=" w-24 text-center pb-6 px-7 pt-4 rounded-lg bg-stormd max-h-14 text-polard mx-auto min-w-20">Ver</a>
            </c:if>
            <c:url value="/admin/selfassign/${param.complainId}" var="postUrl"/>
            <form:form method="post" action="${postUrl}" cssClass="flex my-auto mx-3">
            <button type="submit" class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 text-white w-32 text-center">Atender<button/>
            </form:form>
    </div>
    </c:if>

</div>
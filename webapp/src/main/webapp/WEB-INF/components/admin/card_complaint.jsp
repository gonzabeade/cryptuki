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
    <div class="flex flex-row basis-1/4 my-auto">
        <a  href="<c:url value="/"/>" class="pb-6 px-7 pt-4 rounded-lg bg-stormd max-h-14 text-polard mr-5">Ver</a>
        <c:if test="${true}"> <!--if complaint status is not Assigned nor solved  -->

            <c:url value="/admin/selfassign/${param.complainId}" var="postUrl"/>
            <form:form method="post" action="${postUrl}" cssClass="flex my-auto mx-3">
                <button type="submit" class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 text-white">Atender<button/>
                    </form:form>

        </c:if>
    </div>

</div>
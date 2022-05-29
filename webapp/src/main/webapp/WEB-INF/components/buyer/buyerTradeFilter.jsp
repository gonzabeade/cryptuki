<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="border-gray-200 py-3 h-20"> <!--OK-->
    <div class="container mx-auto my-auto px-20 h-full">
            <ol class="flex justify-center mt-4">
                <li class="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
                    <div class="py-2 pr-4 pl-3 text-polar text-l font-bold">
                        <c:if test="${param.status != 'PENDING'}"> <a href="<c:url value="/buyer/?status=PENDING"/>">Pendientes</a> </c:if>
                        <c:if test="${param.status == 'PENDING'}"> <a class="decoration-frostdr underline underline-offset-8" href="<c:url value="/buyer/?status=PENDING"/>">Pendientes</a> </c:if>
                    </div>
                </li>
                <li class="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
                    <div class="py-2 pr-4 pl-3 text-polar text-l font-bold">
                        <c:if test="${param.status != 'ACCEPTED'}"> <a href="<c:url value="/buyer/?status=ACCEPTED"/>">Aceptadas</a> </c:if>
                        <c:if test="${param.status == 'ACCEPTED'}"> <a class="decoration-frostdr underline underline-offset-8" href="<c:url value="/buyer/?status=ACCEPTED"/>">Aceptadas</a> </c:if>
                    </div>
                </li>
                <li class="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
                    <div class="py-2 pr-4 pl-3 text-polar text-l font-bold">
                        <c:if test="${param.status != 'REJECTED'}"> <a href="<c:url value="/buyer/?status=REJECTED"/>">Rechazadas</a> </c:if>
                        <c:if test="${param.status == 'REJECTED'}"> <a class="decoration-frostdr underline underline-offset-8" href="<c:url value="/buyer/?status=REJECTED"/>">Rechazadas</a></c:if>
                    </div>
                </li>
                <li class="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
                    <div class="py-2 pr-4 pl-3 text-polar text-l font-bold">
                        <c:if test="${param.status != 'SOLD'}"> <a href="<c:url value="/buyer/?status=SOLD"/>">Finalizadas</a> </c:if>
                        <c:if test="${param.status == 'SOLD'}"> <a class="decoration-frostdr underline underline-offset-8" href="<c:url value="/buyer/?status=SOLD"/>">Finalizadas</a> </c:if>
                    </div>
                </li>


            </ol>
    </div>
</nav>

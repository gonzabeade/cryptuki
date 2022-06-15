<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="border-gray-200 py-3 h-20 my-4"> <!--OK-->
    <div class="container mx-auto my-auto px-20 h-full">
    <ol class="flex justify-center mt-4">
        <li class="bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/buyer/?status=PENDING"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar ${param.status=='PENDING'?'decoration-frostdr underline underline-offset-8':'text-l '}">Activas</p>
            </a>
        </li>
        <li class="bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/buyer/?status=ACCEPTED"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar ${param.status=='ACCEPTED'?'decoration-frostdr underline underline-offset-8':'text-l '}">Pausadas</p>
            </a>
        </li>
        <li class="bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/buyer/?status=REJECTED"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar ${param.status=='REJECTED'?'decoration-frostdr underline underline-offset-8':'text-l '}">Eliminadas</p>
            </a>
        </li>
        <li class="bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/buyer/?status=SOLD"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar ${param.status=='SOLD'?'decoration-frostdr underline underline-offset-8':'text-l '}">Agotadas</p>
            </a>
        </li>
    </ol>
    </div>
</nav>

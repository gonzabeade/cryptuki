<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="mx-auto h-full">
    <ol class="flex rounded-lg px-4 flex-col justify-center">
        <li class="my-2 bg-[#FAFCFF] rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/seller/"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar <c:out value="${empty param.status ?'decoration-frostdr underline underline-offset-8':'text-l '}" />">Todos</p>
            </a>
        </li>
        <li class="my-2 bg-ngreen rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/seller/active"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar <c:out value="${param.status=='APR'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">Activos</p>
            </a>
        </li>
        <li class="my-2 bg-nyellow rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/seller/paused"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar <c:out value="${param.status=='PSE'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">Pausados</p>
            </a>
        </li>
        <li class="my-2 bg-nred rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/seller/deleted"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar <c:out value="${param.status=='DEL'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">Eliminados</p>
            </a>
        </li>
        <li class="my-2 bg-gray-400 rounded-lg shadow-md p-1 mx-5 hover:-translate-y-1 hover:scale-110 duration-200">
            <a href="<c:url value="/seller/sold"/>">
                <p class="py-2 pr-4 pl-3 font-bold text-polar <c:out value="${param.status=='SOL'?'decoration-frostdr underline underline-offset-8':'text-l '}" />">Finalizados</p>
            </a>
        </li>

    </ol>
</div>

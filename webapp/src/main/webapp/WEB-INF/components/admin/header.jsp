<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="w-60 bg-frostdr h-full flex flex-col fixed">
    <a href="<c:url value="/admin"/>"/>
        <img src="<c:url value="/public/images/logo_white.png"/>" class="w-60 mx-auto mt-10 px-10">
    </a>

    <ol class="flex flex-col divide-y mt-10">
        <li></li>
        <a class="flex py-10 flex-col" href="<c:url value="/admin"/>">
        <li>
               <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="pendingClaims"/></h1>

        </li>
        </a>
        <a class="flex flex-col flex py-10" href="<c:url value="/admin/kyccheck"/>">
            <li>
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="1">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="kyccheck"/></h1>
            </li>
        </a>
        <a class="flex py-10 flex-col" href="<c:url value="/logout"/>">
        <li>
               <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="logout"/></h1>
        </li>
        </a>
        <li></li>
    </ol>
</nav>


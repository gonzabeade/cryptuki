<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="py-3 sm:py-4">
    <div class="flex items-center space-x-2">
        <div class="flex-shrink-0">
            <img class="w-8 h-8 rounded-full" src="<c:url value="/public/images/${param.code}.png"/>" alt="<c:out value="${param.commercialName}"/>">
        </div>
        <c:set var="referencePrice"><messages:message code="referencePrice"/></c:set>
        <div class="flex-1 min-w-0" title="${referencePrice}">
            <p class="text-sm font-medium text-gray-900 truncate ">
                <c:out value="${param.commercialName}"/>
            </p>
            <p id="${param.code}" class="text-sm text-gray-500 ">
                No data available
            </p>
        </div>
        <div class="inline-flex items-center text-base font-semibold text-gray-900 ">
            <div class="flex flex-row mx-auto my-auto ">
                <label class="switch">
                    <input type="checkbox" id="${param.code}" name="coins" value="${param.code}">
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
    </div>
</li>

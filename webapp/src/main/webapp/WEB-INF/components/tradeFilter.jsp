<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<div class="flex flex-col">
    <div class="mx-auto flex  mt-10 bg-[#FAFCFF]/[0.9] p-4 rounded-full drop-shadow-md divide-x" >
        <div class="flex flex-col my-auto justify-center mx-3">
            <label for="status" class="font-sans text-sm font-semibold  ml-2 text-center"><messages:message code="tradeStatus"/></label>
            <select name="status" id="status" class="bg-transparent p-2 mx-2" onchange="addQueryParam(this.id)">
                <option disabled selected><messages:message code="chooseAnOption"/></option>
                <c:forEach items="${tradeStatusList}" var="status">
                    <option value="${status.toString()}"><messages:message code="${status.toString()}"/></option>
                </c:forEach>
            </select>
        </div>
        <a href="<c:url value="${param.url}"/>" class="drop-shadow-lg bg-frost rounded-full p-3 ml-4 my-auto" id="link" rel="search">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
            </svg>
        </a>
    </div>
</div>
<div class="flex flex-row justify-center mt-3">
    <button onclick="resetAllFilters()" class="justify-start text-polard font-regular hidden" id="reset"><messages:message code="cleanFilters"/></button>
</div>
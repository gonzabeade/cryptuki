<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<c:set var="emailPlaceholder"><messages:message code="placeholder.email"/></c:set>
<c:set var="yourMessagePlaceholder"><messages:message code="placeholder.yourMessage.conditions"/></c:set>

<c:url value="/contact" var="postUrl"/>
<%--@elvariable id="supportForm" type=""--%>
<form:form modelAttribute="supportForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">
    <form:input path="tradeId" type="hidden" value="${param.tradeId}"/>
    <form:input path="complainerId" type="hidden" value="${param.complainerId}"/>
    <form:input path="username" type="hidden" value="${param.username}"/>
    <c:if test="${param.username.isEmpty()}">
        <div class="flex flex-col p-5 justify-center">
            <form:errors path="email" cssClass="text-red-500"/>
            <form:label  path="email" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Correo electrónico *</form:label>
            <div class="flex-row justify-center">
                <form:input type="email" path="email" class=" min-w-full h-10 justify-center rounded-lg p-3" placeholder="roman@gmail.com"/>
            </div>

    <div class="flex flex-col p-5 justify-center">
        <form:errors path="email" cssClass="text-red-500"/>
        <form:label  path="email" class="text-xl font-sans text-polard font-semibold mb-3 text-center"><messages:message code="emailAddress"/> *</form:label>
        <div class="flex-row justify-center">
            <form:input type="email" path="email" class=" min-w-full h-10 justify-center rounded-lg p-3" placeholder="${emailPlaceholder}"/>
        </div>
    </c:if>
    <div class="flex flex-col p-5 ">
        <form:errors path="message" cssClass="text-red-500"/>
        <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center"><messages:message code="message"/> *</form:label>
        <div class="flex-row justify-center">
            <form:textarea class="min-w-full h-32 rounded-lg mx-auto p-5"  path="message" placeholder="${yourMessagePlaceholder}" />
        </div>
    </div>
    <div class="flex flex-row p-5">
        <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto"><messages:message code="send"/></button>
    </div>
</form:form>

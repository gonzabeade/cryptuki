<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url value="/complain" var="postUrl"/>
<%--@elvariable id="supportForm" type=""--%>
<form:form modelAttribute="supportForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">
    <form:input path="tradeId" type="hidden" value="${param.tradeId}"/>
    <form:input path="complainerId" type="hidden" value="${param.complainerId}"/>
    <form:input path="username" type="hidden" value="${param.username}"/>
    <form:input type="hidden" path="email" class=" min-w-full h-10 justify-center rounded-lg p-3" placeholder="roman@gmail.com"/>

    <div class="flex flex-col p-5 ">
        <form:errors path="message" cssClass="text-red-500"/>
        <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Mensaje *</form:label>
        <div class="flex-row justify-center">
            <c:set var="placeholder"> <messages:message code="placeholder.message"/></c:set>
            <form:textarea class="min-w-full h-32 rounded-lg mx-auto p-5"  path="message" placeholder="${placeholder}" />
        </div>
    </div>
    <div class="flex flex-row p-5">
        <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Enviar</button>
    </div>
</form:form>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>


<c:url value="/verify" var="postPath"/>
<%--@elvariable id="CodeForm" type="ar.edu.itba.paw.cryptuki.form"--%>
<form:form modelAttribute="CodeForm" action="${postPath}" method="post" cssClass="flex flex-col mx-auto">
    <h1 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="verifyYourAccount"/></h1>
    <h3 class="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3"><messages:message code="weSentYourCode"/></h3>

    <div class="flex flex-col">
        <c:if test="${param.error == true}">
            <p class="text-red-400 text-center"><messages:message code="incorrectCode"/></p>
        </c:if>
        <form:errors path="code" cssClass="text-red-400 text-center" element="p"/>
        <form:label path="code" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="code"/></form:label>
        <form:input type="number" path="code" cssClass="rounded-lg p-3 mx-auto"/>
        <form:input path="username" type="hidden" value="${param.username}"/>

    </div>
    <div class="flex mx-auto mt-10">
        <input type="submit" value="Verificar" class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg"/>
    </div>
</form:form>

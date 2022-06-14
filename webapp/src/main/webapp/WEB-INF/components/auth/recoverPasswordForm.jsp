<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>


<c:url value="/recoverPassword" var="postPath"/>
<%--@elvariable id="recoverPasswordForm" type="ar.edu.itba.paw.cryptuki.form.auth.RecoverPasswordForm"--%>
<form:form modelAttribute="recoverPasswordForm" action="${postPath}" method="post" cssClass=" py-12 px-36 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard" onsubmit="event.preventDefault(); preventRecoverPasswordNotMatching()">
    <h2 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="insertNewPassword"/></h2>

    <div class="flex flex-col mt-3">
        <form:errors cssClass="text-red-400" element="p"/>
        <form:input path="username" type="hidden" value="${param.username}"/>
        <form:input path="code" type="hidden"/>
        <form:label path="password" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="password"/></form:label>
        <form:errors path="password" cssClass="text-red-400" element="p"/>
        <div class="flex flex-col">
            <form:input type="password" path="password" cssClass="rounded-lg p-3 w-full" placeholder="e.g micontraseña" onchange="passwordMatch()"/>
            <p class="text-red-400 hidden" id="passError"><messages:message code="passwordsDontMatch"/></p>
        </div>
    </div>
    <div class="flex flex-col mt-3">
        <form:label path="repeatPassword" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="repeatPassword"/></form:label>
        <form:errors path="repeatPassword" cssClass="text-red-400" element="p"/>
        <div class="flex flex-col">
            <form:input type="password" path="repeatPassword" cssClass="rounded-lg p-3  w-full" placeholder="e.g micontraseña" onchange="passwordMatch()"/>
            <p class="text-red-400 hidden" id="repeatPassError"><messages:message code="passwordsDontMatch"/></p>
        </div>

    </div>


    <div class="flex flex-col justify-center mt-6">
        <input type="submit" value="Confirmar"  class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg mx-20"/>
    </div>
</form:form>


<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>


<c:url value="/register" var="postPath"/>
<%--@elvariable id="registerForm" type="ar.edu.itba.paw.cryptuki.form.RegisterForm"--%>
<c:set var="passwordPlaceholder"><messages:message code="placeholder.password"/></c:set>
<c:set var="usernamePlaceholder"><messages:message code="placeholder.username"/></c:set>
<c:set var="emailPlaceholder"><messages:message code="placeholder.email"/></c:set>
<c:set var="sendButton"><messages:message code="send"/></c:set>


<form:form modelAttribute="registerForm" action="${postPath}" method="post" cssClass=" py-12 px-36 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard" onsubmit="event.preventDefault(); preventSubmitPasswordNotMatching()">
    <h2 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="register"/></h2>
    <h3 class="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3"><messages:message code="createAccountForPublishing"/></h3>
    <div class="flex flex-col mt-3">
        <form:label path="email" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="emailAddress"/></form:label>
        <form:errors path="email" cssClass="text-red-400" element="p"/>
        <form:input type="text" path="email" cssClass="rounded-lg p-3" placeholder="${emailPlaceholder}"/>

    </div>

    <div class="flex flex-col mt-3">

        <form:label path="username" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="user"/></form:label>
        <form:errors path="username" cssClass="text-red-400" element="p"/>
        <form:input type="text" path="username" cssClass="rounded-lg p-3" placeholder="${usernamePlaceholder}"/>

    </div>

    <div class="flex flex-col mt-3">

        <form:label path="phoneNumber" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="phoneNumber"/></form:label>
        <form:errors path="phoneNumber" cssClass="text-red-400" element="p"/>
        <form:input type="text" path="phoneNumber" cssClass="rounded-lg p-3" placeholder="12345678"/>

    </div>

    <div class="flex flex-col mt-3">
        <form:label path="password" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="password"/></form:label>
        <form:errors path="password" cssClass="text-red-400" element="p"/>
        <div class="flex flex-col">
            <form:input type="password" path="password" cssClass="rounded-lg p-3 w-full" placeholder="${passwordPlaceholder}" onchange="passwordMatch()"/>
            <p class="text-red-400 hidden" id="passError"><messages:message code="passwordsDontMatch"/></p>
        </div>
    </div>
    <div class="flex flex-col mt-3">
        <form:label path="repeatPassword" cssClass="text-center text-xl font-bold font-sans text-polar my-2"><messages:message code="repeatPassword"/></form:label>
        <form:errors path="repeatPassword" cssClass="text-red-400" element="p"/>
        <div class="flex flex-col">
            <form:input type="password" path="repeatPassword" cssClass="rounded-lg p-3  w-full" placeholder="${passwordPlaceholder}" onchange="passwordMatch()"/>
            <p class="text-red-400 hidden" id="repeatPassError"><messages:message code="passwordsDontMatch"/></p>
        </div>

    </div>
    <div class="flex flex-col justify-center mt-6">
        <input type="submit" value="${sendButton}"  class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg mx-20"/>
        <a href="<c:url value="/login"/>" class=" underline text-polard text-center mt-2"><messages:message code="logInWithExistingAccount"/></a>
    </div>
</form:form>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url value="/contact" var="postUrl"/>
<form:form modelAttribute="supportForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">

    <div class="flex flex-col p-5 justify-center">
        <form:errors path="email" element="h1" cssClass="bg-red" />
        <form:label  path="email" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Email</form:label>
        <div class="flex-row justify-center">
            <form:input type="email" path="email" class=" min-w-full h-10 justify-center rounded-lg p-3"/>
        </div>

    </div>
    <div class="flex flex-col p-5 ">
        <form:errors path="message" element="p" cssClass="color: red"/>
        <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Mensaje</form:label>
        <div class="flex-row justify-center">
            <form:textarea class="min-w-full h-32 rounded-lg mx-auto p-5"  path="message"/>
        </div>

    </div>
    <div class="flex flex-row p-5">
        <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Enviar</button>
    </div>
</form:form>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/verify" var="postPath"/>
<%--@elvariable id="CodeForm" type="ar.edu.itba.paw.cryptuki.form"--%>
<form:form modelAttribute="CodeForm" action="${postPath}" method="post" cssClass="flex flex-col mx-auto">
    <h1 class="text-center text-4xl font-semibold font-sans text-polar">Verifica tu cuenta</h1>
    <h3 class="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3">Te enviamos el código a tu mail! Los códigos tienen una validez de 30 días.</h3>

    <div class="flex flex-col">
        <form:errors path="code" cssClass="text-red-400" element="p"/>
        <c:if test="${param.error}"><h2 class="text-red-400 text-center">Código incorrecto</h2></c:if>
        <form:label path="code" cssClass="text-center text-xl font-bold font-sans text-polar my-2">Código</form:label>
        <form:input type="text" path="code" cssClass="rounded-lg p-3 mx-auto"/>
        <form:input path="username" type="hidden" value="${param.username}"/>

    </div>
    <div class="flex mx-auto mt-10">
        <input type="submit" value="Verificar" class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg"/>
    </div>
</form:form>

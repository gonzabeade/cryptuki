<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/register" var="postPath"/>
<%--@elvariable id="registerForm" type="ar.edu.itba.paw.cryptuki.form.RegisterForm"--%>
<form:form modelAttribute="registerForm" action="${postPath}" method="post" cssClass=" py-12 px-36 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard">
    <h2 class="text-center text-4xl font-semibold font-sans text-polar">Registrate</h2>
    <h3 class="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3"> Crea una cuenta para poder publicar anuncios y más.</h3>
    <c:if test="${param.error}"><p class="text-red-400 text-center">Usuario o mail ya registrado</p></c:if>
    <div class="flex flex-col mt-3">
        <form:errors path="email" cssClass="text-red-400" element="p"/>
        <form:label path="email" cssClass="text-center text-xl font-bold font-sans text-polar my-2">Correo electrónico</form:label>
        <form:input type="text" path="email" cssClass="rounded-lg p-3" placeholder="e.g mimail@example.com"/>

    </div>

    <div class="flex flex-col mt-3">
        <form:errors path="username" cssClass="text-red-400" element="p"/>
        <form:label path="username" cssClass="text-center text-xl font-bold font-sans text-polar my-2">Usuario</form:label>
        <form:input type="text" path="username" cssClass="rounded-lg p-3" placeholder="e.g miusario"/>

    </div>

    <div class="flex flex-col mt-3">
        <form:errors path="phoneNumber" cssClass="text-red-400" element="p"/>
        <form:label path="phoneNumber" cssClass="text-center text-xl font-bold font-sans text-polar my-2">Número telefónico</form:label>
        <form:input type="text" path="phoneNumber" cssClass="rounded-lg p-3" placeholder="e.g 5491123456789"/>

    </div>

    <div class="flex flex-col mt-3">
        <form:errors path="password" cssClass="text-red-400" element="p"/>
        <form:label path="password" cssClass="text-center text-xl font-bold font-sans text-polar my-2">Contraseña</form:label>
        <form:input type="password" path="password" cssClass="rounded-lg p-3" placeholder="e.g micontraseña"/>

    </div>
    <div class="flex flex-col mt-3">
        <form:errors path="repeatPassword" cssClass="text-red-400" element="p"/>
        <form:label path="repeatPassword" cssClass="text-center text-xl font-bold font-sans text-polar my-2">Repetir contraseña</form:label>
        <form:input type="password" path="repeatPassword" cssClass="rounded-lg p-3" placeholder="e.g micontraseña" />

    </div>
    <div class="flex flex-col justify-center mt-6">
        <input type="submit" value="Enviar"  class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg mx-20"/>
        <a href="<c:url value="/login"/>" class=" underline text-polard text-center mt-2"> Ya tienes cuenta? Iniciá sesión</a>
    </div>
</form:form>

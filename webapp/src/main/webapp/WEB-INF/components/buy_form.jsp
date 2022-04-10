<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-col">
    <c:url value="/buy" var="postUrl"/>
    <form:form modelAttribute="offerBuyForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">

        <div class="flex flex-col p-5 justify-center">
            <form:errors path="email" cssClass="text-red-500"/>
            <form:label  path="email" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Email *</form:label>
            <div class="flex-row justify-center">
                <form:input type="email" path="email" class=" min-w-full h-10 justify-center rounded-lg p-3" placeholder="roman@gmail.com"/>
            </div>
        </div>

        <div class="flex flex-col p-5 justify-center">
            <form:errors path="amount" cssClass="text-red-500"/>
            <form:label  path="amount" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Quiero Comprar *</form:label>
            <div class="flex-row justify-center">
                <form:input type="number" path="amount" class=" min-w-full h-10 justify-center rounded-lg p-3"/>
            </div>

        </div>
        <div class="flex flex-col p-5 ">
            <form:errors path="message" cssClass="text-red-500"/>
            <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Mensaje *</form:label>
            <div class="flex-row justify-center">
                <form:textarea class="min-w-full h-32 rounded-lg mx-auto p-5"  path="message" placeholder="Nos encontramos en la esquina de Udaondo, soy comprador frecuente" />
            </div>

        </div>
        <div class="flex flex-row p-5">
            <button class="bg-gray-500 text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Cancelar</button>
            <button type="submit" class="bg-green-500 text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Enviar</button>
        </div>
        <form:hidden path="offerId" >${param.offerId}</form:hidden>
    </form:form>
</div>
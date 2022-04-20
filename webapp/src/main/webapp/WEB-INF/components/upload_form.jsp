<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.lang.Iterable"/>
<div class="flex flex-col">
    <form:form modelAttribute="uploadOfferForm" action="/upload" method="post" class="flex flex-col min-w-[50%]">

        <div class="flex flex-col justify-center mt-3">
            <form:errors path="cryptocurrency" cssClass=" mx-auto text-red-500"/>
            <form:label  path="cryptocurrency" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Criptomoneda*</form:label>
            <form:select path="cryptocurrency" class="mx-auto rounded-lg p-4">

                <c:forEach var="coin" items="${cryptocurrencies}">
                    <form:option value="${coin.code}"><c:out value="${coin.name}"/></form:option>
                </c:forEach>
            </form:select>
        </div>

        <div class="flex flex-col justify-center mt-3">
            <form:errors path="maxAmount" cssClass=" mx-auto text-red-500"/>
            <form:label  path="maxAmount" class="text-xl font-sans text-polard font-semibold mb-3 text-center">¿Cuántas monedas quieres vender?*</form:label>
            <div class="flex flex-row justify-center mx-auto">
                <form:input type="number" path="maxAmount" class="h-10 justify-center rounded-lg p-3 mx-5"/>
                <h1 class="my-auto" id="coinAmount">~0.00000</h1>
            </div>
        </div>
        <div class="flex flex-col mt-3 mx-auto">
            <form:errors path="paymentMethods" cssClass="text-red-500 mx-auto"/>
            <form:label path="paymentMethods" class="text-xl font-sans text-polard font-semibold mb-3 text-center ">Medios de Pago: *</form:label>
            <form:select path="paymentMethods" cssClass="rounded-lg p-4 w-96">
                <c:forEach var="coin" items="${cryptocurrencies}">
                    <form:option value="${coin.code}"><c:out value="${coin.name}"/></form:option>
                </c:forEach>
            </form:select>
        </div>
        <div class="flex flex-col mt-3">
            <form:errors path="price" cssClass="text-red-500 mx-auto"/>
            <form:label path="price" class="text-xl font-sans text-polard font-semibold mb-3 text-center ">Precio por moneda</form:label>
            <div class="flex flex-col justify-center mx-96">
                <form:input type="number" path="price" class="h-10 justify-center rounded-lg p-3 mx-auto"/>
               <!-- <h1 class="my-auto mx-auto" id="price">~0.00000 ARS</h1> -->
            </div>
        </div>
        <div class="flex flex-row p-5 mx-60">
            <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="<c:url value="/"/>">Cancelar</a>
            <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Enviar</button>
        </div>
    </form:form>
</div>

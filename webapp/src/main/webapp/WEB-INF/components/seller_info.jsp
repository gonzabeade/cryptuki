<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-col">
    <div class="flex  flex-col mx-auto text-center mb-10 w-full">
        <h1 class="font-sans font-bold text-3xl mt-3">Información del vendedor</h1>
        <div class="mt-4 bg-stormd/[0.9] border-2 border-polard rounded-lg mx-auto px-20">
            <h3 class="font-sans text-2xl my-3 text-polard font-semibold"><c:out value="${param.email}"/></h3>
            <h3 class="font-sans my-3"><b>Número de teléfono: </b><c:out value="${param.phone}"/></h3>
            <h3 class="font-sans my-3"><b>Cantidad de trades: </b><c:out value="${param.trades}"/></h3>
            <h3 class="font-sans my-3"><b>Último Login: </b><c:out value="${param.lastLogin}"/></h3>
        </div>
    </div>
    <div class="flex  flex-col mx-auto text-center mb-10 w-full">
        <h1 class="font-sans font-bold text-3xl mt-3">Mensaje del vendedor</h1>
        <div class="font-sans mx-32 text-justify mt-3">t has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</div>
    </div>
</div>

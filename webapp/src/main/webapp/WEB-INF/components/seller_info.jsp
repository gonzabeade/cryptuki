<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex  flex-col mx-auto text-center mb-10">
    <h1 class="font-sans font-bold text-polard text-2xl mt-3">Información del vendedor</h1>
    <div class="mt-4">
        <h3 class="font-sans my-3"><b>Correo electrónico:</b> <c:out value="${param.email}"/></h3>
        <h3 class="font-sans my-3"><b>Número de teléfono:</b><c:out value="${param.phone}"/></h3>
        <h3 class="font-sans my-3"><b>Cantidad de trades:</b><c:out value="${param.trades}"/></h3>
    </div>

</div>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<div class="flex  flex-col mx-10 text-center mb-10">
    <h1 class="font-sans font-bold text-polard text-2xl mt-3">Información del vendedor</h1>
    <div class="mt-4">
        <h3 class="font-sans my-3"><b>Correo electrónico:</b> <c:out value="${param.email}"/></h3>
        <h3 class="font-sans my-3"><b>Número de teléfono:</b> 12345678 </h3>
        <h3 class="font-sans my-3"><b>Cantidad de trades:</b> 10</h3>
    </div>

</div>

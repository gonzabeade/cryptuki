<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<hr>
<div class="flex  flex-col mx-auto text-center mb-10">
    <h1 class="font-sans font-bold text-polard text-2xl mt-3">Información del vendedor</h1>
    <div>
        <h3 class="font-sans">Correo electrónico: <c:out value="${param.email}"/></h3>
    </div>

</div>

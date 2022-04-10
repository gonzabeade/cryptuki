<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: marcos
  Date: 9/4/2022
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-col">
    <form:form>
        <div>
            <form:label path="amountInARS">Quiero comprar</form:label>
            <form:input path="amountInARS"/>
        </div>
        <div>
            <form:label path="message"/>
            <form:input path="message"/>
        </div>
        <div class="flex flex-row">
            <button>Cancelar</button>
            <button>Enviar</button>
        </div>

    </form:form>
</div>
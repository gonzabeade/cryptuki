<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: marcos
  Date: 8/4/2022
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<c:url value="${param.url}"/>">
    <c:forEach var="" items="">
        <label>Ayuda</label>
        <input type="text">
    </c:forEach>
    <button type="submit"> Envia</button>
</form>

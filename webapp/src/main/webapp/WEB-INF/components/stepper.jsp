<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex flex-row">
  <div class="col-span-12 block">
    <div class="wrapper-progressBar">
      <ul class="progressBar">
        <li class="${param.active >= 0  ? 'active': 'text-gray-400'}">Enviaste tu oferta</li>
        <li class="${param.active >= 1  ? 'active': 'text-gray-400'}">Contacta al vendedor para realizar el intercambio.</li>
        <li class="${param.active == 2  ? 'active': 'text-gray-400'}">Intercambio exitoso!</li>
      </ul>
    </div>
  </div>
</div>
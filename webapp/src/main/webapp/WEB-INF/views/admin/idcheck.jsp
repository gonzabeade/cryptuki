<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
  <script src="<c:url  value="/public/js/filterLink.js"/>"></script>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
  <title>cryptuki</title>
  <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>

<body class="bg-storml">
  <jsp:include page="../../components/admin/header.jsp"/>
  <c:url value="/admin/idcheck" var="idcheckUrl" />
<%--  TODO CHECK PORQUE  el ML ESTA EN 80 ESTO ESTA ASI--%>

  <div class="flex flex-col ml-80 p-10">
    <h1 class="font-sans text-4xl font-bold">Validador de identidad</h1>
    <h1 class="font-sans text-xl font-bold mt-5">Para que una solicitud de identidad sea válida, debe reunir todas las siguientes características. Solo podrá validar una identidad cuando explícitamente haya marcado que la información enviada cumple todos los requisitos.</h1>


    <form:form  modelAttribute="idCheckForm" action="${idcheckUrl}" method="post"  enctype="multipart/form-data" class="">
      <div class="flex w-full h-full mt-10">
        <ul class="font-sants text-xl w-1/2 mr-10">
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">Los nombres dados coinciden con los del documento subido</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
  <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">Los apellidos coinciden con los del documento subido</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
              <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">El número de documento coincide con el del documento subido</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
              <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">La nacionalidad coincide con aquella en el documento</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
              <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">El documento es un pasaporte o un dni</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
              <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">La foto personal y la del documento coinciden</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
              <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <li class="flex flex-row mt-3">
            <label path="isNameOk" class="text-start text-xl font-bold font-sans text-polar my-2 mr-4">El código manuscrito en la foto personal coincide con el solicitado</label>
            <input class="mt-3" path="givenNames" type="checkbox" class="rounded-lg p-3"/>
              <%--          <form:errors path="givenNames" cssClass="text-red-500"/>--%>
          </li>
          <div class="flex flex-row w-full justify-center mt-10">
            <input type="submit" value="Validar" class="w-1/5 rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg"/>
          </div>
        </ul>
        <div class="bg-red-100 w-1/2 p-auto p-auto">
          <img src="<c:url value="/seller/kyc/soutjava"/>"  class="w-1/2 hmx-auto" alt="profile" >
        </div>
      </div>

    </form:form>

  </div>




</body>

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
    <div class="flex flex-row">
      <h1 class="font-sans text-4xl font-bold">Validador de identidad</h1>
      <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 my-auto ml-2" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" onclick="toggleInfo('info')">
        <path stroke-linecap="round" stroke-linejoin="round" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
    </div>

    <!--Toggle -->
    <ol  id="info" class=" hidden bg-white hidden p-3 rounded-lg w-fit shadow-xl z-20 flex flex-col mt-14 absolute" >
      <div class="flex  justify-end" onclick="hideToggle('info')">
        <h2 class="font-sans font-bold mr-16">Chequeá que se cumplan todos los requisitos</h2>
        <p class="mr-2 font-sans font-bold">Cerrar </p>
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" >
          <path stroke-linecap="round" stroke-linejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
      </div>
      <div class="mt-3">
        <li>Los nombres dados coinciden con los del documento subido.</li>
        <li>Los apellidos coinciden con los del documento subido.</li>
        <li>El número de documento coincide con el del documento subido.</li>
        <li>La nacionalidad coincide con aquella en el documento.</li>
        <li>El documento es un pasaporte o un dni.</li>
        <li>La foto personal y la del documento coinciden.</li>
        <li>El código manuscrito en la foto personal coincide con el solicitado.</li>
      </div>
    </ol>
    <!--end Toggle-->
    <div class="flex w-full justify-center my-10 z-10">
      <div class="flex flex-col p-3 w-1/2">
        <h2 class=" text-lg font-sans font-bold">DNI Frente</h2>
        <div class="my-2  border-2 border-gray-400">
          <img src="<c:url value="/kyc/idPhoto/${username}"/>" class="h-[200px] w-full mx-auto">
        </div>
        <hr>
        <h2 class=" text-lg font-sans font-bold">Foto sosteniendo el DNI</h2>
        <div class="border-2 border-gray-400" >
          <img src="<c:url value="/kyc/validationphoto/${username}"/>" class="h-[200px] w-full mx-auto">
        </div>

      </div>
      <div class="flex justify-center w-1/2">
        <div class="flex flex-col">
          <h3 class="font-sans font-bold text-lg mb-10 text-xl">Información del usuario</h3>
          <div class="flex flex-row">
            <h4 class="font-sans font-bold mr-2">Nombres: </h4>
            <h3>Marcos Diego</h3>
          </div>
          <div class="flex flex-row ">
            <h4 class="font-sans font-bold mr-2">Apellidos: </h4>
            <h3> Dedeu Messi</h3>
          </div>
          <div class="flex flex-row ">
            <h4 class="font-sans font-bold mr-2">País: </h4>
            <h3> Argentina</h3>
          </div>
          <div class="flex flex-row">
          <h4 class="font-sans font-bold mr-2">Documento: </h4>
          <h3 class="mr-2"> DNI</h3>
          <h3> 43568446</h3>
        </div>
        </div>

      </div>

    </div>
    <div class="flex flex-row mx-auto">
      <c:url value="/admin/idcheck" var="postUrl"/>
        <form:form method="post" action="${postUrl}" modelAttribute="kycApprovalForm">
          <form:hidden path="approved" value="true"/>
          <button class="bg-ngreen rounded-lg text-white p-3 mr-10" type="submit">Aprobar</button>
        </form:form>
      <div onclick="toggleInfo('confirmation')">
        <button class="bg-nred rounded-lg text-white p-3">Rechazar</button>
      </div>
    </div>
    <!--Confirmation Toggle-->
    <div id="confirmation" class="bg-white shadow-xl p-10 hidden justify-center rounded-lg z-20 absolute w-[70%]">
      <div class="flex flex-col justify-center">
        <h2 class="text-2xl font-sans font-bold">Escribí el motivo del rechazo</h2>
        <form:form method="post" action="${postUrl}" modelAttribute="kycApprovalForm">
          <div class="flex flex-col">
            <form:textarea path="message" cssClass="border-2 border-gray-400 my-10 h-[14rem] p-2"/>
            <form:hidden path="approved" value="false"/>
            <div class="flex flex-row mx-auto justify-center">
              <button class="bg-frostdr rounded-lg text-white p-3 mr-10" type="submit"> Confirmar</button>
              <p class="bg-gray-400 rounded-lg text-white p-3 cursor-pointer" onclick="hideToggle('confirmation')"> Cancelar</p>
            </div>
            </div>
           </form:form>
      </div>


    </div>




  </div>




</body>
<script>
  function toggleInfo(id){
    var element = document.getElementById(id)
    if(element.classList.contains('hidden')){
      element.classList.remove('hidden')
    }else{
      element.classList.add('hidden')
    }
   }
   function hideToggle(id) {
     var element = document.getElementById(id)
     element.classList.add('hidden')
   }
</script>
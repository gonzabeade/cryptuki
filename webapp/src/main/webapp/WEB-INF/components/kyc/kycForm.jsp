<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="name" var="username"/>


<c:url value="/seller/kyc" var="kycUrl" />
<form:form modelAttribute="kycForm" action="${kycUrl}" method="post"  enctype="multipart/form-data" class=" py-12 px-4 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard">
  <h1 class="text-center text-4xl font-semibold font-sans text-polar">Verifica tu identidad</h1>

  <h2 class="text-start text-xl font-semibold font-sans text-polar mt-8">Para poder subir anuncios de compra, tu identidad debe estar validada por uno de nuestros administradores. Al completar este formulario, te notifcaremos por mail una vez que hayamos revisado tu solicitud.</h2>


  <h2 class="text-start text-2xl font-semibold font-sans text-polar underline mt-8">Sección 1: Datos del documento de identidad</h2>
  <h2 class="text-start text-xl font-semibold font-sans text-polar mt-2">Escriba todos los datos tal cuál se muestran en su documento de identidad.</h2>

  <form:input path="username" type="hidden" value="${username}"/>


  <div class="flex flex-wrap mx-5 mt-4 items-start">
    <div class="flex flex-col mr-10 my-4">
      <form:errors path="givenNames" cssClass="text-red-500"/>
      <form:label path="givenNames" class="text-start text-xl font-bold font-sans text-polar my-2">Nombre(s) dado(s)</form:label>
      <form:input path="givenNames" type="text" class="rounded-lg p-3" placeholder="e.g. Gonzalo Manuel"/>
    </div>
    <div class="flex flex-col mr-10 my-4">
      <form:errors path="surnames" cssClass="text-red-500"/>
      <form:label path="surnames" class="text-start text-xl font-bold font-sans text-polar my-2">Apellido(s)</form:label>
      <form:input path="surnames" type="text" class="rounded-lg p-3" placeholder="Beade"/>
    </div>
    <div class="flex flex-col mr-10 my-4">
      <form:errors path="nationality" cssClass="text-red-500"/>
      <form:label path="nationality" class="text-start text-xl font-bold font-sans text-polar my-2">Nacionalidad</form:label>
      <form:input path="nationality" type="text" class="rounded-lg p-3" placeholder="Argentina"/>
    </div>
  </div>


  <div class="flex flex-wrap mx-5 mt-4 items-start">
    <div class="flex flex-col mr-10 my-4">
      <form:errors path="idCode" cssClass="text-red-500"/>
      <form:label path="idCode" class="text-start text-xl font-bold font-sans text-polar my-2">Número de documento:</form:label>
      <form:input path="idCode" type="text" class="rounded-lg p-3" placeholder="e.g. 45089768"/>
    </div>
    <div class="flex flex-col mr-10 my-4">
      <form:errors path="idType" cssClass="text-red-500"/>
      <form:label path="idType" class="text-start text-xl font-bold font-sans text-polar my-2">Tipo de documento</form:label>
      <form:input path="idType" type="text" class="rounded-lg p-3" placeholder="e.g. DNI, Pasaporte, Registro"/>
    </div>
    <form:errors path="idPhoto" cssClass="text-red-500"/>
    <div class="flex flex-row mr-10 my-4">
      <form:label path="idPhoto"  class="text-start text-xl font-bold font-sans text-polar my-2">Foto del frente del documento</form:label>
      <form:input path="idPhoto"  type="file" class="rounded-lg p-3"/>
    </div>
  </div>

  <h2 class="text-start text-2xl font-semibold font-sans text-polar underline mt-8">Sección 2: Validación de los datos provistos</h2>
  <h2 class="text-start text-xl font-semibold font-sans text-polar mt-2">Por razones de seguridad y para poder validar que la persona poseedora del documento de identidad sea quien está llenando este formulario, requerimos que se tome una foto en primer plano, mostrando medio cuerpo, sosteniendo un cartel con el siguiente código:</h2>


  <div class="flex flex-row mx-5 mt-4 items-center">
    <div class="flex flex-col my-10 mx-auto">
      <h2 class="text-center text-3xl font-semibold font-sans text-polar">B00B5</h2>
      <div class="flex flex-row mx-5 my-4 px-16">
        <form:errors path="validationPhoto" cssClass="text-red-500"/>
        <form:label path="validationPhoto"  class="text-center text-xl font-bold font-sans text-polar my-2">Subir foto</form:label>
        <form:input path="validationPhoto"  type="file" enctype="multipart/form-data" class="rounded-lg p-3"/>
      </div>
    </div>
    <div class="flex flex-col my-2 mx-20 items-center">
      <img class="flex justify-start my-auto border-polar" src="<c:url value="/public/images/hold_card.png"/>"/>
    </div>
  </div>
  <div class="flex flex-row w-full justify-center mt-10">
    <input type="submit" value="Enviar" class="w-1/5 rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg"/>
  </div>


</form:form>
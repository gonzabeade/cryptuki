<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/formValidations.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<sec:authentication property="name" var="username"/>

<body class="bg-storml overflow-x-hidden justify-center">

<jsp:include page="../../components/seller/sellerHeader.jsp"></jsp:include>

<div class="">

    <% request.setCharacterEncoding("UTF-8"); %>
    <c:url value="/kyc" var="kycUrl" />
    <form:form modelAttribute="kycForm" action="${kycUrl}" method="post"  enctype="multipart/form-data" class="w-1/2 mt-20 mb-10 py-12 px-4 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard">
        <h1 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="verifyYourIdentity"/> </h1>

        <h2 class="text-start text-xl font-semibold font-sans text-polar mt-8">Para poder subir anuncios de compra, tu identidad debe estar validada por uno de nuestros administradores. Al completar este formulario, te notifcaremos por mail una vez que hayamos revisado tu solicitud.</h2>


        <h2 class="text-start text-2xl font-semibold font-sans text-polar underline mt-8">Sección 1: Datos del documento de identidad</h2>
        <h2 class="text-start text-xl font-semibold font-sans text-polar mt-2">Escriba todos los datos tal cuál se muestran en su documento de identidad.</h2>
        <h2 class="text-start text-xl font-semibold font-sans text-polar mt-2">Si su documento no fue emitido en alguno de los países listados, no podemos verificarlo aún. ¡Ya vamos a llegar!</h2>


        <form:input path="username" type="hidden" value="${username}"/>


        <div class="flex flex-col mx-5 mt-4 items-start">
            <div class="flex flex-col mr-10 my-4 w-1/2">
                <form:errors path="givenNames" cssClass="text-red-500"/>
                <form:label path="givenNames" class="text-start text-xl font-bold font-sans text-polar my-2">Nombre(s) dado(s)</form:label>
                <form:input path="givenNames" type="text" class="rounded-lg w-full p-3" placeholder="e.g. Gonzalo Manuel"/>
            </div>
            <div class="flex flex-col mr-10 my-4 w-1/2">
                <form:errors path="surnames" cssClass="text-red-500"/>
                <form:label path="surnames" class="text-start text-xl font-bold font-sans text-polar my-2">Apellido(s)</form:label>
                <form:input path="surnames" type="text" class="rounded-lg p-3" placeholder="Beade"/>
            </div>
            <div class="flex flex-col mr-10 my-4 w-1/2">
                <form:errors path="emissionCountry" cssClass="text-red-500"/>
                <form:label path="emissionCountry" class="text-start text-xl font-bold font-sans text-polar my-2">País de emisión</form:label>
                <form:select path="emissionCountry"  class="rounded-lg p-3 bg-white">
                    <c:forEach items="${countries}" var="country">
                        <form:option value="${country.isoCode}"><c:out value="${country.name}"/></form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>

        <div class="flex flex-row mx-5 mt-4 items-end">
            <div class="flex flex-col mr-10 my-4 w-1/2">
                <form:errors path="idCode" cssClass="text-red-500"/>
                <form:label path="idCode" class="text-start text-xl font-bold font-sans text-polar my-2">Número de documento:</form:label>
                <form:input path="idCode" type="text" class="rounded-lg p-3" placeholder="e.g. 45089768"/>
            </div>
            <div class="flex flex-col mr-10 my-4 w-1/2">
                <form:errors path="idType" cssClass="text-red-500"/>
                <form:label path="idType" class="text-start text-xl font-bold font-sans text-polar my-2">Tipo de documento</form:label>
                <form:select path="idType"  class="rounded-lg p-3 bg-white">
                    <c:forEach items="${idTypes}" var="idType">
                                  <form:option value="${idType}"><messages:message code="IdType.${idType}"/></form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>

        <div class="flex flex-col mr-18 my-4 w-1/2">
            <form:errors path="idPhoto" cssClass="text-red-500"/>
            <form:label path="idPhoto"  class="text-start text-xl font-bold font-sans text-polar my-2">Foto del frente del documento</form:label>
            <form:input path="idPhoto"  accept="image/png, image/gif, image/jpeg" type="file" class="rounded-lg p-3"/>
        </div>

        <h2 class="text-start text-2xl font-semibold font-sans text-polar underline mt-8">Sección 2: Validación de los datos provistos</h2>
        <h2 class="text-start text-xl font-semibold font-sans text-polar mt-2">Por razones de seguridad y para poder validar que la persona poseedora del documento de identidad sea quien está llenando este formulario, requerimos que se tome una foto en primer plano, mostrando medio cuerpo, sosteniendo la fachada de su documento de identidad frente a la camara</h2>


        <div class="flex flex-row mx-5 mt-4 items-center">
            <div class="flex flex-col my-10 mx-auto">
                <div class="flex flex-col mx-5 my-4 px-16">
                    <form:errors path="validationPhoto" cssClass="text-red-500"/>
                    <form:label path="validationPhoto"  class="text-center text-xl font-bold font-sans text-polar my-2">Subir foto</form:label>
                    <form:input path="validationPhoto"  type="file" accept="image/png, image/gif, image/jpeg" enctype="multipart/form-data" class="rounded-lg p-3"/>
                </div>
            </div>
            <div class="flex flex-col my-2 mx-20 items-center">
                <img class="flex justify-start my-auto border-polar" src="<c:url value="/public/images/hold_card.png"/>"/>
            </div>
        </div>
        <div class="flex flex-row w-full justify-center mt-10">
            <input type="submit" value="Enviar" class="w-1/5 rounded-lg bg-frostl py-3 px-5 text-white cursor-pointer shadow-lg"/>
        </div>
    </form:form>
</div>


</body>
</html>

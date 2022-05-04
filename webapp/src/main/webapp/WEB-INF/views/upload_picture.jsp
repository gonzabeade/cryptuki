<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>

<div class="flex flex-col justify-center items-center">
    <div>
        <h1 class="text-center text-4xl font-semibold font-sans text-polar mt-20 mb-20">Escoge tu nueva foto de perfil</h1>
    </div>
    <div>
        <div>
            <h3 class="text-center text-4xl font-semibold font-sans text-polar mt-20 mb-20">Buscá entre tus archivos</h3>
        </div>
        <c:url value="/profilePicSelector" var="postUrl"/>
        <form:form modelAttribute="ProfilePicForm" action="${postUrl}" method="post" enctype="multipart/form-data">
            <form:label for="fyle" path="multipartFile">Buscá entre tus archivos</form:label>
            <form:input type="file" path="multipartFile"/>
            <div class="flex justify-around flex-row">
                <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="javascript:history.back()">Cancelar</a>
                <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress">Enviar</button>
            </div>
        </form:form>
    </div>
</div>

<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>













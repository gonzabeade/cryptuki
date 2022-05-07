<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/feedback.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-row divide-x divide-polard mt-10">
    <div class="flex flex-col">
        <div class="flex">
            <div class="flex flex-col mx-auto mt-10">
                <h2 class="font-sans font-semibold text-polard text-5xl text-center">Reclamo #25</h2>
                <h2 class="font-sans font-medium text-polard text-3xl text-center">Efectuado el 2022-02-20</h2>
            </div>
        </div>
        <div class="flex flex-col mt-10 mx-20">
            <h1 class="font-sans font-medium text-polard text-2xl text-center">Descripción del reclamo:</h1>
            <p class="mx-auto rounded-lg text-justify">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book</p>
        </div>
        <div class="flex flex-col mt-6 mx-52">
            <h1 class="font-sans font-medium text-polard text-2xl text-center">Usuario del reclamo:</h1>
            <p class="mx-auto rounded-lg text-justify text-xl">mdedeu</p>
            <p class="mx-auto rounded-lg text-justify text-lg">marcosdedeu@hotmail.com</p>
            <p class="mx-auto rounded-lg text-justify text-gray-400">Última vez activo: 2022-02-03</p>
        </div>
        <div class="flex flex-row">
            <c:if test="${isAdmin}">
                <a href="<c:url value="/admin"/>" class="bg-frost text-white p-3 font-sans rounded-lg mx-auto mt-10"> Volver al admin</a>
                <div class="flex flex-row  mx-auto mt-10">
                    <c:url value="/delete" var="deleteUrl"/>
                    <form:form method="post" action="${deleteUrl}" cssClass="flex my-auto mx-3">
                        <button type="submit" class="bg-frostdr text-white text-center mx-auto p-3 rounded-md font-sans">Asignarme el reclamo</button>
                    </form:form>
                </div>
            </c:if>
        </div>

    </div>
    <c:if test="${trade!=null}">
    <div class="flex flex-col mt-6 mx-auto w-1/2">
        <h1 class="font-sans font-medium text-polard text-2xl text-center">Detalles del trade</h1>
        <div class="py-12 px-20 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard mt-3 mx-20">
            <h1 class="font-sans font-medium text-polard text-xl text-center ">Oferta #25</h1>
            <h1 class="text-center text-4xl font-bold">Bitcoin</h1>
            <h2 class="font-sans font-medium text-polard text-2xl text-center">a  1400000 ARS </h2>
            <div class="flex flex-row mt-3 font-sans mx-auto ">
                <h2 class="font-sans mx-2"><b>Mínimo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="10000"/> ARS </h2>
                <h2 class="font-sans"> <b>Máximo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="200000"/> ARS </h2>
            </div>
            <div class="flex flex-col mx-auto mt-5">
                <h2 class="font-sans font-polard font-semibold text-2xl mb-3 text-center">Participantes</h2>
                <p class="font-sans font-polard"><b>Comprador:</b> mdedeu</p>
                <p class="font-sans font-polard"><b>Vendedor:</b> mdedeu</p>
            </div>
            <div class="flex flex-col mx-auto mt-3">
                <h2 class="font-sans font-semibold font-polard text-2xl text-center ">Cantidad ofertada</h2>
                <h3 class="text-xl font-sans font-polard text-center">1000 ARS</h3>
            </div>
            <div class="flex flex-col mx-auto mt-3">
                <h2 class="font-sans font-semibold font-polard text-2xl text-center ">Estado del Trade</h2>
                <h3 class="text-xl font-sans font-polard text-center">Pendiente</h3>
            </div>
        </div>
    </c:if>
</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>
<div class="shape-blob" style="left: 5%; top: 80%"></div>


</body>
</html>
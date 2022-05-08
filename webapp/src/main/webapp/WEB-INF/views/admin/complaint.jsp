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
    <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../../components/admin/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-row divide-x divide-polard">
    <div class="flex flex-col ml-80">
        <div class="flex">
            <div class="flex flex-col mx-auto mt-10">
                <h2 class="font-sans font-semibold text-polard text-5xl text-center">Reclamo # <c:url value="${complain.complainId}"/> </h2>
                <h2 class="font-sans font-medium text-polard text-3xl text-center">Efectuado el <c:url value="${complain.date}"/></h2>
            </div>
        </div>
        <div class="flex flex-col mt-10 mx-20">
            <h1 class="font-sans font-medium text-polard text-2xl text-center">Descripción del reclamo:</h1>
            <p class="mx-auto rounded-lg text-justify"><c:url value="${complain.complainerComments.get()}"/></p>
        </div>
        <div class="flex flex-col mt-6 mx-52">
            <h1 class="font-sans font-medium text-polard text-2xl text-center">Usuario del reclamo:</h1>
            <p class="mx-auto rounded-lg text-justify text-xl"><c:url value="${complain.complainerUsername}"/></p>
            <p class="mx-auto rounded-lg text-justify text-lg"><c:url value="${complainer.email}"/></p>
            <p class="mx-auto rounded-lg text-justify text-gray-400">Última vez activo: <c:url value="${complainer.lastLogin.toLocalDate()}"/></p>
        </div>
        <div class="flex flex-row">
                <a href="<c:url value="/admin"/>" class="bg-frost text-white p-3 font-sans rounded-lg mx-auto mt-10"> Volver a Reclamos Pendientes</a>
                <div class="flex flex-row  mx-auto mt-10">
                    <c:url value="/admin/selfassign/${complain.complainId}" var="postUrl"/>
                    <form:form method="post" action="${postUrl}" cssClass="flex my-auto mx-3">
                        <button type="submit" class="bg-frostdr text-white text-center mx-auto p-3 rounded-md font-sans">Asignarme el reclamo</button>
                    </form:form>
                </div>
        </div>

    </div>
    <c:if test="${trade!=null}">
    <div class="flex flex-col mt-6 mx-auto">
        <h1 class="font-sans font-medium text-polard text-2xl text-center">Detalles del trade</h1>
        <div class="py-12 px-20 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard mt-3 mx-20">
            <h1 class="font-sans font-medium text-polard text-xl text-center ">Trade #<c:out value="${trade.tradeId}"/></h1>
            <h1 class="font-sans font-medium text-polard text-m text-center ">Realizado sobre la oferta #<c:out value="${trade.offerId}"/></h1>
<%--            <h1 class="text-center text-4xl font-bold"><c:out value="${trade.}"/></h1>--%>
<%--            <h2 class="font-sans font-medium text-polard text-2xl text-center">a  1400000 ARS </h2>--%>
<%--            <div class="flex flex-row mt-3 font-sans mx-auto ">--%>
<%--                <h2 class="font-sans mx-2"><b>Mínimo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="10000"/> ARS </h2>--%>
<%--                <h2 class="font-sans"> <b>Máximo:</b> <fmt:formatNumber type="number" maxFractionDigits="2" value="200000"/> ARS </h2>--%>
<%--            </div>--%>
            <div class="flex flex-col mx-auto mt-5">
                <h2 class="font-sans font-polard font-semibold text-2xl mb-3 text-center">Participantes</h2>
                <p class="font-sans font-polard"><b>Comprador:</b> <c:out value="${trade.buyerUsername}"/></p>
                <p class="font-sans font-polard"><b>Vendedor:</b> <c:out value="${trade.sellerUsername}"/></p>
            </div>
            <div class="flex flex-col mx-auto mt-3">
                <h2 class="font-sans font-semibold font-polard text-2xl text-center ">Cantidad ofertada</h2>
                <h3 class="text-xl font-sans font-polard text-center"><c:out value="${trade.quantity}"/> ARS</h3>
            </div>
            <div class="flex flex-col mx-auto mt-3">
                <h2 class="font-sans font-semibold font-polard text-2xl text-center ">Estado del Trade</h2>
                <h3 class="text-xl font-sans font-polard text-center"><c:out value="${trade.status}"/></h3>
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
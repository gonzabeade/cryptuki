<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="pendingKycs" scope="request" type="java.util.Collection"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url  value="/public/js/feedback.js"/>"></script>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>

<body class="bg-storml overflow-x-hidden">
<div class="flex flex-row">

    <% request.setCharacterEncoding("utf-8"); %>
    <jsp:include page="../../components/admin/header.jsp"/>
    <div class="flex flex-col ml-64 my-10 h-screen w-screen">
        <h1 class="font-sans text-4xl font-bold"><messages:message code="kyccheck"/></h1>


        <c:if test="${pendingKycs.isEmpty()}">
            <h1 class="mt-10 font-sans text-2xl font-bold">No hay solicitudes de verificación pendientes.</h1>
        </c:if>

        <div id="confirmationToggle" class="hidden">
            <jsp:include page="../../components/confirmationToggle.jsp">
                <jsp:param name="title" value="Solicitud de verificación procesada con éxito [TRADUCIR]"/>
            </jsp:include>
        </div>

        <div class="flex flex-wrap w-full mt-3">
            <c:forEach items="${pendingKycs}" var="kyc">
                <div class="flex flex-col bg-white shadow rounded-lg p-3 m-5 font-sans font-bold">
                    <div class="w-full mt-2 text-xl text-start"><b><messages:message code="user"/>:</b> <c:out value="${kyc.user.username.get()}"/></div>
                    <div class="w-full mt-2 text-xl text-start"><b><messages:message code="date"/></b> <c:out value="${kyc.kycDate.toLocalDate()}"/></div>
                    <div class="mx-auto my-3">
                        <a href="<c:url value="/admin/kyccheck/${kyc.user.username.get()}"/>" class=" text-center pb-2 px-5 pt-2 rounded-lg bg-stormd max-h-14 text-polard my-auto"><messages:message code="see"/></a>
                    </div>
                </div>
            </c:forEach>
        </div>


    </div>

</div>

<script>
    window.onload = function successMessage() {
        const searchParams = new URLSearchParams(window.location.search);
        console.log(searchParams)
        if (searchParams.has("success")) {
            var element = document.getElementById("confirmationToggle")
            element.classList.remove('hidden')
        }

    }
</script>
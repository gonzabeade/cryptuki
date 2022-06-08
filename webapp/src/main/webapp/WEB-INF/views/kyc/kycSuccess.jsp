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
<body class="bg-storml overflow-x-hidden justify-center">
<jsp:include page="../../components/seller/sellerHeader.jsp"/>
<div class="flex w-1/2 mt-20 mb-10 mx-auto">
    <div class="py-12 px-4 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard">
        <h1 class="text-center text-4xl font-semibold font-sans text-polar">Hemos recibido tus datos correctamente.</h1>
        <h2 class="text-center text-xl font-semibold font-sans text-polar mt-8">Te notificaremos por correo cuando el proceso de verificación de identidad haya concluido.</h2>
        <a class="flex justify-center mt-5" href="<c:url value="/seller"/>">
            <div class="w-1/5 rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
                Volver
            </div>
        </a>

    </div>
</div>


</body>
</html>

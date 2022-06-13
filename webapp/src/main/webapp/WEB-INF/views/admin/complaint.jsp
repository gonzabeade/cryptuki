<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
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
<jsp:include page="../../components/admin/header.jsp"/>
<div class="flex flex-col  ml-72 mr-20">
    <div class="flex">
        <div class="flex flex-col mt-10">
            <h2 class="font-sans text-4xl font-boldfont-sans font-semibold text-5xl"><messages:message code="claim"/> # <c:url value="${complain.complainId}"/> </h2>
            <h2 class="font-sans font-medium text-polard text-2xl"><messages:message code="carriedOutOn"/> <c:url value="${complain.date.toLocalDate()}"/></h2>
        </div>
    </div>
</div>
<div class="ml-72 flex flex-row mt-10">
    <div class="w-1/4">
        <div class="flex flex-col">
            <div class="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard">
                <h1 class="font-sans font-semibold font-polard text-2xl text-start "><messages:message code="tradeDetails"/></h1>
                <h1 class="w-full font-sans font-medium text-polard text-xl text-start "><messages:message code="trade"/> #
                    <c:out value="${trade.tradeId}"/></h1>
                <h1 class="w-full font-sans font-medium text-polard text-xl text-start "><messages:message code="carriedOutOverOffer"/> #<c:out value="${trade.offer.offerId}"/></h1>
                <div class="w-full  flex flex-col mx-auto mt-5">
                    <h2 class="font-sans font-polard font-semibold text-xl mb-3 text-start"><messages:message code="participants"/></h2>
                    <li class="font-sans font-polard"><b><messages:message code="buyer"/></b> <c:out value="${trade.buyer.userAuth.username}"/></li>
                    <li class="font-sans font-polard"><b><messages:message code="seller"/></b> <c:out value="${trade.offer.seller.userAuth.username}"/></li>
                </div>
                <div class="w-full flex flex-row mx-auto mt-3">
                    <h2 class="font-sans font-semibold font-polard text-xl text-start mr-3"><messages:message code="offeredAmount"/>:</h2>
                    <h3 class="font-sans font-medium text-polard text-xl text-start "><c:out value="${trade.quantity}"/> ARS</h3>
                </div>
                <div class="w-full  flex flex-row mx-auto mt-3">
                    <h2 class="font-sans font-semibold font-polard text-xl text-start mr-3"><messages:message code="tradeState"/>:</h2>
                    <h3 class="font-sans font-medium text-polard text-xl text-start "><messages:message code="${trade.status}"/></h3>
                </div>
            </div>
        </div>
        <div class="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard mt-5">
            <h1 class="font-sans font-semibold font-polard text-2xl text-start "><messages:message code="claimDescription"/></h1>
            <p class="font-sans text-start mt-2 italic"><c:url value="${complain.complainerComments.get()}"/></p>
        </div>
        <div class="w-full py-5 px-5 rounded-lg bg-stormd/[0.9] flex flex-col justify-start mx-auto border-2 border-polard mt-5">
            <h1 class="font-sans font-semibold font-polard text-2xl text-start "><messages:message code="claimUser"/></h1>
            <c:choose>
                <c:when test="${complain.complainer.username.get() != null}">
                    <h1 class="font-sans font-medium text-polard text-xl text-start"><c:url value="${complain.complainer.username.get()}"/></h1>
                    <p class="rounded-lg text-gray-400"><messages:message code="lastTimeActive"/>: <c:url value="${complainer.lastLogin.toLocalDate()}"/></p>
                </c:when>
                <c:otherwise>
                    <p class="rounded-lg text-lg"><c:url value="${complainer.email}"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="w-1/3 h-full">
        <c:set value="${trade.messageCollection}" var="messageCollection" scope="request"/>
        <jsp:include page="../../components/chat/immutableChatSnippet.jsp">
            <jsp:param name="otherUsername" value="${trade.buyer.username.get() == complainer.username.get() ? trade.offer.seller.username.get() : trade.buyer.username.get()}"/>
            <jsp:param name="complainerUsername" value="${complainer.username.get()}"/>
            <jsp:param name="otherUserId" value="${trade.buyer.username.get() == complainer.username.get() ? trade.offer.seller.id : trade.buyer.id}"/>
            <jsp:param name="senderId" value="${complainer.id}"/>
        </jsp:include>
    </div>
    <div class="flex flex-col w-1/3 h-full justify-center ">
        <div class="w-full rounded-lg bg-[#FAFCFF] mx-auto py-3 mb-5 text-center border-2 border-polard">
            <p class="font-sans font-semibold font-polard text-xl"> ¿Qué se debería hacer ante la denuncia de <c:out value="${complainer.username.get()}"/>? </p>
        </div>
        <div class="flex flex-row mx-auto w-full text-center justify-around ">
            <button id="dismissButton" class="bg-ngreen rounded-lg text-white p-3" onclick="showOnlyDismissForm()">Desestimar denuncia</button>
            <button id="kickoutButton" class="bg-nred rounded-lg text-white p-3" onclick="showOnlyKickoutForm()">Vetar al denunciado</button>
        </div>
        <div id="kickoutForm" class="hidden w-full flex flex-col mt-5">
            <c:url value="/admin/complaint/kickout/${complaintId}?user=${trade.buyer.username.get() == complainer.username.get() ? trade.offer.seller.id : trade.buyer.id}" var="kickoutUrl"/>
            <form:form method="post" action="${kickoutUrl}" modelAttribute="solveComplainFormKickout">
                <div class="w-full flex justify-end py-2"><img onclick="deleteSemiForms()" class="w-5 h-5 my-auto align-end" src="<c:url value = "/public/images/cross.png"/>"></div>
                <div class="flex flex-row bg-white shadow rounded-lg p-3 font-sans font-bold">
                    <img class="w-5 h-5 mr-4 my-auto " src="<c:url value = "/public/images/attention.png"/>">
                    <p>Estas a punto de vetar a <c:url value="${trade.buyer.username.get() == complainer.username.get() ? trade.offer.seller.username.get() : trade.buyer.username.get()}"/> de Cryptuki. Recuerda que el veredicto no es reversible, debes estar seguro de la decisión. </p>
                </div>
                <form:textarea class="min-w-full h-32 rounded-lg mx-auto p-5 mt-5" path="comments" placeholder="Escribe un motivo [TRADUCIR]"></form:textarea>
                <button class="mt-3 w-1/5 mx-auto bg-frost rounded-lg text-white p-3" >Enviar</button>
            </form:form>
        </div>
        <div id="dismissForm" class="hidden w-full flex flex-col mt-5">
            <c:url value="/admin/complaint/dismiss/${complaintId}" var="dismissUrl"/>
            <form:form method="post" action="${kickoutUrl}" modelAttribute="solveComplainFormKickout">
                <div class="w-full flex justify-end py-2"><img onclick="deleteSemiForms()" class="w-5 h-5 my-auto align-end" src="<c:url value = "/public/images/cross.png"/>"></div>
                <div class="flex flex-row bg-white shadow rounded-lg p-3 font-sans font-bold">
                    <img class="w-5 h-5 mr-4 my-auto " src="<c:url value = "/public/images/attention.png"/>">
                    <p>Estas a punto de desestimar la denuncia de <c:url value="${complainer.username.get()}"/>. Recuerda que el veredicto no es reversible, de estar equivocado deberás esperar a que vuelvan a denunciar al usuario para vetarlo. </p>
                </div>
                <form:textarea path="comments" class="min-w-full h-32 rounded-lg mx-auto p-5 mt-5" placeholder="Escribe un motivo [TRADUCIR]"></form:textarea>
                <button class="mt-3 w-1/5 mx-auto bg-frost rounded-lg text-white p-3">Enviar</button>
            </form:form>
        </div>
    </div>
</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>
<div class="shape-blob" style="left: 5%; top: 80%"></div>


</body>
</html>

<script>
    function deleteSemiForms(){
        if (!document.getElementById("kickoutForm").classList.contains("hidden"))
            document.getElementById("kickoutForm").classList.add("hidden");
        if (!document.getElementById("dismissForm").classList.contains("hidden"))
            document.getElementById("dismissForm").classList.add("hidden");
        if (document.getElementById("dismissButton").classList.contains("underline"))
            document.getElementById("dismissButton").classList.remove("underline");
        if (document.getElementById("kickoutButton").classList.contains("underline"))
            document.getElementById("kickoutButton").classList.remove("underline");
    }

    function showOnlyKickoutForm(){
        if (document.getElementById("kickoutForm").classList.contains("hidden"))
            document.getElementById("kickoutForm").classList.remove("hidden");
        if (!document.getElementById("dismissForm").classList.contains("hidden"))
            document.getElementById("dismissForm").classList.add("hidden");
        if (document.getElementById("dismissButton").classList.contains("underline"))
            document.getElementById("dismissButton").classList.remove("underline");
        if (!document.getElementById("kickoutButton").classList.contains("underline"))
            document.getElementById("kickoutButton").classList.add("underline");
    }

    function showOnlyDismissForm(){
        if (document.getElementById("dismissForm").classList.contains("hidden"))
            document.getElementById("dismissForm").classList.remove("hidden");
        if (!document.getElementById("kickoutForm").classList.contains("hidden"))
            document.getElementById("kickoutForm").classList.add("hidden");
        if (document.getElementById("kickoutButton").classList.contains("underline"))
            document.getElementById("kickoutButton").classList.remove("underline");
        if (!document.getElementById("dismissButton").classList.contains("underline"))
            document.getElementById("dismissButton").classList.add("underline");
    }
</script>
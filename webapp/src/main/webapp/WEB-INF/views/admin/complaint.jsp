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
<div class="flex flex-row divide-x divide-polard">
    <div class="flex flex-col  ml-72 mr-20">
        <div class="flex">
            <div class="flex flex-col mt-10">
                <h2 class="font-sans text-4xl font-boldfont-sans font-semibold text-5xl"><messages:message code="claim"/> # <c:url value="${complain.complainId}"/> </h2>
                <h2 class="font-sans font-medium text-polard text-2xl"><messages:message code="carriedOutOn"/> <c:url value="${complain.date}"/></h2>
            </div>
        </div>
        <div class="flex flex-col mt-10">
            <h1 class="font-sans font-medium text-polard text-2xl r"><messages:message code="claimDescription"/>:</h1>
            <p class="rounded-lg"><c:url value="${complain.complainerComments.get()}"/></p>
        </div>
        <div class="flex flex-col mt-6">
            <h1 class="font-sans font-medium text-polard text-2xl"><messages:message code="claimUser"/>:</h1>
            <c:choose>
                <c:when test="${complain.complainer.username.get() != null}">
                    <p class="rounded-lg text-xl"><c:url value="${complain.complainer.username.get()}"/></p>
                    <p class="rounded-lg text-gray-400"><messages:message code="lastTimeActive"/>: <c:url value="${complainer.lastLogin.toLocalDate()}"/></p>
                </c:when>
                <c:otherwise>
                    <p class="rounded-lg text-lg"><c:url value="${complainer.email}"/></p>
                </c:otherwise>
            </c:choose>

        </div>
        <div class="flex flex-row mx-auto">
                <a href="<c:url value="/admin"/>" class="bg-frost text-white p-3 font-sans rounded-lg mx-auto mt-10"> <messages:message code="backToPendingClaims"/></a>
<%--                <div class="flex flex-row  mx-auto mt-10">--%>
<%--                    <c:url value="/admin/selfassign/${complain.complainId}" var="postUrl"/>--%>
<%--                    <form:form method="post" action="${postUrl}" cssClass="flex my-auto mx-3">--%>
<%--                        <button type="submit" class="bg-frostdr text-white mx-auto p-3 rounded-md font-sans"><messages:message code="assignMe"/></button>--%>
<%--                    </form:form>--%>
<%--                </div>--%>
        </div>

    </div>
    <c:if test="${trade!=null}">
    <div class="flex flex-col mt-6">
        <h1 class="font-sans font-medium text-polard text-2xl text-center"><messages:message code="tradeDetails"/></h1>
        <div class="py-12 px-20 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard mt-3 mx-20">
            <h1 class="font-sans font-medium text-polard text-xl text-center "><messages:message code="trade"/> #
            <c:out value="${trade.tradeId}"/></h1>
            <h1 class="font-sans font-medium text-polard text-m text-center "><messages:message code="carriedOutOverOffer"/> #<c:out value="${trade.offer.offerId}"/></h1>
            <div class="flex flex-col mx-auto mt-5">
                <h2 class="font-sans font-polard font-semibold text-2xl mb-3 text-center"><messages:message code="participants"/></h2>
                <p class="font-sans font-polard"><b><messages:message code="buyer"/></b> <c:out value="${trade.buyer.userAuth.username}"/></p>
                <p class="font-sans font-polard"><b><messages:message code="seller"/></b> <c:out value="${trade.offer.seller.userAuth.username}"/></p>
            </div>
            <div class="flex flex-col mx-auto mt-3">
                <h2 class="font-sans font-semibold font-polard text-2xl text-center "><messages:message code="offeredAmount"/></h2>
                <h3 class="text-xl font-sans font-polard text-center"><c:out value="${trade.quantity}"/> ARS</h3>
            </div>
            <div class="flex flex-col mx-auto mt-3">
                <h2 class="font-sans font-semibold font-polard text-2xl text-center "><messages:message code="tradeState"/></h2>
                <h3 class="text-xl font-sans font-polard text-center"><messages:message code="${trade.status}"/></h3>
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
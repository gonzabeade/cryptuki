<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<c:set var="emailPlaceholder"><messages:message code="placeholder.email"/></c:set>
<c:set var="continue"><messages:message code="continue"/></c:set>
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
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp"/>
<div>
    <h1 class="text-center text-4xl font-semibold font-sans text-polar mt-10"><messages:message code="weSentLinkToRecoverPassword"/></h1>
    <c:url value="/passwordRecovery" var="postPath"/>
    <%--@elvariable id="EmailForm" type="ar.edu.itba.paw.cryptuki.form"--%>
    <form:form modelAttribute="EmailForm" action="${postPath}" method="post">
        <div class="flex flex-col items-center justify-center mt-20">
            <div class="flex flex-col mb-10 items-center justify-center">
                <form:errors path="email" cssClass="formError mx-auto text-red-500" element="p"/>
                <div class="mb-5">
                    <form:label path="email" class="text-center text-2xl font-bold font-sans text-polar my-2"><messages:message code="insertEmailAddress"/></form:label>
                </div>
                <div>
                    <form:input type="text" path="email" placeholder="${emailPlaceholder}" cssClass="rounded-lg p-3"/>
                </div>
            </div>
            <div>
                <input type="submit" value="${continue}" class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg"/>
            </div>
        </div>
    </form:form>
</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>






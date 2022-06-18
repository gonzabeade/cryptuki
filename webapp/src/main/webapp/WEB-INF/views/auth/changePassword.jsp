<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url  value="/public/js/successMessageShow.js"/>"></script>
    <script src="<c:url value="/public/js/formValidations.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">

<% request.setCharacterEncoding("utf-8"); %>
<div id="confirmationToggle" class="hidden">
    <c:set var="changedPass"> <messages:message code="passwordChangedSuccesfully"/></c:set>
    <jsp:include page="../../components/confirmationToggle.jsp">
        <jsp:param name="title" value="${changedPass}"/>
    </jsp:include>
</div>
<div class="flex mt-10 mb-10">
    <jsp:include page="../../components/auth/changePasswordForm.jsp"/>
</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class=" flex  flex-col justify-center mx-10">
    <% request.setCharacterEncoding("UTF-8"); %>
    <c:if test="${completed == true}">
        <jsp:include page="../components/confirmationToggle.jsp">
            <jsp:param name="title" value="Cargaste tu reclamo con éxito."/>
        </jsp:include>
    </c:if>
    <% request.setCharacterEncoding("UTF-8"); %>
    <jsp:include page="../components/contactText.jsp"/>
</div>
<div class="flex justify-center">
    <% request.setCharacterEncoding("utf-8"); %>
    <jsp:include page="../components/complainForm.jsp">
        <jsp:param name="url" value="support"/>
        <jsp:param name="tradeId" value="${tradeId}"/>
        <jsp:param name="username" value="${username}"/>
        <jsp:param name="complainerId" value="${complainerId}"/>
    </jsp:include>
</div>


</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>

<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/buyer/buyerHeader.jsp"/>
<div class="flex flex-col">
    <div class="flex mt-10 mb-5 ml-20">
        <a href="<c:url value="/user"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg"><messages:message code="goBack"/></a>
        <h2 class="text-center text-3xl font-semibold font-sans text-polar my-auto ml-10"><messages:message code="yourComplaints"/></h2>
    </div>
    <div  class="flex flex-col justify-center">
                <c:forEach var="complaint" items="${complaintsList}">
                    <li class="list-none mx-20">
                        <% request.setCharacterEncoding("utf-8"); %>
                        <jsp:include page="../components/complainCard.jsp">
                            <jsp:param name="status" value="${complaint.status}"/>
                            <jsp:param name="message" value="${complaint.complainerComments.get()}"/>
                            <jsp:param name="tradeId" value="${complaint.tradeId.get() }" />
                            <jsp:param name="date" value="${complaint.date}"/>
                        </jsp:include>
                    </li>
                </c:forEach>
    </div>
    <div class="flex flex-col">
        <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../components/paginator.jsp">
                <jsp:param name="activePage" value="${activePage}"/>
                <jsp:param name="pages" value="${pages}"/>
                <jsp:param name="baseUrl" value="/complaints"/>
            </jsp:include>
            <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: ${pages}</h1>
    </div>
</div>
</body>
</html>



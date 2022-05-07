<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>

<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-col">
    <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-10">Tus reclamos: </h2>
    <div  class="flex flex-col mx-80 justify-center">
        <div>
            <c:forEach var="complaint" items="${complaintsList}">
                <li>
                    <jsp:include page="../components/complain_card.jsp">
                        <jsp:param name="status" value="${complaint.status}"/>
                        <jsp:param name="message" value="${complaint.complainerComments.get()}"/>
                        <jsp:param name="tradeId" value="${complaint.tradeId.orElse(-1)}" />
                    </jsp:include>

                </li>
            </c:forEach>
        </div>
        <div class="flex flex-col">
            <jsp:include page="../components/paginator.jsp">
                <jsp:param name="activePage" value="${activePage}"/>
                <jsp:param name="pages" value="${pages}"/>
            </jsp:include>
            <h1 class="mx-auto text-gray-400 mx-auto">Total de páginas: ${pages}</h1>
        </div>
    </div>
</div>
</body>
</html>



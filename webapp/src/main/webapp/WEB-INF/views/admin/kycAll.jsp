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
    <script src="<c:url  value="/public/js/pagination.js"/>"></script>
    <script src="<c:url value="/public/js/successMessageShow.js"/>"></script>
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
    <div class="flex flex-col ml-80 my-10 h-screen w-screen">
        <h1 class="font-sans text-4xl font-bold"><messages:message code="kyccheck"/></h1>


        <c:if test="${pendingKycs.isEmpty()}">
            <h1 class="mt-10 font-sans text-2xl font-bold"><messages:message code="noKycPending"/> </h1>
        </c:if>

        <div id="confirmationToggle" class="hidden">
            <c:set var="placeholder"><messages:message code="kycSubmission"/> </c:set>
            <jsp:include page="../../components/confirmationToggle.jsp">
                <jsp:param name="title" value="${placeholder}"/>
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
        <div class="flex flex-col">
            <jsp:include page="../../components/paginator.jsp">
                <jsp:param name="activePage" value="${activePage}"/>
                <jsp:param name="pages" value="${pages}"/>
                <jsp:param name="baseUrl" value="/admin/kyccheck/"/>
            </jsp:include>
            <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: <c:out value="${pages}" /> </h1>        </div>



    </div>

</div>
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>

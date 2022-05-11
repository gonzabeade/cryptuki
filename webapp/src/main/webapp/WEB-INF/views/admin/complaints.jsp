<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="complainList" scope="request" type="java.util.Collection"/>

<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
  <script src="<c:url  value="/public/js/filterLink.js"/>"></script>
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
  <div class="flex flex-col ml-96 my-10 h-screen w-screen">
    <h1 class="font-sans text-4xl font-bold">${title}</h1>
    <div class="flex flex-row mt-10 divide-x h-full">
      <% request.setCharacterEncoding("utf-8"); %>
      <jsp:include page="../../components/admin/filters.jsp">
        <jsp:param name="baseUrl" value="${baseUrl}"/>
      </jsp:include>
      <div class="p-10 w-2/3  flex flex-col">

        <c:forEach var="complain" items="${complainList}">
          <li style="list-style-type: none" class="my-3">
            <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../../components/admin/cardComplaint.jsp">
              <jsp:param name="date" value="${complain.date}"/>
              <jsp:param name="complainId" value="${complain.complainId}"/>
              <jsp:param name="complainerUsername" value="${complain.complainerUsername}"/>
              <jsp:param name="complainerComments" value="${complain.complainerComments.get()}"/>
              <jsp:param name="complainStatus" value="${complain.status}"/>
            </jsp:include>
          </li>
        </c:forEach>

        <jsp:include page="../../components/paginator.jsp">
          <jsp:param name="activePage" value="${activePage}"/>
          <jsp:param name="pages" value="${pages}"/>
          <jsp:param name="baseUrl" value="${baseUrl}"/>
        </jsp:include>
        <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: ${pages}</h1>
      </div>
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

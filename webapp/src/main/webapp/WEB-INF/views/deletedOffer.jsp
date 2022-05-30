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
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <title>cryptuki</title>
  <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:include page="../components/seller/sellerHeader.jsp"/>
<div class="flex flex-row  mt-10">
  <div class="flex flex-col mx-auto">
    <div class="mx-auto">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-60 w-60 ml-14" fill="none" viewBox="0 0 24 24" stroke="#A3BE8C" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
      </svg>
      <h1 class="text-ngreen text-4xl text-center"><messages:message code="done"/>!</h1>
    </div>
    <div class="mx-auto  text-polard text-2xl mt-10">
     <messages:message code="offerWasDeleted"/>
    </div>
    <a href="<c:url value="/seller/"/>" class="bg-frost text-white p-3 font-sans rounded-lg mx-auto mt-10"><messages:message code="returnMyAdvertisements"/></a>
  </div>
</div>
</body>
</html>



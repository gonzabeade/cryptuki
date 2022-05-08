<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
  <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
  <script src="<c:url value="/public/js/profilePic.js"/>"></script>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
  <title>cryptuki</title>
  <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden flex flex-col">
<jsp:include page="../components/header.jsp">
  <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex">
  <h1 class="mx-auto my-10 text-4xl font-semibold font-sans text-polar">Mi Perfil</h1>
</div>
<div class=" p-12 mx-96 rounded-lg bg-stormd/[0.9] flex  border-2 border-polard flex flex-row">
  <div class="flex flex-col height-auto mx-auto my-auto">
    <div class="flex flex-col">
      <c:url value="/profilePicSelector" var="postUrl"/>
      <form:form modelAttribute="ProfilePicForm" action="${postUrl}" method="post" enctype="multipart/form-data">
        <form:errors path="multipartFile" cssClass=" mx-auto text-red-500"/>
        <div class="group flex flex-col">
          <form:label path="multipartFile">
            <div class="flex flex-col mx-auto">
              <img src="<c:url value="/profilepic/${username}"/>"  class=" shadow rounded-full w-36 object-cover mx-auto group-hover:opacity-60" >
              <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 invisible group-hover:visible mx-auto -mt-20" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </div>
          </form:label>
          <form:input type="file" path="multipartFile" cssClass="invisible before:bg-red-400 w-0" onchange="showSendButton()"/>
        </div>
        <button type="submit" id="sendProfile" class="invisible bg-frostdr text-white  mt-7 p-3 rounded-md font-sans  mx-auto active:cursor-progress">Guardar</button>
      </form:form>
      <p class="mx-auto font-bold text-polard text-2xl -mt-10"><c:out value="${username}"/></p>
    </div>
  </div>

  <div class="flex flex-col">
    <div class="flex  rounded-lg h-12 mb-5 mt-5 mr-5 mx-10">
      <div class="flex">
        <h2 class="text-2xl font-semibold font-sans text-polar my-auto">Correo electronico: </h2>
      </div>
      <div class="flex">
        <h2 class="text-xl font-semibold font-sans text-polar text-left my-auto ml-2"><c:out value="${user.email}"/></h2>
      </div>
    </div>
    <div class="flex  rounded-lg h-12 mb-5 mt-5 mr-5 mx-10">
      <div class="flex">
        <h2 class="text-2xl font-semibold font-sans text-polar text-left my-auto" >Teléfono : </h2>
      </div>
      <div class="flex">
        <h2 class="text-xl font-semibold font-sans text-polar my-auto ml-2"><c:out value="${user.phoneNumber}"/></h2>
      </div>
    </div>
    <div class="flex  rounded-lg h-12 mb-5  mt-5 mr-5 mx-10">
      <div class="flex">
        <h2 class="text-2xl font-semibold font-sans text-polar text-left my-auto">Cantidad de trades:</h2>
      </div>
      <div class="flex">
        <h2 class="text-xl font-semibold font-sans text-polar my-auto ml-2"><c:out value="${user.ratingCount}"/></h2>
      </div>
    </div>
    <div class="flex mt-10" >
      <div>
        <a href="<c:url value="/changePassword"/>" class="mx-36 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center">Cambiar contraseña</a>
      </div>
    </div>
  </div>



</div>
  <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-10">Tus transacciones: </h2>
  <div  class="flex flex-col mx-80 justify-center">
    <div>
      <c:forEach var="trade" items="${tradeList}">
      <li>
        <jsp:include page="../components/trade_card.jsp">
          <jsp:param name="username" value="${username}"/>
          <jsp:param name="sellerUsername" value="${trade.sellerUsername}"/>
          <jsp:param name="buyerUsername" value="${trade.buyerUsername}"/>
          <jsp:param name="quantity" value="${trade.quantity}"/>
          <jsp:param name="cryptoCurrencyCode" value="${trade.cryptoCurrency.code}"/>
          <jsp:param name="askedPrice" value="${trade.askedPrice}"/>
          <jsp:param name="tradeId" value="${trade.tradeId}"/>
        </jsp:include>

     </li>
    </c:forEach>
    </div>
    <div class="flex flex-col mt-3">
      <jsp:include page="../components/paginator.jsp">
        <jsp:param name="activePage" value="${activePage}"/>
        <jsp:param name="pages" value="${pages}"/>
        <jsp:param name="baseUrl" value="/user"/>
      </jsp:include>
      <h1 class="mx-auto text-gray-400 mx-auto mt-3">Total de páginas: ${pages}</h1>
    </div>
</div>


<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>
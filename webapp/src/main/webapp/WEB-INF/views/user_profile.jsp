<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
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
<jsp:include page="../components/header.jsp">
  <jsp:param name="username" value="${username}"/>
</jsp:include>
<h1 class="text-center text-4xl font-semibold font-sans text-polar mt-5 mb-5">Hola , <c:out value="${username}"/></h1>

<div class=" py-12 px-64 rounded-lg bg-stormd/[0.9] flex  justify-center border-2 border-polard">
  <div class="flex flex-col basis-3/4">
    <div class="flex justify-between rounded-lg h-12 mb-5 mt-5 mr-5">
      <div class="flex basis-1/2 items-center justify-center ">
        <h2 class="text-3xl font-semibold font-sans text-polar" >Correo electronico: </h2>
      </div>
      <div class="flex items-center justify-center  bg-storm rounded-lg basis-1/2">
        <h2 class="text-3xl font-semibold font-sans text-polar"><c:out value="${user.email}"/></h2>
      </div>
    </div>
    <div class="flex justify-between rounded-lg h-12 mb-5 mr-5">
      <div class="flex basis-1/2 items-center justify-center " >
        <h2 class="text-3xl font-semibold font-sans text-polar">Nùmero de telefono: </h2>
      </div>
      <div class="flex items-center justify-center  bg-storm rounded-lg basis-1/2" >
        <h2 class="text-3xl font-semibold font-sans text-polar"><c:out value="${user.phoneNumber}"/></h2>
      </div>
    </div>
    <div class="flex justify-between rounded-lg h-12 mb-5 mr-5">
      <div class="flex basis-1/2 items-center justify-center  justify-center ">
        <h2 class="text-3xl font-semibold font-sans text-polar">Cantidad de trades:</h2>
      </div>
      <div class="flex items-center justify-center bg-storm rounded-lg basis-1/2">
        <h2 class="text-3xl font-semibold font-sans text-polar"><c:out value="${user.ratingCount}"/></h2>
      </div>
    </div>
    <div class="flex justify-around mt-36" >
      <div>
        <a href="<c:url value="/changePassword"/>" class="mx-36 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center">Cambiar contraseña</a>
      </div>
    </div>
  </div>

  <div class="flex flex-col basis-1/4 height-auto ml-5">
    <img src="<c:url value="/profilepic/${username}"/>"  class=" shadow rounded-full" />
      <a class="mx-auto mt-3 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center" href="<c:url value="/profilePicSelector"/>">
        Editar foto de perfil
      </a>
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
    <div class="flex flex-col">
      <jsp:include page="../components/paginator.jsp">
        <jsp:param name="activePage" value="${activePage}"/>
        <jsp:param name="pages" value="${pages}"/>
        <jsp:param name="baseUrl" value="/user"/>
      </jsp:include>
      <h1 class="mx-auto text-gray-400 mx-auto">Total de páginas: ${pages}</h1>
    </div>
</div>


<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>
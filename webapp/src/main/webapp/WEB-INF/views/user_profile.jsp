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
<h1 class="text-center text-4xl font-semibold font-sans text-polar mt-20 mb-20">Hola , <c:out value="${username}"/></h1>
<div class="flex flex-col items-center justify-center">
  <div  class="basis-1/4">
    <h2 class="text-center text-4xl font-semibold font-sans text-polar mb-10">Tu perfil:</h2>
  </div>
  <div class="flex flex-col items-center basis-3/4">
      <div class="w-1/12">
        <img src="<c:url value="/profilepic/${username}"/>"  class=" shadow rounded-full" />
      </div>
    <div class="flex basis-1/4 justify-center">
      <a class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 m-2 hover:bg-frostdr/[.6] hover:border-2 hover:border-frostdr text-white" href="<c:url value="/profilePicSelector" />">
        Editar foto de perfil
      </a>
    </div>
  </div>
</div>

<div class="flex flex-col mx-48 bg-frostl justify-around rounded-lg">
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
  <div class="flex justify-between mb-30" >
    <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Cambiar contraseña</button>
  </div>
</div>
  <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-10">Ultimas transacciones: </h2>
  <div  class="flex justify-center flex-nowrap">
    <div>
      <c:forEach var="trade" items="${tradeList}">
      <li>
        <jsp:include page="../components/trade_card.jsp">
          <jsp:param name="user" value="${trade.sellerUsername}"/>
          <jsp:param name="quantity" value="${trade.quantity}"/>
        </jsp:include>

     </li>
    </c:forEach>
    </div>
</div>

<div>

</div>
  <h2>Tus reclamos pendientes</h2>

<div>

</div>

<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>
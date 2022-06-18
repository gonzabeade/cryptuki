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

<body class="bg-storml">
  <jsp:include page="../../components/admin/header.jsp"/>
  <c:url value="/admin/kyccheck" var="kyccheckUrl" />
<%--  TODO CHECK PORQUE  el ML ESTA EN 80 ESTO ESTA ASI--%>

  <div class="flex flex-col ml-80 p-10">

    <!--Title -->
    <div class="flex flex-row">
      <h1 class="font-sans text-4xl font-bold"><messages:message code="identityVerify"/></h1>
      <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 my-auto ml-2 cursor-pointer" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" onclick="toggleInfo('info')">
        <path stroke-linecap="round" stroke-linejoin="round" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
    </div>

    <!--Toggle -->
    <ol  id="info" class=" hidden bg-white hidden p-3 rounded-lg w-fit shadow-xl z-20 flex flex-col mt-14 absolute" >
      <div class="flex  justify-end" onclick="hideToggle('info')">
        <h2 class="font-sans font-bold mr-16"><messages:message code="checkAllRequirementsAreOK"/> </h2>
        <p class="mr-2 font-sans font-bold"><messages:message code="close"/> </p>
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 cursor-pointer" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" >
          <path stroke-linecap="round" stroke-linejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
      </div>
      <div class="mt-3">
        <li><messages:message code="namesMatch" /></li>
        <li><messages:message code="surnamesMatch"/></li>
        <li><messages:message code="docMatch"/></li>
        <li><messages:message code="nationalityMatch"/></li>
        <li><messages:message code="typeMatch"/></li>
        <li><messages:message code="picMatch"/></li>
      </div>
    </ol>

    <%--Page Body--%>
    <div class="flex w-full my-10 z-10">

      <%--Left panel--%>
      <div class="flex flex-col w-3/5">
        <%-- Picture selectors --%>
        <div class="flex flex-row w-full h-[60px]">

          <div class="w-1/2 h-full mx-2 shadow-l rounded-lg bg-[#FAFCFF] hover:bg-gray-300 cursor-pointer" onclick="showIdPhoto()">
            <h2 id="idphotoText" class="underline underline-offset-2 font-sans text-xl font-bold text-center mt-4"><messages:message code="pictureOfId"/> </h2>
          </div>

          <div class="w-1/2 h-full mx-2 shadow-l rounded-lg bg-[#FAFCFF] hover:bg-gray-300 cursor-pointer" onclick="showValidationPhoto()">
            <h2 id="validationphotoText" class="underline-offset-2 font-sans text-xl font-bold text-center mt-4"><messages:message code="pictureWithFace"/> </h2>
          </div>
        </div>

          <%-- Picture show --%>
        <div class="w-full h-4/5 mt-5">
            <div id="idphoto" class="border-2 border-gray-400 ">
              <img src="<c:url value="/kyc/idphoto/${kyc.user.username.get()}"/>" class=" w-full mx-auto">
            </div>
            <div id="validationphoto" class="hidden border-2 border-gray-400" >
              <img src="<c:url value="/kyc/validationphoto/${kyc.user.username.get()}"/>" class="w-full mx-auto">
            </div>
        </div>


      </div>


        <%--Right panel--%>
      <div class="flex flex-col w-1/3 mx-auto">
        <div class="flex flex-col shadow-xl rounded-lg py-10 bg-[#FAFCFF] px-5 flex justify-center mx-auto w-3/4">
          <div class="flex flex-col">
              <h3 class="font-sans font-bold text-lg mb-10 text-xl"><messages:message code="userInformation"/></h3>
              <div class="flex flex-row">
                <h4 class="font-sans font-bold mr-2"><messages:message code="names"/></h4>
                <h3><c:out value="${kyc.givenNames}"/></h3>
              </div>
              <div class="flex flex-row ">
                <h4 class="font-sans font-bold mr-2"><messages:message code="surnames"/> </h4>
                <h3><c:out value="${kyc.surnames}"/></h3>
              </div>
              <div class="flex flex-row ">
                <h4 class="font-sans font-bold mr-2"><messages:message code="countryOfEmission"/></h4>
                <h3><c:out value="${kyc.emissionCountry}"/></h3>
              </div>
              <div class="flex flex-row">
                <h4 class="font-sans font-bold mr-2"> <messages:message code="idNumber"/> </h4>
                <h3><c:out value="${kyc.idCode}"/></h3>
              </div>
              <div class="flex flex-row">
                <h4 class="font-sans font-bold mr-2"><messages:message code="idType"/> </h4>
                <h3 class="mr-2"><messages:message code="IdType.${kyc.idType}"/></h3>
              </div>

          </div>
        </div>
        <div class="flex flex-row mx-auto mt-10">
          <c:url value="/admin/kyccheck/approve/${kyc.kycId}" var="approveUrl"/>
          <form:form method="post" action="${approveUrl}">
            <button class="bg-ngreen rounded-lg text-white p-3 mr-10" type="submit"><messages:message code="approve"/></button>
          </form:form>
          <div onclick="toggleInfo('confirmation')">
            <button class="bg-nred rounded-lg text-white p-3"><messages:message code="reject"/></button>
          </div>
        </div>
      </div>
    </div>

    <!--Confirmation Toggle-->
    <div id="confirmation" class="bg-white shadow-xl p-10 hidden justify-center rounded-lg z-20 mt-52 absolute w-[70%]">
      <div class="flex flex-col justify-center">
        <h2 class="text-2xl font-sans font-bold"> <messages:message code="rejectionMotif"/> </h2>
        <c:url value="/admin/kyccheck/reject/${kyc.kycId}" var="rejectUrl"/>
        <form:form method="post" action="${rejectUrl}" modelAttribute="kycApprovalForm">
          <div class="flex flex-col">
              <form:errors path="message" cssClass=" mx-auto text-red-500"/>
              <form:hidden path="username" value="${kyc.user.username.get()}"/>
              <form:textarea path="message" cssClass="border-2 border-gray-400 my-10 h-[14rem] p-2"/>
              <div class="flex flex-row mx-auto justify-center">
                <button class="bg-frostdr rounded-lg text-white p-3 mr-10" type="submit"> <messages:message code="send"/> </button>
                <p class="bg-gray-400 rounded-lg text-white p-3 cursor-pointer" onclick="hideToggle('confirmation')"> <messages:message code="cancel"/></p>
              </div>
          </div>
        </form:form>
      </div>
    </div>




  </div>




</body>
<script>

  window.onload = function errorMessage() {
     if (window.location.href.indexOf("reject") > -1) {
       toggleInfo("confirmation")
     }
  }


  function showIdPhoto(){
      var element = document.getElementById("idphoto")
      element.classList.remove('hidden')
      element = document.getElementById("idphotoText")
      element.classList.add('underline')
      element = document.getElementById("validationphotoText")
      element.classList.remove('underline')
      element = document.getElementById("validationphoto")
      element.classList.add('hidden')
  }

  function showValidationPhoto(){
    var element = document.getElementById("idphoto")
    element.classList.add('hidden')
    element = document.getElementById("idphotoText")
    element.classList.remove('underline')
    element = document.getElementById("validationphotoText")
    element.classList.add('underline')
    element = document.getElementById("validationphoto")
    element.classList.remove('hidden')
  }

  function toggleInfo(id){
    var element = document.getElementById(id)
    if(element.classList.contains('hidden')){
      element.classList.remove('hidden')
    }else{
      element.classList.add('hidden')
    }
  }

  function hideToggle(id) {
    var element = document.getElementById(id)
    element.classList.add('hidden')
  }

</script>
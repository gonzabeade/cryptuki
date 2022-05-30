<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="flex flex-row my-5">

  <div class="flex flex-col my-auto mr-4">
    <div class="shadow-xl flex rounded-lg bg-[#FAFCFF] h-8 w-8 mb-4">
      <img class="m-auto h-5 w-5" src="<c:url value="/public/images/edit.png"/>"/>
    </div>

    <div class="shadow-xl flex rounded-lg bg-[#FAFCFF]  h-8 w-8">
      <img class="m-auto h-5 w-5" src="<c:url value="/public/images/delete.png"/>"/>
    </div>
  </div>

  <div class="shadow-xl flex flex-col rounded-l-lg w-2/5  bg-[#FAFCFF] z-10 justify-center items-center">
    <%--    Tarjeta de anuncio--%>
      <%--      title--%>
      <div class="flex flex-row items-center mt-5">
        <h1 class="text-xl font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="2" value="${215.00}"/> ARS </h1>
        <p class="my-auto mx-2"> <messages:message code="for"/> </p>
        <h1 class="text-xl font-sans font-semibold my-auto"><c:out value="DAI"/></h1>
        <img src="<c:url value="/public/images/DAI.png"/>" alt="<c:out value="DAI"/>" class="w-5 h-5 mx-2 my-auto"/>
      </div>

    <div class="flex flex-col font-sans items-center mt-3">
      <p>Min: 1000AR$ - Max: 2000AR$</p>
    </div>

    <div class="flex flex-col font-sans items-center mt-3 mb-3">
      <p>Fecha: 25-05-2022</p>
    </div>

  </div>

  <div class="flex flex-col w-10 -ml-1">
    <div class="bg-green-200 shadow-xl rounded-lg h-1/4 hover:bg-green-300 hover:-mr-5"></div>
    <div class="bg-yellow-200 shadow-xl rounded-lg h-1/4 hover:bg-yellow-300 hover:-mr-5"></div>
    <div class="bg-red-200 shadow-xl rounded-lg h-1/4 hover:bg-red-300 hover:-mr-5"></div>
    <div class="bg-blue-200 shadow-xl rounded-lg h-1/4 hover:bg-blue-300 hover:-mr-5"></div>
  </div>

  <div class="shadow-xl flex flex-row rounded-lg w-full p-3 bg-gray-100 -ml-2 -z-10 justify-center overflow-x-scroll">
      <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
      <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>


<%--    <jsp:include page="../../components/seller/sellerTradeSummaryCard.jsp"/>--%>
<%--    <jsp:include page="../../components/seller/sellerTradeSummaryCard.jsp"/>--%>
<%--    <jsp:include page="../../components/seller/sellerTradeSummaryCard.jsp"/>--%>

  </div>

</div>
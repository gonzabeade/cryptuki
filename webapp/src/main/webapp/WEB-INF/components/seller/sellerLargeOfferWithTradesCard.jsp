<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="flex flex-row my-5 mx-5 h-2/5">

  <%--    Tarjeta de anuncio--%>
  <div class="shadow-xl w-[250px] flex flex-col rounded-lg py-10 px-4 bg-[#FAFCFF] z-20 justify-center items-center content-start">

      <div class="flex flex-col items-center mt-5 mb-5">
          <h1 class="text-[30px] font-bold font-sans">Anuncio 10</h1>
      </div>

      <div class="flex flex-row items-center">
          <h1 class="text-xl font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="2" value="${215.00}"/> ARS </h1>
          <p class="my-auto mx-2"> <messages:message code="for"/> </p>
          <h1 class="text-xl font-sans font-semibold my-auto"><c:out value="DAI"/></h1>
          <img src="<c:url value="/public/images/DAI.png"/>" alt="<c:out value="DAI"/>" class="w-5 h-5 mx-2 my-auto"/>
      </div>

    <div class="flex flex-col justify-start -ml-5 mb-3">
        <div class="flex flex-col font-sans mt-3">
            <p>Min: 1000AR$</p>
        </div>
        <div class="flex flex-col font-sans mt-3">
            <p>Max: 2000AR$</p>
        </div>
        <div class="flex flex-col font-sans mt-3">
            <p>Fecha: 25-05-2022</p>
        </div>
        <div class="flex flex-col font-sans mt-3">
            <p>Ubicación: 25-05-2022</p>
        </div>
    </div>


      <div class="flex flex-row mx-auto mt-5">
        <div class="flex rounded-lg bg-storml h-8 w-8 mr-3 hover:bg-stormd">
          <img class="m-auto h-5 w-5" src="<c:url value="/public/images/edit.png"/>"/>
        </div>

        <div class="flex rounded-lg bg-storml  h-8 w-8 hover:bg-stormd">
          <img class="m-auto h-5 w-5" src="<c:url value="/public/images/delete.png"/>"/>
        </div>
      </div>
  </div>

<%--      s--%>
  <div id="1" class="flex flex-row shadow-xl rounded-lg  w-[900px] bg-gray-100 -ml-2 z-10 p-5 overflow-x-scroll overflow-y-hidden hidden">

        <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
        <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
        <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
        <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
        <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
        <jsp:include page="../../components/verticalSellingTradeCard.jsp"/>
  </div>

  <div class="flex flex-col w-10 -ml-5 h-full" onClick="toggle(1)">
    <div class="my-auto bg-gray-400 shadow-xl rounded-lg h-5/6 hover:bg-gray-300 hover:-mr-7">
    </div>
  </div>

</div>

<script>
    function toggle(id){
        if (document.getElementById(id).classList.contains("hidden")) {
            document.getElementById(id).classList.remove("hidden");
        } else {
            document.getElementById(id).classList.add("hidden");
        }
    }
</script>

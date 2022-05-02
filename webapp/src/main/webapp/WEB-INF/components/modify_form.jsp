<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="paymentMethods" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="selectedCrypto" scope="request" type="java.lang.String"/>
<jsp:useBean id="selectedPayments" scope="request" type="java.util.ArrayList"/>
<div class="flex flex-col">

  <c:url value="${param.url}" var="postUrl"/>
  <form:form modelAttribute="uploadOfferForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]" onsubmit="event.preventDefault(); preventMinMaxFromSubmitting();">
    <div class="flex flex-col justify-center">
      <form:errors path="cryptocurrency" cssClass=" mx-auto text-red-500"/>
      <form:label  path="cryptocurrency" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Criptomoneda*</form:label>
      <div class="flex flex-row justify-center mx-auto">
        <form:select path="cryptocurrency" class="rounded-lg p-3" onchange="updateVars(this.value)">
          <c:forEach var="coin" items="${cryptocurrencies}">
            <c:choose>
              <c:when test="${selectedCrypto == coin.code}">
                <option value="${coin.code}" selected>
                  <c:out value="${coin.name}"/>
                </option>
              </c:when>
              <c:otherwise>
                <form:option value="${coin.code}">
                  <c:out value="${coin.name}"/>
                </form:option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </form:select>
      </div>
    </div>
    <div class="flex flex-col justify-center mt-6">
      <h2 class="text-xl font-sans text-polard font-semibold mb-3 text-center flex flex-row justify-center ">Límites*</h2>
      <form:errors path="minAmount" cssClass=" mx-auto text-red-500"/>
      <form:errors path="maxAmount" cssClass=" mx-auto text-red-500"/>
      <p class="text-red-500 hidden text-center" id="minMaxValidation">El valor mínimo debe ser menor al máximo</p>
      <div class="flex flex-row justify-center">
        <div>

          <form:label  path="minAmount" class="text-lg font-sans text-polard font-semibold mb-3 text-center flex flex-row justify-center ">Min en <p id="minCoin" class="mx-2"><c:out value="${selectedCrypto}"/></p></form:label>
          <div class="flex flex-row justify-center mx-auto">
            <form:input type="number" path="minAmount" class="h-10 justify-center rounded-lg p-3 mx-5" step=".00000001" onchange="checkMinMax()"/>
            <!-- <h1 class="my-auto" id="coinAmount">~0.00000</h1> -->
          </div>
        </div>
        <div class="my-12">
          -
        </div>
        <div>
          <form:label  path="maxAmount" class="text-lg font-sans text-polard font-semibold mb-3 text-center flex flex-row justify-center">Max en <p id="maxCoin" class="mx-2"><c:out value="${selectedCrypto}"/></p></form:label>
          <div class="flex flex-row justify-center mx-auto">
            <form:input type="number" path="maxAmount" class="h-10 justify-center rounded-lg p-3 mx-5" step=".00000001" onchange="checkMinMax()"/>
            <!-- <h1 class="my-auto" id="coinAmount">~0.00000</h1> -->
          </div>
        </div>
      </div>

    </div>


    <div class="flex flex-col mx-auto">
      <form:errors path="paymentMethods" cssClass="text-red-500 mx-auto"/>
      <form:label path="paymentMethods" class="text-xl font-sans text-polard font-semibold mb-3 text-center ">Medios de Pago*</form:label>
      <div class="flex flex-row justify-center mx-auto">
        <c:forEach items="${paymentMethods}" var="paymentMethod">
          <c:choose>
            <c:when test="${selectedPayments.contains(paymentMethod.name)}">
              <form:label path="paymentMethods" for="${paymentMethod.name}" cssClass="checked border-2 border-frostdr mr-4 ml-2 p-3 my-auto bg-[#FAFCFF] font-sans rounded-lg text-center text-polard font-medium min-w-[25%] hover:cursor-pointer  " onclick="changeBorderColor(this)">
                <div class="flex flex-row">
                  <img src="<c:url value="/public/images/${paymentMethod.name}.png"/>" class="w-5 h-5 mr-2 my-auto"/>
                  <c:out value="${paymentMethod.description}"/>
                </div>
              </form:label>
            </c:when>
            <c:otherwise>
              <form:label path="paymentMethods" for="${paymentMethod.name}" cssClass="mr-4 ml-2 p-3 my-auto bg-[#FAFCFF] font-sans rounded-lg text-center text-polard font-medium min-w-[25%] hover:cursor-pointer border-2 border-[#FAFCFF]" onclick="changeBorderColor(this)">
                <div class="flex flex-row">
                  <img src="<c:url value="/public/images/${paymentMethod.name}.png"/>" class="w-5 h-5 mr-2 my-auto"/>
                  <c:out value="${paymentMethod.description}"/>
                </div>
              </form:label>
            </c:otherwise>
          </c:choose>

          <form:checkbox path="paymentMethods" id="${paymentMethod.name}" value="${paymentMethod.name}" cssClass="hidden"/>
        </c:forEach>
      </div>
    </div>
    <div class="flex flex-col mt-6">
      <form:errors path="price" cssClass="text-red-500 mx-auto"/>
      <form:label path="price" class="text-xl font-sans text-polard font-semibold mb-3 text-center ">Precio por moneda en ARS*</form:label>
      <div class="flex flex-col justify-center mx-96">
        <form:input type="number" path="price" class="h-10 justify-center rounded-lg p-3 mx-auto " step=".01"/>
        <!-- <h1 class="my-auto mx-auto" id="price">~0.00000 ARS</h1> -->
      </div>
    </div>
    <div class="flex flex-row p-5 mx-60 mt-10">
      <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="<c:url value="/"/>">Cancelar</a>
      <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress">Guardar</button>
    </div>
  </form:form>
</div>
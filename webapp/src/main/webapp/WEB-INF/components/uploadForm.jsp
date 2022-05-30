<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.lang.Iterable"/>
<jsp:useBean id="paymentMethods" scope="request" type="java.lang.Iterable"/>
<div class="flex flex-col">
    <c:url value="/seller/upload" var="postUrl"/>
    <%--@elvariable id="uploadOfferForm" type="ar.edu.itba.paw.cryptuki.form.UploadOfferForm"--%>
    <form:form modelAttribute="uploadOfferForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">

        <div class="flex flex-col justify-center">
            <form:errors path="cryptocurrency" cssClass=" mx-auto text-red-500"/>
            <form:label  path="cryptocurrency" class="text-xl font-sans text-polard font-semibold mb-3 text-center"><messages:message code="cryptocurrency"/>*</form:label>
            <div class="flex flex-row justify-center mx-auto">
            <form:select path="cryptocurrency" class="rounded-lg p-3" onchange="updateVars(this.value)">
                <option disabled selected><messages:message code="chooseAnOption"/></option>
                <c:forEach var="coin" items="${cryptocurrencies}">
                    <form:option value="${coin.code}">
                        <c:out value="${coin.commercialName}"/>
                    </form:option>
                </c:forEach>
            </form:select>
            </div>
        </div>
        <div class="flex flex-col mt-6">
                <form:errors path="price" cssClass="text-red-500 mx-auto"/>
                <form:label path="price" class="text-xl font-sans text-polard font-semibold mb-3 text-center "><messages:message code="priceByCurrency"/> ARS*</form:label>
                <div class="flex flex-col justify-center mx-96">
                    <form:input type="number" path="price" class="h-10 justify-center rounded-lg p-3 mx-auto " step=".01"/>
                    <!-- <h1 class="my-auto mx-auto" id="price">~0.00000 ARS</h1> -->
                </div>
        </div>
        <div class="flex flex-col justify-center mt-6">
            <h2 class="text-xl font-sans text-polard font-semibold mb-3 text-center flex flex-row justify-center "><messages:message code="limits"/>*</h2>
            <form:errors path="minAmount" cssClass=" mx-auto text-red-500"/>
            <form:errors path="maxAmount" cssClass=" mx-auto text-red-500"/>
            <form:errors cssClass="text-red-500 mx-auto"/>
            <div class="flex flex-row justify-center">
                <div>
                    <form:label  path="minAmount" class="text-lg font-sans text-polard font-semibold mb-3 text-center flex flex-row justify-center "><messages:message code="minIn"/> <p id="minCoin" class="mx-2">BTC</p></form:label>
                    <div class="flex flex-row justify-center mx-auto">
                        <form:input type="number" path="minAmount" class="h-10 justify-center rounded-lg p-3 mx-5" step=".00000001"/>
                        <!-- <h1 class="my-auto" id="coinAmount">~0.00000</h1> -->
                    </div>
                </div>
                <div class="my-12">
                    -
                </div>
                <div>
                    <form:label  path="maxAmount" class="text-lg font-sans text-polard font-semibold mb-3 text-center flex flex-row justify-center"><messages:message code="maxIn"/><p id="maxCoin" class="mx-2">BTC</p></form:label>
                    <div class="flex flex-row justify-center mx-auto">
                        <form:input type="number" path="maxAmount" class="h-10 justify-center rounded-lg p-3 mx-5" step=".00000001" />
                        <!-- <h1 class="my-auto" id="coinAmount">~0.00000</h1> -->
                    </div>
                </div>
            </div>
        </div>

        <div class="flex flex-col mx-auto mt-6">
            <form:errors path="location" cssClass="text-red-500 mx-auto"/>
            <form:label path="location" class="text-xl font-sans text-polard font-semibold mb-3 text-center "><messages:message code="offerLocation"/> *</form:label>
            <div class="flex flex-row justify-center w-96 mx-auto">
                <form:input type="text" path="location" cssClass="w-full h-10 justify-center rounded-lg mx-auto p-3"/>
            </div>
        </div>
        <%--
        <div class="flex flex-col mx-auto">
            <form:errors path="paymentMethods" cssClass="text-red-500 mx-auto"/>
            <form:label path="paymentMethods" class="text-xl font-sans text-polard font-semibold mb-3 text-center "><messages:message code="paymentMethods"/>*</form:label>
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
        --%>
        <div class="flex flex-col mx-auto mt-6">
            <form:errors path="message" cssClass="text-red-500 mx-auto"/>
            <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center "><messages:message code="transferInformation"/> *</form:label>
            <div class="flex flex-row justify-center w-96 mx-auto">
                <form:textarea path="message" cssClass="w-full h-36 rounded-lg mx-auto p-5"/>
            </div>
        </div>

        <div class="flex flex-row p-5 mx-60 mt-10">
            <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="javascript:history.back()"><messages:message code="cancel"/></a>
            <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress"><messages:message code="send"/></button>
        </div>
    </form:form>
</div>
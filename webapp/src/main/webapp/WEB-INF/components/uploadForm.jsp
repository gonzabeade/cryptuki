<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="cryptocurrencies" scope="request" type="java.lang.Iterable"/>
<%--<jsp:useBean id="paymentMethods" scope="request" type="java.lang.Iterable"/>--%>
<div class="flex flex-row">
    <form:form modelAttribute="uploadOfferForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">
        <div class="flex flex-row divide-x">
            <div class="flex flex-col mx-5">
                <h1 class="font-sans font-polard font-extrabold text-2xl text-center">1. <messages:message code="priceSettings"/> </h1>
                <c:url value="/offer/upload" var="postUrl"/>
                <form:input type="hidden" path="sellerId" value="${sellerId}"/>
                <div class="flex flex-col justify-center">
                    <form:errors path="cryptoCode" cssClass=" mx-auto text-red-500"/>
                    <form:label  path="cryptoCode" class="text-lg font-sans text-polard  mb-3 text-center"><messages:message code="cryptocurrency"/>*</form:label>
                    <div class="flex flex-row justify-center mx-auto">
                        <form:select path="cryptoCode" class="rounded-lg p-3" onchange="updateVars(this.value)">
                            <option disabled selected><messages:message code="chooseAnOption"/></option>
                            <c:forEach var="coin" items="${cryptocurrencies}">
                                <form:option value="${coin.code}">
                                    <c:out value="${coin.commercialName}"/>
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="flex flex-col mt-4">
                    <form:errors path="unitPrice" cssClass="text-red-500 mx-auto"/>
                    <form:label path="unitPrice" class="text-lg font-sans text-polard  mb-3 text-center "><messages:message code="priceByCurrency"/> ARS*</form:label>
                    <div class="flex flex-col justify-center ">
                        <form:input type="number" path="unitPrice" class="h-10 justify-center rounded-lg p-3 mx-auto " step=".01"/>
                        <!-- <h1 class="my-auto mx-auto" id="price">~0.00000 ARS</h1> -->
                    </div>
                </div>
                <div class="flex flex-col justify-center mt-4">
                    <h2 class="text-lg font-sans text-polard mb-3 text-center flex flex-row justify-center "><messages:message code="limits"/>*</h2>
                    <form:errors path="minInCrypto" cssClass=" mx-auto text-red-500"/>
                    <form:errors path="maxInCrypto" cssClass=" mx-auto text-red-500"/>
                    <form:errors cssClass="text-red-500 mx-auto"/>
                    <div class="flex flex-row justify-center">
                        <div>
                            <form:label  path="minInCrypto" class="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center "><messages:message code="minIn"/> <p id="minCoin" class="mx-2">BTC</p></form:label>
                            <div class="flex flex-row justify-center mx-auto">
                                <form:input type="number" path="minInCrypto" class="h-10 justify-center rounded-lg p-3 mx-5 w-20" step=".00000001"/>
                                <!-- <h1 class="my-auto" id="coinAmount">~0.00000</h1> -->
                            </div>
                        </div>
                        <div class="my-12">
                            -
                        </div>
                        <div>
                            <form:label  path="maxInCrypto" class="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center"><messages:message code="maxIn"/><p id="maxCoin" class="mx-2">BTC</p></form:label>
                            <div class="flex flex-row justify-center mx-auto">
                                <form:input type="number" path="maxInCrypto" class="h-10 justify-center rounded-lg p-3 mx-5 w-20" step=".00000001" />
                                <!-- <h1 class="my-auto" id="coinAmount">~0.00000</h1> -->
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="flex flex-col mx-auto w-96 ">
                <form:errors path="location" cssClass="text-red-500 mx-auto"/>
                <form:label path="location"
                            class="text-2xl font-sans text-polard font-extrabold mb-3 text-center ">2.<messages:message
                        code="offerLocation"/></form:label>
                <div class="flex flex-col justify-center px-5">
                    <h1 class="text-justify"><messages:message code="hoodDetail"/></h1>
                    <h2 class="text-lg font-sans text-polard text-center flex flex-row justify-center mt-3"><messages:message code="hood"/>*</h2>
                    <form:select path="location" cssClass="font-sans text-polard mb-3 text-center rounded-lg p-2 ">
                        <option disabled selected><messages:message code="chooseAnOption"/></option>
                        <c:forEach items="${location}" var="hood">
                            <form:option value="${hood}"><messages:message code="Location.${hood}"/></form:option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="flex flex-col px-10">
                <form:label path="firstChat"
                            class="text-2xl font-sans text-polard font-extrabold mb-3 text-center ">3.<messages:message
                        code="firstChat"/></form:label>
                <h2 class="text-justify"><messages:message code="automaticResponseDetail"/> </h2>
                <form:errors path="firstChat" cssClass="text-red-500 mx-auto mt-2"/>
                <div class="flex flex-row justify-center w-80 mx-auto mt-2">
                    <form:textarea path="firstChat" cssClass="w-full h-36 rounded-lg mx-auto p-5"/>
                </div>
            </div>
        </div>
        <div class="flex flex-row p-5 mx-auto">
            <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans mx-5 w-32"
               href="javascript:history.back()"><messages:message code="cancel"/></a>
            <button type="submit"
                    class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans  w-32 mx-5 active:cursor-progress">
                <messages:message code="send"/></button>
        </div>
    </form:form>
</div>
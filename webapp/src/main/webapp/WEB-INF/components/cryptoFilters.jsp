<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="cryptocurrencies" scope="request" type="java.util.Collection"/>
<div class="p-8 max-w-md  bg-[#FAFCFF] rounded-lg border shadow-md mx-5 mt-10">
    <div class="flex justify-between items-center mb-4">
        <h5 class="text-xl  text-polard font-bold leading-none"><messages:message code="cryptocurrencies"/></h5>
    </div>
    <div class="flow-root">
        <ul role="list" class="divide-y divide-gray-200 ">

            <c:forEach var="coin" items="${cryptocurrencies}">
                <jsp:include page="cryptoSlider.jsp">
                    <jsp:param name="commercialName" value="${coin.commercialName}"/>
                    <jsp:param name="code" value="${coin.code}"/>
                </jsp:include>
            </c:forEach>

        </ul>
        <div class="flex">
            <button type="submit" class="mx-auto p-3" id="link">Aplicar Filtros</button>
        </div>
          </div>
</div>

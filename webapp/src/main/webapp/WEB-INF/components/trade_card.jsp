<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">

    <div class="basis-1/5 ">
        <h1 class="font-sans">Vendedor: </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.sellerUsername}"/></h3>
    </div>
    <div class="basis-1/5 ">
        <h1 class="font-sans">Comprador: </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.buyerUsername}"/></h3>
    </div>

    <div class="basis-1/5 font-sans mr-20 justify-center items-center">
        <c:if test="${username ==  param.buyerUsername }">
            <h1 class="font-sans">Compraste por: </h1>
        </c:if>
        <c:if test="${username == param.sellerUsername}">
            <h1 class="font-sans">Vendiste por: </h1>
        </c:if>
        <h3 class="font-sans font-semibold"><c:out value="${param.quantity}"/>$AR</h3>
    </div>

    <div class="flex flex-col basis-1/5 font-sans">
        <div class="flex flex-row">
            <img src="<c:url value="/public/images/${param.cryptoCurrencyCode}.png"/>" alt="<c:out value="${param.cryptoCurrencyCode}"/>" class="w-5 h-5"/>
            <h1 class="font-sans font-semibold"><c:out value="${param.cryptoCurrencyCode}"/></h1>
        </div>
        <h1 class="text-xl font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="6" value="${param.quantity/param.askedPrice}"/></h1>
    </div>

    <div class="flex basis-1/5">
        <a class="mx-36 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center" href="<c:url value="/receiptDescription/${param.tradeId}"/>">
        Ayuda
        </a>
    </div>
</div>

</div>

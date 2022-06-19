<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${offer.offerStatus == 'DEL'}">
    <c:url var="url" value="#"/>
</c:if>
<c:if test="${offer.offerStatus != 'DEL'}">
    <c:url  var="url" value="/seller/associatedTrades/${offer.offerId}"/>
</c:if>

<a  href="${url}" class="z-10 flex flex-col p-3 bg-[#FAFCFF] rounded-lg w-60 my-5 mx-5 ${offer.offerStatus!= 'DEL' ? 'hover:-translate-y-1 hover:scale-110 duration-200': 'cursor-not-allowed' }">
    <h1 class="text-center text-3xl font-semibold font-polard font-sans"><messages:message code="offer"/>#<c:out value="${offer.offerId}"/></h1>

    <div class="flex flex-col mx-auto mt-5">
        <h1 class="font-bold text-lg text-center"><messages:message code="acceptedRange"/></h1>
        <p class="text-center"><fmt:formatNumber type="number" maxFractionDigits="6" value="${offer.minInCrypto}"/> <c:out value="${offer.crypto.code}"/> - <fmt:formatNumber type="number" maxFractionDigits="6" value="${offer.maxInCrypto}"/> <c:out value="${offer.crypto.code}"/></p>
    </div>
    <div class="flex flex-col mx-auto">
        <h1 class="font-bold text-lg"><messages:message code="unitPrice"/></h1>
        <p class="text-center"><c:out value="${offer.unitPrice}"/>ARS</p>
    </div>
    <div class="flex flex-col mx-auto mb-5">
        <h1 class="font-bold text-lg"><messages:message code="location"/> </h1>
        <p class="text-center"><messages:message code="Location.${offer.location}"/></p>
    </div>

    <c:if test="${offer.offerStatus!= 'DEL' && offer.offerStatus!= 'PSE'}">
        <div class="flex flex-row justify-between">
            <c:url value="/offer/modify/${offer.offerId}" var="getUrl"/>
            <form method="post" action="${getUrl}" class="rounded-lg text-center bg-frostdr hover:bg-frostdr/[0.7] w-1/2 p-2 mr-2">
                <button type="submit" class=" text-white"><messages:message code="edit"/></button>
            </form>
            <c:url value="/offer/delete/${offer.offerId}" var="postUrl"/>
            <form method="post" action="${postUrl}" class="rounded-lg text-center bg-nred hover:bg-nred/[0.7] text-white w-1/2 p-2 ml-2 z-30 text-white">
                <button type="submit" class=" "><messages:message code="delete"/></button>
            </form>
        </div>
        <form class="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer" action="/offer/pause/${offer.offerId}" method="post">
            <button type="submit" >
                <messages:message code="pauseOffer"/>
            </button>
        </form>
    </c:if>

    <c:if test="${offer.offerStatus== 'PSE'}">
        <form class="rounded-lg bg-storm  hover:bg-stormd py-3 px-5 text-l font-sans text-center cursor-pointer" action="/offer/resume/${offer.offerId}" method="post">
            <button type="submit" >
                <messages:message code="resumeOffer"/>
            </button>
        </form>
    </c:if>
    <c:if test="${offer.offerStatus == 'DEL'}">
        <div class="bg-nred my-auto p-2 text-white text-center"><messages:message code="offerDeleted"/></div>
    </c:if>


</a>

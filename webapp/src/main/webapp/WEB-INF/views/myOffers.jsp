<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>

    <script src="<c:url value="/public/js/pagination.js"/> "></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
    <style>
        div.scroll {
            overflow-x: auto;
            overflow-y: hidden;
            white-space: nowrap;
        }
    </style>

</head>

<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/seller/sellerHeader.jsp"/>
<div class="flex flex-col">
    <div class="flex justify-between mt-10 mb-5">
        <div class="flex mt-10 mb-5 ml-20">
            <c:if test="${!noOffers}">
                <h2 class="text-center text-3xl font-semibold font-sans text-polar my-auto"><messages:message code="yourOffers"/></h2>
            </c:if>
        </div>
    </div>
    <div  class="flex flex-col justify-center">
        <c:forEach var="offer" items="${offerList}">
            <li class="list-none mx-20 my-10">
                <% request.setCharacterEncoding("utf-8"); %>
                <c:set  var="accepted_payments" value="${offer.paymentMethods}" scope="request"/>
                <c:set  var="owner" value="${offer.seller.username.isPresent() ? offer.seller.username.get() : offer.seller.email}" scope="request"/>

                <jsp:include page="../components/card.jsp">
                    <jsp:param name="currency" value="${offer.crypto.code}"/>
                    <jsp:param name="owner" value="${owner}"/>
                    <jsp:param name="asking_price" value="${offer.unitPrice}"/>
                    <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                    <jsp:param name="offerId" value="${offer.offerId}"/>
                    <jsp:param name="minCoinAmount" value="${offer.minQuantity}"/>
                    <jsp:param name="maxCoinAmount" value="${offer.maxQuantity}"/>
                    <jsp:param name="userEmail" value="${userEmail}"/>
                    <jsp:param name="lastLogin" value="${offer.seller.lastLogin.toLocalDate()}"/>
                    <jsp:param name="lastLoginTime" value="${offer.seller.lastLogin.toLocalTime().hour}:${offer.seller.lastLogin.toLocalTime().minute}"/>
                    <jsp:param name="minutesSinceLastLogin" value="${offer.seller.minutesSinceLastLogin}"/>
                    <jsp:param name="rating" value="${offer.seller.rating}"/>
                    <jsp:param name="location" value="${offer.location}"/>

                </jsp:include>
                <div id="${offer.offerId}" style="display: none" class="flex scroll bg-stormd/[0.9]">
                    <div class="flex w-1/2 mt-2 bg-stormd/[0.9]">
                        <c:if test="${(offer.associatedTrades.size() == 0)}">
                            <h2 class="text-center text-4xl font-semibold font-sans text-polar mt-4"><messages:message code="noAssociatedPendignProposes"/> </h2>
                        </c:if>
                        <c:if test="${!(offer.associatedTrades.size() == 0) }">
<%--                            <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="associatedTransactions"/> </h2>--%>
                            <c:forEach var="trade" items="${offer.associatedTrades}">
                                <% request.setCharacterEncoding("utf-8"); %>
                                <jsp:include page="../components/seller/sellerVerticalTradeCard.jsp">
                                    <jsp:param name="username" value="${username}"/>
                                    <jsp:param name="tradeId" value="${trade.tradeId}"/>
                                    <jsp:param name="tradeStatus" value="${trade.status.toString()}"/>
                                    <jsp:param name="askedPrice" value="${trade.offer.unitPrice}"/>
                                    <jsp:param name="cryptoCurrencyCode" value="${trade.offer.crypto.code}"/>
                                    <jsp:param name="quantity" value="${trade.quantity}"/>
                                    <jsp:param name="offerId" value="${trade.offer.offerid}"/>
                                    <jsp:param name="buyerUsername" value="${trade.buyer.username.get()}"/>
                                    <jsp:param name="buyerMail" value="${trade.user.email}"/>
                                    <jsp:param name="buyerPhone" value="${trade.user.phoneNumber}"/>
                                    <jsp:param name="buyerRating" value="${trade.user.rating}"/>
                                </jsp:include>
                            </c:forEach>
                        </c:if>
                    </div>


                </div>



            </li>
        </c:forEach>
    </div>

    <c:if test="${noOffers}">
        <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noOffersUploaded"/></h2>
        <a href="<c:url value="/seller/upload"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message code="startSelling"/></a>
    </c:if>

    <c:if test="${!noOffers}">
        <div class="flex flex-col">
            <% request.setCharacterEncoding("utf-8"); %>
            <jsp:include page="../components/paginator.jsp">
                <jsp:param name="activePage" value="${activePage}"/>
                <jsp:param name="pages" value="${pages}"/>
                <jsp:param name="baseUrl" value="/seller/"/>
            </jsp:include>
            <h1 class="mx-auto text-gray-400 mx-auto"><messages:message code="totalPageAmount"/>: ${pages}</h1>
        </div>
    </c:if>
    <div class="shape-blob"></div>
    <div class="shape-blob one"></div>
    <div class="shape-blob two"></div>
    <div class="shape-blob" style="left: 50%"></div>
    <div class="shape-blob" style="left: 5%; top: 80%"></div>
</div>
</body>
</html>
<script>
    function show(id){
        document.getElementById(id).setAttribute('style','display');
        document.getElementById('show-'+id).setAttribute('style','display: none');
        document.getElementById('hide-'+id).setAttribute('style','display');

    }

    function hide(id){
        document.getElementById(id).setAttribute("style","display: none");
        document.getElementById("hide-"+id).setAttribute("style","display: none");
        document.getElementById("show-"+id).setAttribute("style","display");
    }
    function updateStatus(newStatusName, tradeId) {
        document.getElementById('newStatus-'+tradeId).setAttribute('value',newStatusName)
    }

</script>



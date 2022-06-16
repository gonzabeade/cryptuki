<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/feedback.js"/>"></script>
    <script src="<c:url value="/public/js/starRating.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<sec:authentication property="name" var="username"/>
<% request.setCharacterEncoding("utf-8"); %>
<c:choose>
    <c:when test="${username == trade.buyer.username.get()}">
        <jsp:include page="../components/buyer/buyerHeader.jsp"/>
    </c:when>
<c:otherwise>
    <jsp:include page="../components/seller/sellerHeader.jsp"/>
</c:otherwise>
</c:choose>
<div class="flex flex-row divide-x-2 divide-polard mt-5">
    <div class="flex flex-col w-3/5 h-screen">
        <div class="flex flex-col mx-auto">
            <svg xmlns="http://www.w3.org/2000/svg" class=" mx-auto h-20 w-20" fill="none" viewBox="0 0 24 24" stroke="#A3BE8C" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <h1 class="p-4 text-polard font-bold text-3xl font-sans mx-5 text-center "><messages:message code="ProposingDone"/></h1>
            <h3 class="mx-auto text-polard text-center text-xl text-gray-500"> <messages:message code="thankForUsingCryptuki"/></h3>
        </div>
        <div class="flex flex-col">
            <h1 class="mx-auto text-polard font-bold text-2xl mt-10 mb-5"><messages:message code="exchangeData"/></h1>
            <div class="py-5 px-14 mx-auto rounded-lg bg-stormd/[0.9] flex  border-2 border-polard flex-col">
                <div class="flex flex-row px-30">
                    <div class="flex flex-col">
                        <h1 class="text-polard font-bold text-xl"><messages:message code="youPaid"/></h1>
                        <c:choose>
                            <c:when test="${buying}">
                                <div class="flex flex-row">
                                    <h2 class="text-xl font-sans text-polar text-left my-auto"><fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity/offer.unitPrice}"/></h2>
                                    <h1 class="text-xl  font-sans text-polar text-left my-auto ml-2"><c:out value="${offer.crypto.code}"/></h1>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="flex flex-row">
                                    <h2 class="text-xl  font-sans text-polar text-left my-auto"><fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity}"/> ARS</h2>
                                </div>
                            </c:otherwise>

                        </c:choose>

                    </div>
                    <div class="mx-10 my-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3" />
                        </svg>
                    </div>
                    <div class="flex flex-col">
                        <h1 class="text-polard font-bold text-xl"><messages:message code="youReceived"/></h1>
                        <c:choose>
                            <c:when test="${!buying}">
                                <div class="flex flex-row">
                                    <h2 class="text-xl font-sans text-polar text-left my-auto"><fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity/offer.unitPrice}"/></h2>
                                    <h1 class="text-xl  font-sans text-polar text-left my-auto ml-2"><c:out value="${offer.crypto.code}"/></h1>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="flex flex-row">
                                    <h2 class="text-xl  font-sans text-polar text-left my-auto"><fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity}"/>ARS</h2>
                                </div>
                            </c:otherwise>

                        </c:choose>
                    </div>
                </div>
                <div class="flex flex-col my-10 px-30">
                    <h4 class="text-lg font-polard font-bold mx-auto"><messages:message code="trasactionDate"/></h4>
                    <h2 class="text-xl font-sans text-polar text-center my-auto ">
                        <fmt:parseDate value="${ trade.startDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${ parsedDateTime }" />
                    </h2>
                </div>
            </div>
        </div>



        <div class="flex flex-row mt-10">
            <c:set var="urlBack" value="${trade.buyer.username.get() == username ? '/buyer':'/seller'}"/>
            <a class="bg-frost text-white p-3 font-sans rounded-lg mx-auto  w-40 text-center" href="<c:url  value="${urlBack}"/>"><messages:message code="goBack"/></a>
            <a class="bg-nred text-white p-3 font-sans rounded-lg mx-auto w-40 text-center" href="<c:url value="/complain?tradeId=${trade.tradeId}"/> "><messages:message code="iHadAProblema"/></a>
        </div>



    </div>
    <div class="flex flex-col w-2/5">
        <div class="flex flex-col ml-32">
            <c:if test="${buying}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../components/sellerInfo.jsp">
                    <jsp:param name="email" value="${offer.seller.email}"/>
                    <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
                    <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                    <jsp:param name="lastLogin" value="${otherLastLogin.relativeTime}"/>
                    <jsp:param name="rating" value="${offer.seller.rating}"/>
                    <jsp:param name="message" value="${offer.comments}"/>
                </jsp:include>
            </c:if>

            <c:if test="${!buying}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../components/buyerInfo.jsp">
                    <jsp:param name="email" value="${trade.buyer.email}"/>
                    <jsp:param name="phone" value="${trade.buyer.phoneNumber}"/>
                    <jsp:param name="trades" value="${trade.buyer.ratingCount}"/>
                    <jsp:param name="lastLogin" value="${otherLastLogin.relativeTime}"/>
                    <jsp:param name="rating" value="${trade.buyer.rating}"/>
                </jsp:include>
            </c:if>

            <div class="flex flex-col mx-auto mt-10">
                <c:if test="${(trade.buyer.username.get() == username && trade.sellerRated == false) || (trade.offer.seller.username.get() == username && trade.buyerRated == false)}">
                    <h1 class="text-polard font-sans  font-bold text-center text-3xl mx-auto"><messages:message code="rate"/> ${trade.offer.seller.username.get() == username ? trade.buyer.username.get(): trade.offer.seller.username.get()}</h1>
<%--            WIP RATING STARS --%>

                    <c:url value="/rate" var="postUrl"/>
                    <form:form modelAttribute="ratingForm" action="${postUrl}" method="post" >

                        <form:hidden path="tradeId" value="${trade.tradeId}"/>
                        <div class="flex flex-col">
                            <form:errors path="rating" cssClass="mx-auto text-red-400"/>
                            <form:label path="rating" cssClass="mx-auto"><messages:message code="ratingConditions"/></form:label>
                            <div class="flex flex-row mx-auto mt-3">
                                <span class=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star1" onclick="setRatingAndSend(1)" onmouseleave="leaveHoverOnRating(1)" onmouseover="hoverOnRating(1)"></span>
                                <span class=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star2" onclick="setRatingAndSend(2)" onmouseleave="leaveHoverOnRating(2)" onmouseover="hoverOnRating(2)"></span>
                                <span class=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star3" onclick="setRatingAndSend(3)" onmouseleave="leaveHoverOnRating(3)" onmouseover="hoverOnRating(3)"></span>
                                <span class=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star4" onclick="setRatingAndSend(4)" onmouseleave="leaveHoverOnRating(4)" onmouseover="hoverOnRating(4)"></span>
                                <span class=" cursor-pointer fa fa-star-o text-orange-400 text-3xl" id="star5" onclick="setRatingAndSend(5)" onmouseleave="leaveHoverOnRating(5)" onmouseover="hoverOnRating(5)"></span>

                            </div>
                            <div class="flex flex-row">
                                <form:hidden path="rating" value="0"/>
                                <button type="submit" id="sendRating" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress hidden"><messages:message code="send"/> </button>
                            </div>
                           </div>

                    </form:form>
                </c:if>
                <c:if test="${rated == true }">
                    <div class="mb-5 mt-5">
                        <c:set var="ratingSent"><messages:message code="ratingSent"/></c:set>
                        <jsp:include page="../components/confirmationToggle.jsp">
                            <jsp:param name="title" value="${ratingSent}"/>
                        </jsp:include>
                    </div>
                </c:if>
            </div>

    </div>
</div>
</body>
</html>




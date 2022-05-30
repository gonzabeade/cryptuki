<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link  href="/public/css/stepper.css" rel="stylesheet">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>

<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/buyer/buyerHeader.jsp"/>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5">
            <c:if test="${status.equals('PENDING')}">
                <div class="flex bg-nyellow p-5 text-center rounded-lg mx-auto border-2 border-[#816327]">
                    <div class="my-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="#816327" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                    </div>
                    <div class="flex flex-col mx-4">
                        <p class="text-[#816327] text-left text-xl"><b><messages:message code="ProposingPending"/></b></p>
                        <p><messages:message code="waitForTheSeller"/></p>
                    </div>
                </div>
            </c:if>
            <c:if test="${status.equals('REJECTED')}">
                <div class="flex bg-nred/[0.6] p-5 text-center rounded-lg mx-auto border-2 border-nred">
                    <div class="my-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="#BF616A" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                    </div>
                    <div class="flex flex-col mx-4">
                        <p class="text-nred text-left text-xl"><b><messages:message code="ProposingRejected"/></b>
                        </p>
                        <p><messages:message code="furtherInstructionsRejected"/></p>
                    </div>
                </div>
            </c:if>
            <c:if test="${status.equals('ACCEPTED')}">
                <div class="flex bg-ngreen p-5 text-center rounded-lg mx-auto border-2 border-[#364427]">
                    <div class="my-auto">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="#364427" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                    </div>
                    <div class="flex flex-col mx-4">
                        <p class="text-[#364427] text-left text-xl"><b><messages:message code="ProposingAccepted"/></b></p>
                        <p><messages:message code="furtherInstructions"/></p>
                    </div>
                </div>
            </c:if>
        <div class="flex flex-col mx-auto my-4">
            <c:choose>
                <c:when test="${status.equals('PENDING')}">
                    <c:set var="step" value="0"/>
                </c:when>
                <c:when test="${status.equals('ACCEPTED')}">
                    <c:set var="step" value="1"/>
                </c:when>
                <c:when test="${status.equals('SOLD')}">
                    <c:set var="step" value="2"/>
                </c:when>
                <c:otherwise>
                    <c:set var="step" value="3"/>
                </c:otherwise>
            </c:choose>

            <c:if test="${step < 3}">
                <h1 class="text-polard text-3xl font-bold text-center my-4">Proceso de compra</h1>
                <jsp:include page="../components/stepper.jsp">
                    <jsp:param name="active" value="${step}"/>
                </jsp:include>
            </c:if>


        </div>
      <div class="flex flex-col">
            <h1 class="text-2xl font-sans font-semibold mx-auto text-center my-10"><messages:message code="aboutTheOffer"/> <c:if test="${!buying}"><messages:message code="received"/> </c:if></h1>
            <div class="flex flex-row justify-center">

                <div class="flex flex-col mx-10 order-1" id="left">
                        <h1 class="text-center text-xl"><messages:message code="youPay"/></h1>
                        <h1 class="text-center text-2xl font-semibold font-polard">${amount} ARS</h1>
                </div>
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 my-5 order-2 mx-10" fill="none" viewBox="0 0 24 24" stroke="black" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M14 5l7 7m0 0l-7 7m7-7H3" />
                </svg>
                <div class="flex flex-col mx-10 order-3" id="right">
                    <h1 class="text-center text-xl"><messages:message code="youReceive"/></h1>
                    <h1 class="text-center text-2xl font-semibold font-polard"><fmt:formatNumber type="number" maxFractionDigits="10" value="${amount / offer.askingPrice }"/> ${offer.crypto.code}</h1>
                </div>
            </div>
            <c:if test="${status.equals('ACCEPTED')}">
                <div class="flex flex-col mx-auto">
                    <div class="flex flex-row mx-auto mt-10">
                        <h1 class="text-polard text-2xl font-bold text-center mr-5 "><messages:message code="adviceOnP2P"/> </h1>
                    </div>
                    <ul class="mx-20 list-decimal my-3">
                        <li class="mt-5 text-polard text-lg">
                           <messages:message code="advice1"/>
                            </li>
                        <li class="mt-5 text-polard text-lg">
                            <p>
                                <messages:message code="advice2"/>
                             </p>
                        </li>
                        <li class="mt-5 text-polard text-lg">
                            <p>
                                <messages:message code="advice3"></messages:message>
                            </p>
                        </li>
                        <li class="mt-5 text-polard text-lg">
                            <p>
                                <messages:message code="adivce4"></messages:message>
                            </p>
                        </li>
                    </ul>

                </div>

            </c:if>

            <div class="flex justify-around mt-5">
                <a class="h-fit bg-frost text-white p-3 font-sans rounded-lg w-40 text-center" href="<c:url value="/buyer/market"/>"><messages:message code="returnHome"/></a>

                <form  method="post"
                       action="<c:url value="/deleteTrade/${tradeId}"/>" class="flex">
                    <button type="submit" class="bg-nred text-white p-3 font-sans rounded-lg w-40">
                        <messages:message code="removeTrade"/>
                    </button>
                </form>
            </div>

        </div>




        </div>

    <div class="flex w-2/5 ">
        <div class="ml-32">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../components/sellerInfo.jsp">
                    <jsp:param name="email" value="${offer.seller.email}"/>
                    <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
                    <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
                    <jsp:param name="lastLogin" value="${otherLastLogin.relativeTime}"/>
                    <jsp:param name="rating" value="${offer.seller.rating}"/>
                    <jsp:param name="message" value="${offer.comments}"/>
                </jsp:include>
        </div>
    </div>
</div>
</body>
</html>


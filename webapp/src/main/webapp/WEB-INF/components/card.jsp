<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="accepted_payments" scope="request" type="java.util.Collection"/>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
<sec:authentication property="name" var="username"/>

<div class="shadow-xl flex rounded-lg space-x-40 m-5 p-7 bg-[#FAFCFF]">
				<div class="flex-row basis-1/4 justify-center">
					<h1 class="font-sans"><messages:message code="seller"/>: <span class="font-bold font-sans"><c:out value="${param.owner}"/></span></h1>
					<%--<p class="font-bold font-sans"><c:out value="${param.owner}"/></p>--%>
					<h4 class="text-gray-400 font-sans"> <c:out value="${param.trades}"/> <messages:message code="trades"/> | <messages:message code="rating"/>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${param.rating}"/> </h4>
					<div class="flex flex-row">
						<c:choose>
							<c:when test="${param.minutesSinceLastLogin <= 5}">
								<div class="bg-ngreen rounded-full w-2 h-2 my-auto"></div>
								<p class="ml-1"><messages:message code="online"/></p>
							</c:when>
							<c:when test="${5 < param.minutesSinceLastLogin && param.minutesSinceLastLogin <= 60}">
								<p class="ml-1"><messages:message code="lastLogin"/>: <c:out value="${fn:substring(param.lastLoginTime, 0, 5)}"/></p>
							</c:when>
							<c:otherwise>
								<p><messages:message code="lastLogin"/>: <c:out value="${param.lastLogin}"/></p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="flex flex-col basis-1/4 font-sans justify-center">
					<div class="flex flex-row">
						<h1 class="font-sans"><messages:message code="price"/>: </h1>
						<h1 class="text-xl font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="2" value="${param.asking_price}"/> ARS </h1>
						<p class="my-auto mx-2"> <messages:message code="for"/> </p>
						<img src="<c:url value="/public/images/${param.currency}.png"/>" alt="<c:out value="${param.currency}"/>" class="w-5 h-5 mx-2 my-auto"/>
						<h1 class="font-sans font-semibold my-auto"><c:out value="${param.currency}"/></h1>
					</div>
					<div class="flex flex-row">
						<h4 class="text-gray-400 font-sans mr-2"> Min: <fmt:formatNumber type="number" maxFractionDigits="2" value="${param.asking_price * param.minCoinAmount}"/> ARS</h4>
						<p class="text-gray-400 font-sans">-</p>
						<h4 class="text-gray-400 font-sans mx-2"> Máx: <fmt:formatNumber type="number" maxFractionDigits="2" value="${param.asking_price * param.maxCoinAmount}"/> ARS </h4>
					</div>
					<div class="flex flex-row">
						<c:choose>
							<c:when test="${param.location != ''}">
								<h1 class="flex flex-row"><messages:message code="location"/>: <span class="font-sans font-bold"><c:out value="${param.location}"/></span></h1>
							</c:when>
						</c:choose>
					</div>

					<!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
				</div>
				<%--
				<div class="flex flex-row basis-1/4 justify-between">
					<div class="flex-col">
						<h1 class="font-sans"><messages:message code="paymentMethods"/>: </h1>
						<div class="flex flex-row justify-start">
							<c:if test="${accepted_payments.size() == 0}"><messages:message code="noPaymentMethods"/></c:if>
							<c:forEach  var="payment_method" items="${accepted_payments}">
							<img src="<c:url value = "/public/images/${payment_method.name}.png"/>" class="max-w-[32px] max-h-[32px] mr-2 " title="${payment_method.description}">
							</c:forEach>
						</div>
					</div>
				</div>
				--%>
				<div class="flex basis-1/4 justify-center">
					<c:choose>
						<c:when test="${param.userEmail == param.owner || username==param.owner || isAdmin}">
							<a class=" pb-6 px-5 pt-4 rounded-lg bg-gray-300 max-h-14 m-2 hover:bg-stormdl/[.6] hover:border-2 hover:border-polard w-36 text-center" href="<c:url value="/offer/${param.offerId}"/>">
								<messages:message code="seeOffer"/>
							</a>
						</c:when>
						<c:otherwise>
							<a class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 m-2 hover:bg-frostdr/[.6] hover:border-2 hover:border-frostdr text-white w-36 text-center" href="<c:url value="/buy/${param.offerId}"/>">
								<messages:message code="buy"/>
							</a>
						</c:otherwise>
					</c:choose>
				</div>

		</div>
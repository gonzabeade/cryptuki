<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<jsp:useBean id="accepted_payments" scope="request" type="java.util.Collection"/>--%>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
<sec:authentication property="name" var="username"/>

<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">
				<div class="flex-row basis-1/4 ">
					<h1 class="font-sans"><messages:message code="seller"/>: </h1>
					<h3 class="font-bold font-sans"><c:out value="${param.owner}"/></h3>
					<c:if test="${param.trades > 0 }">
						<h4 class="text-gray-400 font-sans"> <c:out value="${param.trades}"/> <messages:message code="trades"/> | <messages:message code="rating"/>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${param.rating}"/> </h4>
					</c:if>
					<c:if test="${param.trades == 0 }">
						<h4 class="text-gray-400 font-sans"><messages:message code="newUser"/></h4>
					</c:if>

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

				<div class="flex flex-col basis-1/4 font-sans">
					<h1 class="font-sans"><messages:message code="price"/>: </h1>
					<div class="flex flex-row">
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
					<!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
				</div>

				<div class="flex flex-row basis-1/4 justify-center">
					<div class="flex-col">
						<h1 class="font-sans"><messages:message code="location"/>: </h1>
						<div class="pt-2 flex flex-row justify-start">
							<c:choose>
								<c:when test="${param.location == ''}">
									<h1 class="font-sans font-bold pr-3 mt-1"><messages:message code="unknown"/></h1>
								</c:when>
								<c:otherwise>
									<h1 class="font-sans font-bold pr-3 mt-1"><c:out value="${param.location}"/></h1>
								</c:otherwise>
							</c:choose>
							<img src="<c:url value = "/public/images/blue_location.png"/>" class="max-w-[28px] max-h-[28px] mr-2" title="location">
							<%--
							<c:forEach  var="payment_method" items="${accepted_payments}">
								<img src="<c:url value = "/public/images/${payment_method.name}.png"/>" class="max-w-[32px] max-h-[32px] mr-2 " title="${payment_method.description}">
							</c:forEach>
							--%>
						</div>
					</div>
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
						<c:if test="${param.userEmail == param.owner || username==param.owner || isAdmin}">
<%--								<a class=" pb-6 px-5 pt-4 rounded-lg bg-gray-300 max-h-14 m-2 hover:bg-stormdl/[.6] hover:border-2 hover:border-polard w-36 text-center" href="<c:url value="/offer/${param.offerId}"/>"><messages:message code="seeOffer"/></a>--%>
							<div class="flex flex-row mx-auto my-4">
								<a class="active:cursor-progress my-auto mx-3" href="<c:url value="/modify/${param.offerId}"/>">
									<svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="#2E3440" stroke-width="2">
										<path stroke-linecap="round" stroke-linejoin="round" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
									</svg>
								</a>
								<div class="my-auto">
									<c:url value="/delete/${param.offerId}" var="deleteUrl"/>
									<form:form method="post" action="${deleteUrl}" cssClass="flex my-auto mx-3">
										<button type="submit">
											<svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="#2E3440" stroke-width="2">
												<path stroke-linecap="round" stroke-linejoin="round" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
											</svg>
										</button>
									</form:form>
								</div>
							</div>
						</c:if>
						<c:if test="${param.userEmail == param.owner || username==param.owner}">
							<button class=" text-center pb-6 px-2 pt-4 rounded-lg bg-stormd max-h-16 text-polard mx-auto my-auto" id="show-${param.offerId}" onclick="show(${param.offerId})">
								<p class="p-1 mb-1"><messages:message code="show"/></p>
							</button>
							<button class="text-center pb-6 px-7 pt-4 rounded-lg bg-stormd max-h-16 text-polard mx-auto my-auto" id="hide-${param.offerId}" onclick="hide(${param.offerId})" style="display: none">
								<messages:message code="hide"/>
							</button>
						</c:if>
						<c:if test="${param.userEmail != param.owner && username!=param.owner && !isAdmin}">
							<a class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 m-2 hover:bg-frostdr/[.6] hover:border-2 hover:border-frostdr text-white w-36 text-center" href="<c:url value="/buy/${param.offerId}"/>">
								<messages:message code="buy"/>
							</a>
						</c:if>
				</div>
		</div>
		<div id="offerId" style="display: none">
			<h1>${param.offerId}</h1>
		</div>






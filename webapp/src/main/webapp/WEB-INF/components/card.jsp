<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="accepted_payments" scope="request" type="java.util.Collection"/>
<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">
				<div class="flex-row basis-1/4 ">
					<h1 class="fotn-sans">Vendedor: </h1>
					<h3 class="font-bold font-sans"><c:out value="${param.user}"/></h3>
					<h4 class="text-gray-400 font-sans"><c:out value="${param.trades}"/> trades</h4>
				</div>

				<div class="flex flex-col basis-1/4 font-sans">
					<div class="flex flex-row">
						<img src="<c:url value="/public/images/${param.currency}.png"/>" alt="<c:out value="${param.currency}"/>" class="w-5 h-5 mx-2"/>
						<h1 class="font-sans font-semibold"><c:out value="${param.currency}"/></h1>
					</div>
					<h1 class="text-xl font-bold font-sans mx-2"><fmt:formatNumber type="number" maxFractionDigits="2" value="${param.asking_price}"/> ARS </h1>
					<h4 class="text-gray-400 font-sans mx-2"> Máx: <fmt:formatNumber type="number" maxFractionDigits="2" value="${param.asking_price * param.coinAmount}"/> ARS </h4>
					<!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
				</div>
				<div class="flex flex-row basis-1/4 justify-between">
					<div class="flex-col">
						<h1 class="font-sans">Medios de Pago: </h1>
						<div class="flex flex-row justify-start">
							<c:if test="${accepted_payments.size() == 0}">El vendedor no propuso métodos de pago.</c:if>
							<c:forEach  var="payment_method" items="${accepted_payments}">
							<img src="<c:url value = "/public/images/${payment_method.name}.png"/>" class="max-w-[32px] max-h-[32px] mr-2 " title="${payment_method.description}">
							</c:forEach>
						</div>


					</div>

				</div>
				<div class="flex basis-1/4 justify-center">
					<a class=" pb-6 px-7 pt-4 rounded-lg bg-frostdr max-h-14 m-2 hover:bg-frostdr/[.6] hover:border-2 hover:border-frostdr text-white" href="<c:url value="/buy/${param.offerId}"/>">
						Ver oferta
					</a>
				</div>

		</div>
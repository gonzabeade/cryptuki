<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="accepted_payments" scope="request" type="java.util.Collection"/>
<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">
				<div class="flex-row basis-1/4 ">
					<h1 class="fotn-sans">Vendedor: </h1>
					<h3 class="font-bold font-sans">${param.user}</h3>
					<h4 class="text-gray-400 font-sans">${param.trades} trades</h4>
				</div>

				<div class="flex flex-col basis-1/4 font-sans">
					<h1 class="font-sans">Precio: </h1>
					<h1 class="text-xl font-bold font-sans">${param.asking_price} ARS / ${param.currency}</h1>
  					<!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
				</div>
				<div class="flex flex-row basis-1/4 justify-between">
					<div class="flex-col">
						<h1 class="font-sans">Medios de Pago: </h1>
						<div class="flex flex-row justify-start">
							<c:forEach  var="payment_method" items="${accepted_payments}">
							<img src="<c:url value = "/public/images/${payment_method.name}.png"/>" class="max-w-[32px] max-h-[32px] mr-2 " alt="${payment_method}">
							</c:forEach>
						</div>


					</div>

				</div>
				<div class="flex basis-1/4 justify-center">
					<a class=" pb-6 px-9 pt-4 rounded-lg bg-frostdr max-h-14 m-2 transition ease-in-out delay-150 hover:-translate-y-0.25 hover:scale-110 hover:bg-polarl duration-150 text-white" href="<c:url value="/buy/${param.offerId}"/>">
						Compra
					</a>
				</div>

		</div>
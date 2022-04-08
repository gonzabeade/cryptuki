<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>


<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">
				<div class="flex-row basis-1/4 ">
					<h1 class="fotn-sans">Vendedor: </h1>
					<h3 class="font-bold font-sans">${param.user}</h3>
					<h4 class="text-gray-400 font-sans">${param.trades} trades</h4>
				</div>

				<div class="flex flex-col basis-1/4 font-sans">
					<h1 class="font-sans">Precio: </h1>
					<h1 class="text-xl font-bold font-sans">${param.asking_price} ARS/ ${param.currency}</h1>
  					<!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
				</div>
				<div class="flex flex-row basis-1/4 justify-between">
					<div class="flex-col">
						<h1 class="font-sans">Medios de Pago: </h1>
						<div class="flex flex-row justify-between">

							<%--@elvariable id="accepted_payments" type="java.util.Iterable"--%>
							<c:forEach  var="payment_method" items="${accepted_payments}">
							<img src="<c:url value = "/public/images/${payment_method}.png"/>" class="max-w-[32px] max-h-[32px]" alt="${payment_method}">
							</c:forEach>
						</div>


					</div>

				</div>
				<div class="flex basis-1/4 justify-center">
						<button class="rounded-lg bg-frostdr max-h-14 m-2 transition ease-in-out delay-150 hover:-translate-y-0.25 hover:scale-110 hover:bg-polarl duration-150">
						<p class="pr-14 pl-14 text-storm ">Compra</p>
  					</button>
  				</div>

		</div>
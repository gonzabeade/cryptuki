<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>

<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">
				<div class="flex-row basis-1/4 ">
					<h1>Vendedor: </h1>
					<h3 class="font-bold">${param.user}</h3>
					<h4 class="text-gray-400">${param.trades} trades</h4>
				</div>

				<div class="flex flex-col basis-1/4">
					<h1>Precio: </h1>
					<h1 class="text-xl font-bold">${param.asking_price} ARS/ ${param.currency}</h1>
  					<!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
				</div>
				<div class="flex flex-row basis-1/4 justify-between">
					<div class="flex-col ">
						<h1>Medios de Pago: </h1>
						<div class="flex flex-row justify-between">
							<c:forEach var="payment_method" items="${param.accepted_payments}">
							<img src="public/images/${payment_method}.png" class="max-w-[50px]" alt="${payment_method}">
							</c:forEach>
						</div>


					</div>

				</div>
				<div class="flex basis-1/4 justify-center">
						<button class="rounded-lg bg-frostdr max-h-14 m-2">
						<p class="pr-14 pl-14 text-storm ">Compra</p>
  					</button>
  				</div>

		</div>
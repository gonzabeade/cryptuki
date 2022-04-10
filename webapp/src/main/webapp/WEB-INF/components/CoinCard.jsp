<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>


<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">
    <div class="flex flex-col basis-1/4 font-sans">
        <h1 class="font-sans">Nombre: </h1>
        <h1 class="text-xl font-bold font-sans">${param.name}</h1>
        <!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
    </div>
    <div class="flex flex-col basis-1/4 font-sans">
        <h1 class="font-sans">Código: </h1>
        <h1 class="text-xl font-bold font-sans">${param.code}</h1>
        <!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
    </div>
    <div class="flex flex-col basis-1/4 font-sans">
        <h1 class="font-sans">Valor de mercado: </h1>
        <h1 class="text-xl font-bold font-sans">${param.marketPrice}</h1>
        <!-- <h3 class="text-gray-400"> 20% por encima del mercado </h3> -->
    </div>
</div>
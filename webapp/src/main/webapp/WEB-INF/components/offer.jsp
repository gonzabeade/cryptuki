<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-col p-3 bg-[#FAFCFF] rounded-lg w-60 my-5 mx-5">
    <h1 class="text-center text-3xl font-semibold font-polard font-sans">Anuncio #1</h1>
    <div class="flex flex-col mx-auto mt-5">
        <h1 class="font-bold text-lg">Rango Aceptado</h1>
        <p class="text-center">20 ETH - 200 ETH</p>
    </div>
    <div class="flex flex-col mx-auto">
        <h1 class="font-bold text-lg">Precio por moneda</h1>
        <p class="text-center">200ARS</p>
    </div>
    <div class="flex flex-col mx-auto mb-10">
        <h1 class="font-bold text-lg">Ubicación</h1>
        <p class="text-center">Recoleta</p>
    </div>
    <div class="flex flex-row justify-between">
        <c:url value="/offer/modify/10" var="getUrl"/>
        <form method="get" action="${getUrl}" class="rounded-xl text-center bg-frostdr w-1/2 p-2 mx-2">
            <button type="submit" class=" text-white">Editar</button>
        </form>
         <c:url value="/offer/delete/10" var="postUrl"/>
        <form method="post" action="${postUrl}" class="rounded-xl text-center bg-storm w-1/2 p-2 mx-2">
            <button type="submit" class=" ">Eliminar</button>
        </form>
    </div>
</div>

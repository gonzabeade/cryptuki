<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="flex flex-col mx-auto">
    <h1 class="font-sans text-2xl font-bold">Filtros</h1>

    <label>Desde:</label>
    <input name="fromDate" type="date" class="p-1 rounded-lg">

    <label>Hasta:</label>
    <input name="fromDate" type="date" class="p-1 rounded-lg">

    <label>Id de la oferta</label>
    <input type="number" class="p-1 rounded-lg">

    <label> Id de la transacción</label>
    <input type="number" class="p-1 rounded-lg">
    <a href="/" class="rounded-lg bg-frost text-white mt-10 p-3 text-center"> Filtrar</a>

</div>
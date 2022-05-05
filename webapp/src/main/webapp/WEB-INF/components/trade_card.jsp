<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="shadow-xl flex rounded-lg  m-5 p-7 bg-[#FAFCFF]">

    <div class="basis-1/4 ">
        <h1 class="font-sans">Vendedor: </h1>
        <h3 class="font-bold font-sans"><c:out value="${param.user}"/></h3>
    </div>

    <div class="basis-2/4 font-sans mr-20 justify-center items-center">
            <h1 class="font-sans">Compraste por: </h1>
            <h3 class="font-sans font-semibold"><c:out value="${param.quantity}"/>$AR</h3>
    </div>

    <div class="flex basis-1/4">
        <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Ayuda</button>
    </div>
</div>

</div>

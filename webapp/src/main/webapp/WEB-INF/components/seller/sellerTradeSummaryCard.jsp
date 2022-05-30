<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="flex flex-col rounded-lg w-1/5 bg-[#FAFCFF] mx-2 justify-between">

    <div class="flex flex-row items-center mt-5 justify-center align-start underline">
        <h1 class="text-l font-bold">gonzabeade</h1>
    </div>

    <div class="flex flex-row items-center mt-1 justify-center align-start">
        <h1 class="font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="2" value="${1.2}"/><c:out value="  DAI"/></h1>
    </div>
    <div class="flex flex-row items-center justify-center align-start">
        <h1 class="font-bold font-sans">⇅</h1>
    </div>
    <div class="flex flex-row items-center justify-center align-start mb-2">
        <h1 class="font-bold font-sans"><fmt:formatNumber type="number" maxFractionDigits="2" value="${300.00}"/> ARS </h1>
    </div>

</div>


<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<a href="<c:url value="/buyer/market?coin=${param.code}"/>">
    <div class="flex justify-around rounded-lg p-2 justify-center">
        <div class="flex flex-col basis-2/3 font-sans">
            <h1 class="text-xl font-bold font-sans"><c:out value="${param.commercialName}"/> (<c:out value="${param.code}"/>)</h1>
            <h1 id="${param.code}">No data available</h1>
        </div>
        <div class="flex flex-col justify-items-center align-center basis-1/2 font-sans">
            <img class="w-10" src="<c:url value = "/public/images/${param.code}.png"/>">
        </div>
    </div>
</a>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<a href="<c:url value="/buyer/market?coin=${param.code}"/>">
    <div class="flex justify-start rounded-lg p-2">
        <div class="w-1/6 flex flex-col justify-items-center align-center font-sans rounded-full p-2 bg-white mr-4 hover:w-2/6">
            <img class="w-10" src="<c:url value = "/public/images/${param.code}.png"/>">
        </div>
        <div class="flex flex-col basis-2/3 font-sans">
            <h1 class="text-xl font-bold font-sans"><c:out value="${param.commercialName}"/> (<c:out value="${param.code}"/>)</h1>
            <h1 id="<c:out value="${param.code}"/>">No data available</h1>
        </div>
    </div>
</a>


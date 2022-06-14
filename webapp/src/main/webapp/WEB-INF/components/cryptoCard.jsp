<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<a href="<c:url value="/buyer/market?coin=${param.code}"/>">
    <div class="flex flex-row justify-start rounded-lg p-2 bg-white border-gray-200 my-2">
        <div class="w-1/6 flex flex-col justify-items-center align-center font-sans rounded-full p-2 bg-white mr-4 hover:w-2/6">
            <img class="w-10" src="<c:url value = "/public/images/${param.code}.png"/>">
        </div>
        <div class="flex flex-col basis-2/3 font-sans">
            <h1 class="text-xl font-bold font-sans"><c:out value="${param.commercialName}"/> (<c:out value="${param.code}"/>)</h1>
            <h1 id="<c:out value="${param.code}"/>">No data available</h1>
        </div>
        <label for="small-toggle" class="inline-flex relative items-center mb-5 cursor-pointer">
            <input type="checkbox" value="" id="small-toggle" class="sr-only peer">
            <div class="w-9 h-5 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-4 after:w-4 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600"></div>
        </label>
    </div>
</a>


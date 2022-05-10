<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>

<a href="<c:url value="/?coin=${param.code}"/>">
    <div class="transition ease-in-out delay-50 bg-[#FAFCFF] hover:-translate-y-1 hover:scale-110 duration-300 shadow-xl flex justify-around rounded-lg p-7">
        <div class="flex flex-col basis-1/2 font-sans">
            <h1 class="text-xl font-bold font-sans"><c:out value="${param.commercialName}"/> (<c:out value="${param.code}"/>)</h1>
        </div>
        <div class="flex flex-col justify-items-center align-center basis-1/2 font-sans">
            <img class="w-20" src="<c:url value = "/public/images/${param.code}.png"/>">
        </div>
    </div>
</a>
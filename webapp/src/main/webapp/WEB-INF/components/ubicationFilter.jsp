<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mx-5 flex flex-col bg-[#FAFCFF] shadow-xl rounded-lg p-8  mt-5">
    <div class="flex flex-col">
        <h1 class="text-xl  text-polard font-bold leading-none mb-2"><messages:message code="location"/></h1>
        <div  id="location-selected" class=" hidden flex flex-row bg-gray-200 p-2 w-fit">
            <p  id="hood-selected" class="font-regular">Agronomia</p>
            <div onclick="removeLocationParam()" class="cursor-pointer">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="black" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
                </svg>
            </div>

        </div>
    </div>
    <c:forEach items="${locations}" var="location" end="5">
        <a href="<c:url value="/buyer/market?location=${location}"/>" class="text-gray-600"><messages:message code="Location.${location}"/>
    </c:forEach>
    <c:forEach items="${locations}" var="location" begin="5">
        <a href="<c:url value="/buyer/market?location=${location}"/>" class="hidden text-gray-600" id="hood-${location}"><messages:message code="Location.${location}"/></a>
    </c:forEach>
    <span  class="text-blue-400 underline cursor-pointer" onclick="showAllLocations()" id="seeMore">Ver más</span>

</div>
<script>
    function showAllLocations(){
        let hoods = document.querySelectorAll("[id^=hood-]");
        hoods.forEach((hood)=>{
            hood.classList.remove("hidden")
        })
        document.getElementById("seeMore").classList.add("hidden")
    }
</script>

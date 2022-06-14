<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mx-5 flex flex-col bg-[#FAFCFF] shadow-xl rounded-lg p-8  mt-5">
    <h1 class="text-xl  text-polard font-bold leading-none mb-2"><messages:message code="location"/></h1>
    <c:forEach items="${locations}" var="location" end="5">
        <a href="<c:url value="/buyer/market?location=${location}"/> "><messages:message code="Location.${location}"/></a>
    </c:forEach>
    <c:forEach items="${locations}" var="location" begin="5">
        <a href="<c:url value="/buyer/market?location=${location}"/>" class="hidden" id="hood-${location}"><messages:message code="Location.${location}"/></a>
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

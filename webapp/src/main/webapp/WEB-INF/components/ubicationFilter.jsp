<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mx-5 flex flex-col bg-[#FAFCFF] shadow-xl rounded-lg p-5  mt-5">
    <div class="flex flex-row justify-between">
        <h1 class="text-xl  text-polard font-bold leading-none mb-2 justify-center my-auto"><messages:message code="location"/></h1>
        <div class="justify-end" onclick="showAllFilter('allPossibleLocations')">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8  cursor-pointer" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
            </svg>
        </div>

<%--        <div  id="location-selected" class=" hidden flex flex-row bg-gray-200 p-2 w-fit">--%>
<%--            <p  id="hood-selected" class="font-regular">Agronomia</p>--%>
<%--            <div onclick="removeLocationParam()" class="cursor-pointer">--%>
<%--                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="black" stroke-width="2">--%>
<%--                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />--%>
<%--                </svg>--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>
    <div class="flex flex-col" id="allPossibleLocations">
        <div id="locType0" class="w-full flex flex-col justify-start">
            <c:forEach var="locationWrapper" items="${locationsWithOffers}">
                <div class="flex flex-row">
                    <form:checkbox cssClass="mr-1 my-auto" class="hidden" path="location" value="${locationWrapper.location}" />
                    <form:label path="location" cssClass="my-auto"><messages:message code="Location.${locationWrapper.location}"/><c:out value=" (${locationWrapper.locationCount})"/></form:label>
                </div>
            </c:forEach>
            <a onclick="changeLocType()" class="text-frostl underline cursor-pointer mt-2"><messages:message code="seeMore"/></a>
        </div>
        <div id="locType1" class="w-full flex flex-col justify-start hidden">
            <c:forEach var="location" items="${locations}">
                <div class="flex flex-row">
                    <form:checkbox cssClass="mr-1 my-auto" class="hidden" path="location" value="${location}" />
                    <form:label path="location" cssClass="my-auto"><messages:message code="Location.${location}"/></form:label>
                </div>
            </c:forEach>
            <a onclick="changeLocType()" class="text-frostl underline cursor-pointer mt-2"><messages:message code="seeLess"/> </a>
        </div>
    </div>
</div>

<script>
    function showAllFilter(id){
        let element = document.getElementById(id);
        if(element.classList.contains("hidden")){
            document.getElementById(id).classList.remove("hidden")
        }else{
            document.getElementById(id).classList.add("hidden")
        }
    }

    let locType = 0;
    function changeLocType(){
        document.getElementById('locType'+locType).classList.add("hidden")
        locType = (locType+1)%2;
        document.getElementById('locType'+locType).classList.remove("hidden")
    }
</script>

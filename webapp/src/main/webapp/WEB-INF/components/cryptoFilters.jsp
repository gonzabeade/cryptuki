<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="cryptocurrencies" scope="request" type="java.util.Collection"/>
<div class="p-5 max-w-md  bg-[#FAFCFF] rounded-lg border shadow-md mx-5 mt-5">
    <div class="flex justify-between items-center">
        <h5 class="text-xl  text-polard font-bold leading-none my-auto" ><messages:message code="cryptocurrencies"/></h5>
        <div onclick="showAllFilter('allPossibleCripto')">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 cursor-pointer" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
            </svg>
        </div>

    </div>
    <div class="flow-root" id="allPossibleCripto">
        <ul role="list" class="divide-y divide-gray-200 ">
            <div class="flex flex-row justify-end my-3">
                <h1 id="selectAll" onclick="selectAllCrypto()" class=" cursor-pointer text-frostdr font-semibold text-sm"><messages:message code="selectAll"/></h1>
                <h1 id="deselectAll" onclick="desSelectAllCrypto()" class="hidden cursor-pointer text-frostdr font-semibold text-sm"><messages:message code="desSelectAll"/></h1>
            </div>
            <c:forEach var="coin" items="${cryptocurrencies}">
                <jsp:include page="cryptoSlider.jsp">
                    <jsp:param name="commercialName" value="${coin.commercialName}"/>
                    <jsp:param name="code" value="${coin.code}"/>
                </jsp:include>
            </c:forEach>

        </ul>
    </div>
</div>
<script>
    function selectAllCrypto() {
        document.getElementsByName("coins").forEach((coin)=>{
            if(!coin.checked){
                coin.click()
            }
        })
        document.getElementById("deselectAll").classList.remove("hidden")
        document.getElementById("selectAll").classList.add("hidden")
    }
    function desSelectAllCrypto() {
        document.getElementsByName("coins").forEach((coin)=>{
          if(coin.checked){
              coin.click()
          }
        })
        document.getElementById("deselectAll").classList.add("hidden")
        document.getElementById("selectAll").classList.remove("hidden")
    }
</script>
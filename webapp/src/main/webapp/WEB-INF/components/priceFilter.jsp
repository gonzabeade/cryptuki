<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="cryptocurrencies" scope="request" type="java.util.Collection"/>
<div class="p-5 max-w-md  bg-[#FAFCFF] rounded-lg border shadow-md mx-5 mt-5">
    <div class="flex justify-between items-center">
        <h5 class="text-xl  text-polard font-bold leading-none my-auto" ><messages:message code="maximum"/> <messages:message code="price"/> </h5>
    </div>
    <div class="flex flex-row" id="price">
        <div class="mx-auto flex flex-row">
            <input  type="number" id="max" class="bg-transparent border-b-2 border-gray-400 focus:outline-0 w-full m-4 h-10 mx-auto">
            <p class="my-auto mx-auto">   ARS</p>

        </div>
    </div>
</div>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: marcos
  Date: 8/4/2022
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<c:url value="${param.url}"/>"  method="post" class="flex flex-col min-w-[50%]">
    <div class="flex flex-col p-5 justify-center">
        <label class="text-xl font-sans text-polard font-semibold mb-3 text-center" for="email">Email</label>
        <div class="flex-row justify-center">
            <input type="email" id="email" name="email" class=" min-w-full h-10 justify-center rounded-lg p-3">
        </div>

    </div>
    <div class="flex flex-col p-5 ">
        <label  for="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Mensaje</label>
        <div class="flex-row justify-center">
            <textarea class="min-w-full h-32 rounded-lg mx-auto p-5" id="message" name="message" > </textarea>
        </div>

    </div>
    <div class="flex flex-row p-5">
        <button type="submit" class="bg-frost text-white  mt-4 mb-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Enviar</button>
    </div>
</form>

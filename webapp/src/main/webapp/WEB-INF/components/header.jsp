<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="bg-[#FAFCFF] border-gray-200 py-3 h-20"> <!--OK-->
    <div class="container flex flex-wrap mx-auto my-auto px-[10%] h-full justify-between">
        <div class="flex justify-center center w-1/4">
            <div class="flex w-full my-2">
                <a href="<c:url value="/1"/>"><img class='object-contain' src="<c:url value="/public/images/logo.png"/>" alt="logo"></a>
            </div>
        </div>
        <div class="flex justify-end">
            <ol class="flex mt-4">
                <li>
                    <a href="<c:url value="/"/>" class="py-2 pr-4 pl-3 text-polar text-xl font-bold hover:underline hover:decoration-frostdr hover:underline-offset-8">Home</a>

                </li>
                <li>
                    <a href="./coins" class="py-2 pr-4 pl-3 text-polar text-xl font-bold hover:underline hover:decoration-frostdr hover:underline-offset-8">Monedas</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/contact" class="py-2 pr-4 pl-3 text-polar font-bold text-xl hover:underline hover:decoration-frostdr hover:underline-offset-8 ">Contactate</a>
                </li>
            </ol>
        </div>
    </div>
</nav>

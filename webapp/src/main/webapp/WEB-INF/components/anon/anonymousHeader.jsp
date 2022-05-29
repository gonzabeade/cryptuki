<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="bg-[#FAFCFF] border-gray-200 py-3 h-20"> <!--OK-->
    <div class="container flex flex-wrap mx-auto my-auto px-4 h-full justify-between">
        <div class="flex justify-center center w-[15%] my-auto xl:w-1/4">
            <div class="flex w-full my-2">
                <a href="<c:url value="/buyer/market"/>"><img class='object-contain' src="<c:url value="/public/images/logo.png"/>"
                                                              alt="logo"></a>
            </div>
        </div>
        <div class="flex justify-end">
            <ol class="flex justify-between mt-4">
                <%--                <li>--%>
                <%--                    <a href="<c:url value="/mytrades"/>"--%>
                <%--                       class="py-2 pr-4 pl-3 text-polar font-bold text-xl hover:underline hover:decoration-frostdr hover:underline-offset-8 "><messages:message code="yourTrades"/></a>--%>
                <%--                </li>--%>
                <li>
                    <a href="<c:url value="/buyer/market"/>"
                       class="py-2 pr-4 pl-3 text-polar text-xl font-bold hover:underline hover:decoration-frostdr hover:underline-offset-8"><messages:message code="explore"/></a>
                </li>
                <li>
                    <a href="<c:url value="/coins"/>"
                       class="py-2 pr-4 pl-3 text-polar text-xl font-bold hover:underline hover:decoration-frostdr hover:underline-offset-8"><messages:message code="cryptocurrencies"/></a>
                </li>
                    <li class="mt-1 mr-2">
                        <a href="<c:url value="/login"/>"
                           class="py-2 pr-4 pl-3  text-polar font-bold rounded-lg bg-storml border-2 border-polar my-auto mx-auto"><messages:message
                                code="logIn"/></a>
                    </li>
                    <li class="mt-1">
                        <a href="<c:url value="/register"/>"
                           class="py-2 pr-4 pl-3  text-polar font-bold rounded-lg bg-storml border-2 border-polar my-auto mx-auto"><messages:message
                                code="register"/></a>
                    </li>
                <%--                <li>--%>
                <%--                    <a href="<c:url value="/contact"/>"--%>
                <%--                       class="py-2 pr-4 pl-3 text-polar font-bold text-xl hover:underline hover:decoration-frostdr hover:underline-offset-8 "><messages:message code="contact"/></a>--%>
                <%--                </li>--%>
            </ol>
        </div>
    </div>
</nav>
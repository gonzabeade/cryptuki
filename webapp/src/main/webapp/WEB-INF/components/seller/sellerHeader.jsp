<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="bg-[#FAFCFF] border-gray-200 py-3 h-20"> <!--OK-->
    <div class="container flex flex-wrap mx-auto my-auto px-4 h-full justify-between">
        <div class="flex justify-center center w-[15%] my-auto xl:w-1/4">
            <div class="flex flex-col w-full mt-2">
                <a class="mx-auto" href="<c:url value="/"/>"><img class='object-contain' src="<c:url value="/public/images/logo.png"/>" alt="logo"></a>
            </div>
        </div>
        <div class="flex justify-end">
            <ol class="flex justify-between mt-4">
<%--                <li>--%>
<%--                    <a href="<c:url value="/contact"/>"--%>
<%--                       class="py-2 pr-4 pl-3 text-polar font-bold text-xl hover:underline hover:decoration-frostdr hover:underline-offset-8 "><messages:message code="contact"/></a>--%>
<%--                </li>--%>
                <li class="mt-1 mx-2">
                    <a href="<c:url value="/buyer/market"/>" class="py-2 pr-4 pl-3  text-polar font-bold rounded-lg bg-storml border-2 border-polar my-auto mx-auto"><messages:message code="switchBuyer"/></a>
                </li>
                <li class="-mt-3 mx-2">
                    <a href="<c:url value="/seller/"/>" class="text-polar font-bold text-xl">
                        <div class="flex flex-col">
                            <div class="mx-auto">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10" fill="none" viewBox="0 0 24 24" stroke="#3B4252" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                </svg>
                            </div>
                            <div class="mx-2 font-sans text-sm text-polar">
                                <sec:authentication property="name"/>
                            </div>
                        </div>

                    </a>
                </li>

                <li class="-mt-3 mx-2">
                    <a href="<c:url value="/logout"/>" >
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12" fill="none" viewBox="0 0 24 24" stroke="#3B4252" stroke-width="2">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                        </svg>
                    </a>
                </li>

            </ol>
        </div>
    </div>
</nav>

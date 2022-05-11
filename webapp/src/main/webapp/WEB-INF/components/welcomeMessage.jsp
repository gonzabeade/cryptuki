<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-col mt-9 mb-5">
    <h1 class="text-center text-4xl font-semibold font-sans text-polar"><messages:message code="mainTitle"/></h1>
    <h3 class="text-center text-lg font-regular font-sans mx-10 mt-3 mb-3"><messages:message code="subTitle"/></h3>
    <a href="<c:url value="/upload"/>" class=" mx-36 bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center"><messages:message code="uploadAdvertisement"/></a>
</div>
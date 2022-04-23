
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%--    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" />--%>
</head>
<body>
<h2>Verify</h2>
<c:url value="/verify" var="postPath"/>
<%--@elvariable id="CodeForm" type="ar.edu.itba.paw.cryptuki.form"--%>
<form:form modelAttribute="CodeForm" action="${postPath}" method="post">
    <div>
        <form:label path="code">code: </form:label>
        <form:input type="text" path="code"/>
        <form:input path="username" type="hidden" value="${username}"/>
        <form:errors path="code" cssClass="formError" element="p"/>
    </div>
    <div>
        <input type="submit" value="Verify!"/>
    </div>
</form:form>
</body>
</html>
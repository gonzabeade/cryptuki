<%--
  Created by IntelliJ IDEA.
  User: gonza
  Date: 1/5/22
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<c:set var="sendButton"><messages:message code="send"/></c:set>


<html>
<head>
    <title>Title</title>
</head>
<body>

<c:url value="/test" var="postUrl"/>
<form:form modelAttribute="ProfilePicForm" action="${postUrl}" method="post" enctype="multipart/form-data">
  <form:input type="file" path="multipartFile" />
    <input type="submit" value="sendButton" />

</form:form>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<h2>Hello ${greeting}!</h2>

<ol>
    <c:forEach var="emp" items="${empList}">
        <li>${emp}</li>
    </c:forEach>
</ol>

<h2>size: ${empListSize}</h2>
</body>
</html>
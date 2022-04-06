<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
			<meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script src="https://cdn.tailwindcss.com"></script>
			<script src="../config/tailwind.config.js"></script>
</head>

<body>
<ol>
    <c:forEach var="emp" items="${empList}">
        <li><jsp:include page="../components/card.jsp"/></li>
    </c:forEach>
</ol>
</body>
</html>
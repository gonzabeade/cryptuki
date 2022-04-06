<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
			<meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script src="https://cdn.tailwindcss.com"></script>
			<script src="../config/tailwind.config.js"></script>
</head>

<body>

<h2>Hello ${greeting}!</h2>

<ol>
    <c:forEach var="emp" items="${empList}">
        <>${emp}</li>
    </c:forEach>
</ol>

<h2>size: ${empListSize}</h2>
</body>
</html>
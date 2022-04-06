<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
			<meta charset="ISO-8859-1">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script src="https://cdn.tailwindcss.com"></script>
			<script src="../config/tailwind.config.js"></script>
</head>

<body>
<jsp:include page="../components/header.jsp"/>
<div class=" flex justify-center mx-10">
    <jsp:include page="../components/subir_oferta_marketplace.jsp"/>
</div>
<div class="flex justify-center mx-10">
    <ol class="min-w-full">
        <div>
            <c:forEach var="emp" items="${empList}">
                <li><jsp:include page="../components/card.jsp"/></li>
            </c:forEach>
        </div>
    </ol>
</div>

</body>
</html>
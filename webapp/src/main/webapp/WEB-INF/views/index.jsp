<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<html>
<head>
			<meta charset="ISO-8859-1">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script src="https://cdn.tailwindcss.com"></script>
			<script>
                tailwind.config = {
                    theme: {
                        extend: {
                            colors: {
                                polar: '#3B4252',
                                polard: '#2E3440',
                                polarl: '#434C5E',
                                polarlr: '#4C566A',

                                storm: '#E5E9F0',
                                stormd: '#D8DEE9',
                                storml: '#ECEFF4',

                                frostl: '#8FBCBB',
                                frost: '#88C0D0',
                                frostd: '#81A1C1',
                                frostdr: '#5E81AC',

                                nred: '#BF616A',
                                norange: '#D08770',
                                nyellow: '#EBCB8B',
                                ngreen: '#A3BE8C',
                                npurple: '#B48EAD'

                            },
                            fontFamily: {
                                sans: ['Roboto', 'sans-serif'],
                            },
                        }
                    }
                }
            </script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">

</head>
<body class="bg-storml">
<jsp:include page="../components/header.jsp"/>
<div class=" flex justify-center mx-10">
    <jsp:include page="../components/welcome_message.jsp"/>
</div>
<div class="flex justify-center mx-48">
    <ol class="min-w-full">
        <div>
            <c:forEach var="offer" items="${offerList}">
                <li>
                    <c:set  var="accepted_payments" value="${payments}" scope="request"/>
                    <jsp:include page="../components/card.jsp">
                        <jsp:param name="currency" value="${offer.coin_id}"/>
                        <jsp:param name="user" value="pepito"/>
                        <jsp:param name="asking_price" value="${offer.askingPrice}"/>
                        <jsp:param name="trades" value="2"/>
                        <jsp:param name="offerId" value="${offer.id}"/>
                    </jsp:include>
                </li>
            </c:forEach>
        </div>
    </ol>
</div>

</body>
</html>
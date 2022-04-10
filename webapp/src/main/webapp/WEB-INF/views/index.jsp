<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                .shape-blob {
                    background: #81A1C1;
                    height: 200px;
                    width: 200px;
                    border-radius: 30% 50% 20% 40%;
                    animation:
                            transform 20s ease-in-out infinite both alternate,
                            movement_one 40s ease-in-out infinite both;
                    opacity:.2;
                    z-index: -1;
                    position: absolute;
                    left: 70%;
                    top: 50%;
                }

                .shape-blob.one{
                    height: 500px;
                    width: 500px;
                    left: -200px;
                    top: -150px;
                    transform: rotate(-180deg);
                    animation: transform 30s ease-in-out infinite both alternate, movement_two 60s ease-in-out infinite both;
                }

                .shape-blob.two{
                    height: 350px;
                    width: 350px;
                    left: 80%;
                    top: -150px;
                    transform: rotate(-180deg);
                    animation: transform 30s ease-in-out infinite both alternate, movement_two 60s ease-in-out infinite both;
                }

                @keyframes transform
                {
                    0%,
                    100% { border-radius: 33% 67% 70% 30% / 30% 30% 70% 70%; }
                    20% { border-radius: 37% 63% 51% 49% / 37% 65% 35% 63%; }
                    40% { border-radius: 36% 64% 64% 36% / 64% 48% 52% 36%; }
                    60% { border-radius: 37% 63% 51% 49% / 30% 30% 70% 70%; }
                    80% { border-radius: 40% 60% 42% 58% / 41% 51% 49% 59%; }
                }


                @keyframes movement_one
                {
                    0%,
                    100% { transform: none; }
                    50% { transform: translate(50%, 20%) rotateY(10deg) scale(1.2); }
                }

                @keyframes movement_two
                {
                    0%,
                    500% { transform: none; }
                    50% { transform: translate(50%, 20%) rotate(-200deg) scale(1.2);}
                }
            </style>
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
<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>





</body>
</html>
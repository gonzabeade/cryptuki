<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>

    <div class="flex flex-row mx-40 justify-center ">
        <c:choose>
            <c:when test="${param.pages > 0}">
                <div class="my-auto">
                    <c:if  test="${param.activePage > 0}">
                        <a  onclick="addPageValue(${param.activePage - 1 })" href="#" class="font-bold font-sans text-polard ">Anterior</a>
                    </c:if>
                </div>
                <c:forEach var = "i" begin = "${param.activePage - 1 < 0 ? param.activePage : param.activePage - 1 }" end = "${(param.activePage + 1 > param.pages - 1 )? param.pages - 1 : param.activePage + 1 }">
                    <c:choose>
                        <c:when test="${param.activePage == i }">
                            <a href="#" onclick="addPageValue(<c:out value="${i}"/>)" class="bg-stormd border-2 border-polard active:text-white-400 px-3 py-1 mx-4 my-5 rounded-full "><c:out value="${i+1}"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="#" onclick="addPageValue(<c:out value="${i}"/>)" class="bg-storm active:text-white-400 px-3 py-1 mx-4 my-5 rounded-full"><c:out value="${i+1}"/></a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <div class="my-auto">
                    <c:if test="${param.activePage < param.pages-1}">
                        <a   href="#" onclick="addPageValue(${param.activePage +1})" class="font-bold font-sans text-polard">Siguiente</a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="flex flex-col">
                    <h2 class="font-polard text-lg mx-auto">No hubo resultados</h2>
                    <a class="bg-polar/[0.5] text-white rounded-lg p-3 text-center" href="<c:url value="${param.baseUrl}"/>" >Volver a cargar</a>
                </div>
            </c:otherwise>

        </c:choose>
    </div>

</div>

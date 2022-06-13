<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<div class="flex flex-row h-full w-full justify-around ">
  <div class="container mx-10 h-4/5 F border-2 border-polard">
    <div class=" border rounded bg-[#FAFCFF]">
      <div>
        <div class=" flex  flex-col w-full">
          <div class="relative flex items-center p-3 border-b border-gray-300">
            <div class="flex flex-row w-1/2 justify-start">
              <img class="object-cover w-10 h-10 rounded-full" src="<c:url value="/profilepic/${param.otherUsername}"/>" alt="username" />
              <span class="block ml-2 font-bold text-gray-600 my-auto"><c:out value="${param.otherUsername}"/></span>
            </div>
            <div class="flex flex-row w-1/2 justify-end">
              <img class="object-cover w-10 h-10 rounded-full" src="<c:url value="/profilepic/${param.complainerUsername}"/>" alt="username" />
              <span class="block ml-2 font-bold text-gray-600 my-auto"><c:out value="${param.complainerUsername}"/></span>
            </div>
          </div>
          <div class="relative w-full p-6 overflow-y-auto h-[25rem]">

            <ul class="space-y-2">
              <c:forEach items="${messageCollection}" var="message">
                <c:choose>
                  <c:when test="${message.sender == param.otherUserId}">
                    <jsp:include page="../../components/chat/leftMessage.jsp">
                      <jsp:param name="msg" value="${message.message}"/>
                    </jsp:include>
                  </c:when>
                  <c:otherwise>
                    <jsp:include page="../../components/chat/rightMessage.jsp">
                      <jsp:param name="msg" value="${message.message}"/>
                    </jsp:include>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </ul>

          </div>

          <div class="flex justify-end w-full p-3  border-gray-300">
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
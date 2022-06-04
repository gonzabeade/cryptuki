<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<div class="flex flex-row h-full w-full justify-around">
  <div class="container mx-10 h-4/5 bg-[#FAFCFF] border-gray-200">
    <div class=" border rounded">
      <div>
        <div class=" flex  flex-col w-full">
          <div class="relative flex items-center p-3 border-b border-gray-300">
            <img class="object-cover w-10 h-10 rounded-full"
                 src="<c:url value="/profilepic/${param.otherUsername}"/>" alt="username" />
            <span class="block ml-2 font-bold text-gray-600"><c:out value="${param.otherUsername}"/></span>
            <%--                    Online green button, checkLastLogin--%>
            <c:if test="${true}">
              <span class="absolute w-3 h-3 bg-green-600 rounded-full left-10 top-3"></span>
            </c:if>
            <h1 class="w-full text-right text-l font-sans font-bold left-"><messages:message code="buyOrder"/> #<c:out value="${param.tradeId}"/></h1>
          </div>
          <div class="relative w-full p-6 overflow-y-auto h-[30rem]">

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

          <div class="flex justify-end w-full p-3 border-t border-gray-300">

            <form:form modelAttribute="messageForm" action="${param.url}" method="post" class="flex flex-row w-full ">
              <input type="text" placeholder="Message"
                     class="block w-full py-2 pl-4 mx-3 bg-gray-100 rounded-full outline-none focus:text-gray-700"
                     name="message" required />
              <form:input path="tradeId" type="hidden" value="${param.tradeId}"/>
              <form:input path="userId" type="hidden" value="${param.senderId}"/>


              <button type="submit">
                <svg class="w-5 h-5 text-gray-500 origin-center transform rotate-90" xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 20 20" fill="currentColor">
                  <path
                          d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z" />
                </svg>
              </button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
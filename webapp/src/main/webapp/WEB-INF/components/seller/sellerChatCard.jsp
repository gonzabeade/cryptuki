<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>

<head>
  <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
</head>

<body>
  <div class="p-4 max-w-md bg-[#FAFCFF] rounded-lg shadow-md sm:p-8">
    <div class="flex justify-between items-center mb-4">
      <h5 class="text-xl font-bold leading-none text-polar"><messages:message code="lastTransactions"/></h5>
      <a href="#" class="text-sm font-medium text-blue-600 hover:underline dark:text-blue-500">
       <messages:message code="seeMore"/>
      </a>
    </div>
    <div class="flow-root">
      <ul role="list" class="divide-y divide-gray-200">
        <li class="py-1 sm:py-4">
          <div class="flex items-center space-x-4">
            <div class="flex-shrink-0">
              <img class="w-8 h-8 rounded-full" src="https://picsum.photos/700/800" alt="Neil image">
            </div>
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-polar-600
               truncate">
                gonzabeade
              </p>
              <p class="text-sm text-gray-500 truncate"><messages:message code="madeYouAnOffer"/> </p>
            </div>
            <div class="inline-flex items-center text-base font-semibold text-polar">
              ×
            </div>
          </div>
        </li>
        <li class="py-1 sm:py-4">
          <div class="flex items-center space-x-4">
            <div class="flex-shrink-0">
              <img class="w-8 h-8 rounded-full" src="https://picsum.photos/200/300" alt="Bonnie image">
            </div>
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-polar-600 truncate"> salteno </p>
              <p class="text-sm text-gray-500 truncate"><messages:message code="madeYouAnOffer"/></p>
            </div>
            <div class="inline-flex items-center text-base font-semibold text-polar">×</div>
          </div>
        </li>
        <li class="py-1
         sm:py-4">
          <div class="flex items-center space-x-4">
            <div class="flex-shrink-0">
              <img class="w-8 h-8 rounded-full" src="https://picsum.photos/0/100" alt="Michael image">
            </div>
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-polar-600 truncate"> soutjava </p>
              <p class="text-sm text-gray-500 truncate"> <messages:message code="madeYouAnOffer"/></p>
            </div>
            <div class="inline-flex items-center text-base font-semibold text-polar"> × </div>
          </div>
        </li>
        <li class="py-1
         sm:py-4">
          <div class="flex items-center space-x-4">
            <div class="flex-shrink-0">
              <img class="w-8 h-8 rounded-full" src="https://picsum.photos/100/200" alt="Lana image">
            </div>
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-polar-600 truncate"> leomessi </p>
              <p class="text-sm text-gray-500 truncate"> <messages:message code="madeYouAnOffer"/> </p>
            </div>
            <div class="inline-flex items-center text-base font-semibold text-polar">
              ×
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</body>


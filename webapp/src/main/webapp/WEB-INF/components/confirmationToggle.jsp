<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-row mt-10" id="confirmationMessage">
  <div class="mx-auto bg-ngreen rounded-lg flex flex-row p-3">
    <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto my-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
      <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
    </svg>
    <p class="text-white p-3 text-xl text-white">${param.title}</p>
    <div onclick="hideConfirmation()" class="hover:cursor-pointer my-auto">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 " fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2" >
        <path stroke-linecap="round" stroke-linejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
    </div>
  </div>
</div>
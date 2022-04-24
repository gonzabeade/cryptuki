<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url value="/login" var="loginUrl" />

<form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded" class=" py-12 px-64 rounded-lg bg-stormd/[0.9] flex flex-col justify-center mx-auto border-2 border-polard">
  <h1 class="text-center text-4xl font-semibold font-sans text-polar">Inicia sesión</h1>
  <div class="flex flex-col mx-5 my-4">
    <label for="username" class="text-center text-xl font-bold font-sans text-polar my-2">Usuario</label>
    <input id="username" name="j_username" type="text" class="rounded-lg p-3" placeholder="e.g juanperez"/>
  </div>
  <div class="flex flex-col mx-5 my-4" >
    <label for="password" class="text-center text-xl font-bold font-sans text-polar my-2">Contraseña</label>
    <input id="password" name="j_password" type="password" class="rounded-lg p-3" placeholder="e.g micontraseña" />
  </div>
  <div class="flex flex-row mx-5" >
    <label class="flex flex-row">
      <input name="j_rememberme" type="checkbox" class="text-polard my-auto"/>
      <div class="text-polard mx-2 font-sans font-semibold">
        <spring:message code="remember_me"/>
      </div>
    </label>
  </div>
  <div class="flex flex-col mx-auto my-10" >
    <input type="submit" value="Iniciar sesión" class="rounded-lg bg-frost py-3 px-5 text-white cursor-pointer shadow-lg"/>
    <a href="<c:url value="/register"/>" class=" underline text-polard text-center mt-2"> No tienes cuenta? Registrate</a>
  </div>
</form>
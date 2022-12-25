<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Sign In">
    <form id="loginForm" class="form-horizontal" action="<c:url value="/signin"/>" method="POST" style="margin: 10px;">
        <div class="form-group" style="margin-bottom: 5px;">
            <label for="exampleInputEmail1">E-mail</label>
            <input required type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                   name="username">
        </div>
        <div class="form-group" style="margin-bottom: 5px;">
            <label for="password">Password</label>
            <input required id="password" type="password" minlength="8" maxlength="32" class="form-control"
                   name="password">
            <input type="checkbox" class="form-check-input" onclick="changePasswordVisibility()">Показать пароль</input>
        </div>
        <button type="submit" class="btn btn-dark">Войти</button>
    </form>
    <script src="/js/passwordVisibility.js"></script>
</t:mainLayout>

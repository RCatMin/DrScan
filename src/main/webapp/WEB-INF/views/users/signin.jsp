<%--
  Created by IntelliJ IDEA.
  User: TJ
  Date: 2025-02-19
  Time: 오전 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Sign In</title>
    <link rel="stylesheet" href="/style/userForm.css">
</head>
<c:import url="/header" />
<body>

<form class="form-box" id="signin-form" action="/users/action/signin" method="POST">
    <div class="input-group">
        <label for="username">ID : </label>
        <input type="text" id="username" name="username" required>
    </div>
    <div class="input-group">
        <label for="password">PW : </label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Sign In</button>
    <a href="/users/signup">Sign Up</a>
</form>

</body>
<c:import url="/footer" />
</html>

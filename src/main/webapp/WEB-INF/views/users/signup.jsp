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
    <title>Sign Up</title>
    <link rel="stylesheet" href="/style/userForm.css">
    <script type="module" src="/script/signup.js"></script>
</head>
<c:import url="/header" />
<body>

<form class="form-box" id="signup-form" action="/users/action/signup" method="POST">
    <div class="input-group">
        <label for="username">ID : </label>
        <input type="text" id="username" name="username" required>
    </div>
    <div class="input-group">
        <label for="password">PW : </label>
        <input type="password" id="password" name="password" required>
    </div>
    <div class="input-group">
        <div class="hospital-department">
            <div class="hospital">
                <label id="department-label" for="hospital">HN : </label>
                <input type="text" id="hospital" name="hospital" required>
            </div>
            <div class="department">
                <label for="department">DP : </label>
                <input type="text" id="department" name="department" required>
            </div>
        </div>
    </div>
    <div class="input-group">
        <label for="name">NAME : </label>
        <input type="text" id="name" name="name" required>
    </div>
    <div class="input-group">
        <label for="email">EMAIL : </label>
        <div class="email-container">
            <input type="text" id="email" name="email" required>
            <button type="button" id="email-verify-btn">Verify</button>
        </div>
    </div>
    <div class="input-group">
        <label for="phone">PHONE : </label>
        <input type="text" id="phone" name="phone" required>
    </div>
    <button type="submit">Sign Up</button>
    <a href="/users/signin">Sign In</a>
</form>


</body>
<c:import url="/footer" />
</html>

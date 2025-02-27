<%--
  Created by IntelliJ IDEA.
  User: TJ
  Date: 2025-02-19
  Time: 오전 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Sign In</title>
    <link rel="stylesheet" href="/style/userForm.css">
    <script type="module" src="/script/signin.js"></script>
</head>
<c:import url="/header" />
<body>

<form class="form-box" id="signin-form" action="/users/action/signin" method="POST">
    <div class="input-group" id="input-username">
        <label id="label-username" for="username">ID : </label>
        <div class="email-container">
            <input type="text" id="username" name="username">
            <button type="button" id="email-verify-btn">Verify</button>
        </div>
    </div>
    <p class="error-msg" id="error-username">아이디를 입력해주세요.</p>

    <div class="input-group" id="input-password">
        <label id="label-password" for="password">PW : </label>
        <input type="password" id="password" name="password">
    </div>
    <p class="error-msg" id="error-password">비밀번호를 입력해주세요.</p>
    <div class="input-group" id="verification-code-container" style="display: none;">
        <label id="label-verification" for="verification-code">Verification Code:</label>
        <input type="text" id="verification-code" name="verification-code">
    </div>
    <p class="error-msg" id="error-verification">인증코드를 입력해주세요.</p>
    <button type="submit">Sign In</button>
    <a id="url-signup" href="/users/signup">Sign Up</a>
</form>

</body>
<c:import url="/footer" />
</html>

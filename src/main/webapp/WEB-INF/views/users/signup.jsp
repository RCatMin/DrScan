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
    <h1 class="form-title">Sign Up</h1>
    <div class="input-group" id="input-username">
        <label id="label-username" for="username">ID : </label>
        <input class="input-box" type="text" id="username" name="username">
    </div>
    <p class="error-msg" id="error-username">&nbsp6~20자리의 영문, 숫자로 입력해주세요.</p>
    <div class="input-group" id="input-password">
        <label id="label-password" for="password">PW : </label>
        <input class="input-box" type="password" id="password" name="password">
    </div>
    <p class="error-msg" id="error-password">&nbsp특수기호를 포함한 8~16자리의 비밀번호를 입력해주세요.</p>
    <div class="input-group" id="input-hospital-department">
        <div class="hospital-department">
            <div class="hospital" id="input-hospital">
                <label id="label-hospital" for="hospital">HN : </label>
                <input class="input-box" type="text" id="hospital" name="hospital">
            </div>
            <div class="department" id="input-department">
                <label id="label-department"  for="department">DP : </label>
                <input class="input-box" type="text" id="department" name="department">
            </div>
        </div>
    </div>
    <p class="error-msg" id="error-hospital-department">&nbsp병원이름 혹은 진료과를 입력해주세요.</p>
    <div class="input-group" id="input-name">
        <label id="label-name"  for="name">NAME : </label>
        <input class="input-box" type="text" id="name" name="name">
    </div>
    <p class="error-msg" id="error-name">&nbsp2~5글자의 한글로 입력해주세요.</p>
    <div class="input-group" id="input-email">
        <label id="label-email" for="email">EMAIL : </label>
        <div class="email-container">
            <input class="input-box" type="text" id="email" name="email">
            <button type="button" id="email-verify-btn">Verify</button>
        </div>
    </div>
    <p class="error-msg" id="error-email">이메일 형식에 맞게 입력해주세요.</p>
    <div class="input-group" id="verification-code-container" style="display: none;">
        <label id="label-verification" for="verification-code">Verification Code:</label>
        <input class="input-box" type="text" id="verification-code" name="verification-code">
    </div>
    <p class="error-msg" id="error-verification">인증코드를 입력해주세요.</p>
    <div class="input-group" id="input-phone">
        <label id="label-phone" for="phone">PHONE : </label>
        <input class="input-box" type="text" id="phone" name="phone">
    </div>
    <p class="error-msg" id="error-phone">전화번호 형식에 맞게 입력해주세요.</p>
    <button type="submit">Sign Up</button>
</form>


</body>
<c:import url="/footer" />
</html>

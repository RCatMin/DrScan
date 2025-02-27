<%--
  Created by IntelliJ IDEA.
  User: TJ
  Date: 2025-02-19
  Time: 오후 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>me</title>
    <link rel="stylesheet" href="/style/userForm.css">
    <script type="module" src="/script/me.js"></script>
</head>
<c:import url="/header" />
<body>

<form class="form-box" id="me-form" action="/users/action/signup" method="POST">
    <input type="hidden" id="userCode" name="userCode" value="${authUser.userCode}">
    <div class="input-group" id="input-username">
        <label id="label-username" for="username">ID : </label>
        <input type="text" id="username" name="username" value="${authUser.username}" disabled>
    </div>
    <div class="input-group" id="input-password">
        <label id="label-password" for="password">PW : </label>
        <input type="password" id="password" name="password" value="00000000">
    </div>
    <p class="error-msg" id="error-password">&nbsp특수기호를 포함한 8~16자리의 비밀번호를 입력해주세요.</p>
    <div class="input-group" id="input-hospital-department">
        <div class="hospital-department">
            <div class="hospital" id="input-hospital">
                <label id="label-hospital" for="hospital">HN : </label>
                <input type="text" id="hospital" name="hospital" value="${authUser.hospitalName}">
            </div>
            <div class="department" id="input-department">
                <label id="label-department"  for="department">DP : </label>
                <input type="text" id="department" name="department" value="${authUser.department}">
            </div>
        </div>
    </div>
    <p class="error-msg" id="error-hospital-department">&nbsp병원이름 혹은 진료과를 입력해주세요.</p>
    <div class="input-group" id="input-name">
        <label id="label-name"  for="name">NAME : </label>
        <input type="text" id="name" name="name" value="${authUser.name}">
    </div>
    <p class="error-msg" id="error-name">&nbsp2~5글자의 한글로 입력해주세요.</p>
    <div class="input-group" id="input-email">
        <label id="label-email" for="email">EMAIL : </label>
        <div class="email-container">
            <input type="text" id="email" name="email" value="${authUser.email}" disabled>
        </div>
    </div>
    <div class="input-group" id="input-phone">
        <label id="label-phone" for="phone">PHONE : </label>
        <input type="text" id="phone" name="phone" value="${authUser.phone}">
    </div>
    <p class="error-msg" id="error-phone">전화번호 형식에 맞게 입력해주세요.</p>
    <p class="error-msg" id="error-phone2">전화번호가 중복됩니다.</p>
    <div class="button-container">
        <button type="submit" class="btn edit-btn">Edit</button>
        <button type="button" id="withdraw-btn" class="btn withdraw-btn">Withdraw</button>
    </div>
</form>

<div class="modal" id="myModal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>탈퇴신청을 위해 비밀번호를 다시 입력해주세요.</p>
        <div class="input-group">
            <label for="rewrite-password">Password : </label>
            <input type="password" id="rewrite-password" name="rewrite-password">
        </div>
        <button id="confirm-btn">확인</button>
    </div>
</div>

</body>
<c:import url="/footer" />
</html>
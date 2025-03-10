<%--
  Created by IntelliJ IDEA.
  User: kokoz
  Date: 2025-02-20
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<html>
<head>
    <title>진료등록페이지</title>
    <link rel="stylesheet" href="/style/addClinic.css">
    <script src="/script/add-clinic.js" type="module"></script>
</head>
<c:import url="/header" />
<body>
<div id ="content-container">
    <h1 id ="patient-code"></h1>
    <form id="addClinic-form" action="/clinic/action" method="POST">
        <div class="input-group">
            <label for="userCode">의사 ID:</label>
            <input type="text" id="userCode" name="userCode" value="${authUser.userCode}" disabled>
        </div>

        <div class="input-group">
            <label for="clinicDate">진료 날짜:</label>
            <input type="date" id="clinicDate" name="clinicDate" required>
        </div>

        <div class="input-group textarea-group">
            <label for="context">진료 내용:</label>
            <textarea id="context" name="context" required></textarea>
        </div>

        <button type="submit">진료 등록</button>
    </form>


</div>
</body>
<c:import url="/footer" />
</html>

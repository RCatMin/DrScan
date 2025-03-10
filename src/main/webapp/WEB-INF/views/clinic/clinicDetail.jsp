<%--
  Created by IntelliJ IDEA.
  User: kokoz
  Date: 2025-02-24
  Time: 오후 5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
  <title>진료 상세정보</title>
  <link rel="stylesheet" href="/style/clinicDetail.css">
  <script src ="/script/clinic-detail.js" type="module"></script>
</head>
<c:import url="/header" />
<body>
<div id ="content-container">
  <h1 id="title-name">진료 상세 정보</h1>
  <form id="clinic-form">
    <div class="input-group">
      <label for="clinicCode">진료 코드:</label>
      <input type="text" id="clinicCode" disabled>
    </div>
    <div class="input-group">
      <label for="patientCode">환자 코드:</label>
      <input type="text" id="patientCode" disabled>
    </div>
    <div class="input-group">
      <label for="clinicDate">진료 날짜:</label>
      <input type="date" id="clinicDate">
    </div>
    <div class="input-group textarea-group">
      <label for="context">진료 내용:</label>
      <textarea id="context"></textarea>
    </div>
    <button type="button" id="update-button">수정</button>
    <button type="button" id="delete-button">삭제</button>
  </form>
</div>
</body>
<c:import url="/footer" />
</html>

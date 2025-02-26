<%--
  Created by IntelliJ IDEA.
  User: kokoz
  Date: 2025-02-20
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
    String patientCode = request.getParameter("patientCode");
%>
<html>
<head>
    <title>진료등록페이지</title>
</head>
<c:import url="/header" />
<body>
<div id ="content-container">
    <form id="addClinic-form" action="/clinic/action" method="POST">
        <input type="text" name="patientCode" value="<%= patientCode %>">
        <input type="text" name="userCode" value="1005">
        <label for="context">진료 내용:</label>
        <textarea id="context" name="context"></textarea>
        <label for="clinicDate">진료 날짜:</label>
        <input type="date" id="clinicDate" name="clinicDate">
        <button type="submit">등록</button>
    </form>
</div>
</body>
<c:import url="/footer" />
</html>

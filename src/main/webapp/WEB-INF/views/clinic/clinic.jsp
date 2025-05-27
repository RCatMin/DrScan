<%--
  Created by IntelliJ IDEA.
  User: kokoz
  Date: 2025-02-19
  Time: 오후 5:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>진료페이지</title>
    <link rel="stylesheet" href="/style/clinic.css">
    <script src ="/script/clinic.js" type="module"></script>
</head>
<c:import url="/header" />
<body>
<div id ="content-container">
    <h1 id="patient-code"></h1>
    <div class = "clinic-list-container">
        <table id="clinic-list">
        <thead>
            <tr>
                <th>진료코드</th>
                <th>환자코드</th>
                <th>진료날짜</th>
                <th>진료내용</th>
                <th>등록일시</th>
                <th>수정일시</th>
                <th>상세보기</th>
            </tr>
        </thead>
        <tbody id="clinic-body">
        </tbody>
        </table>
        <div id="pagination"></div>
    </div>
        <button type="button" id="add-button">진료 추가</button>
</div>
</body>
<c:import url="/footer" />
</html>

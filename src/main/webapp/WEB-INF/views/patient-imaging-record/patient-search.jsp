<%--
  Created by IntelliJ IDEA.
  User: gusrb
  Date: 2025-02-21
  Time: 오전 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>환자 정보 검색 페이지</title>
    <link rel="stylesheet" type="text/css" href="/style/patient-search.css">
    <script src="/script/patient-search.js" defer></script>
</head>
<c:import url="/header" />
<body>
<div class="container">
    <div class="search-box">
        <h2>환자 코드를 입력하세요</h2>
        <input type="text" id="patientCode" placeholder="환자 코드 입력">
        <button onclick="searchPatient()">검색</button>
    </div>

    <div id="resultSection" style="display: none;">
        <h3>검색 결과</h3>
        <table>
            <thead>
            <tr>
                <th>환자성명</th>
                <th>환자 ID</th>
                <th>성별</th>
                <th>생년월일</th>
                <th>촬영기기</th>
                <th>설명</th>
                <th>촬영날짜</th>
                <th>촬영시간</th>
                <th>진료 기록</th>
                <th>영상 판독</th>
            </tr>
            </thead>
            <tbody id="patientRecords">
            </tbody>
        </table>
        <div id="pagination" class="pagination-container" style="text-align: center; margin-top: 10px;">
        </div>
    </div>
</div>
</body>
<c:import url="/footer" />
</html>

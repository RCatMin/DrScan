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
    <div class="search-container">
        <label class="search-label">환자 코드를 입력하세요.</label>
        <input type="text" id="searchPid" placeholder="환자 코드">
        <input type="text" id="searchPname" placeholder="환자 이름">
        <select id="searchPsex">
            <option value="">전체</option>
            <option value="M">남</option>
            <option value="F">여</option>
        </select>
        <input type="date" id="searchPbirthdate">
        <button id="btn-search" onclick="searchPatient()">검색</button>
    </div>


    <div id="resultSection" style="display: none;">
        <h3 id="result-head">목록</h3>
        <table>
            <thead>
            <tr>
                <th onclick="sortTable('pname')">환자성명</th>
                <th onclick="sortTable('pid')">환자 ID</th>
                <th onclick="sortTable('psex')">성별</th>
                <th onclick="sortTable('pbirthdate')">생년월일</th>
                <th onclick="sortTable('modality')">촬영기기</th>
                <th onclick="sortTable('studydesc')">설명</th>
                <th onclick="sortTable('studydate')">촬영날짜</th>
                <th onclick="sortTable('studytime')">촬영시간</th>
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

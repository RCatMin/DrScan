<%--
  Created by IntelliJ IDEA.
  User: gusrb
  Date: 2025-03-06
  Time: 오후 3:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>판독 상세 정보 페이지</title>
    <link rel="stylesheet" href="/style/reportDetail.css">
    <script src="/script/report-detail.js"></script>
</head>
<c:import url="/header" />
<body>
<div id="content-container">
    <h1 id="content-head">판독 상세 정보</h1>

    <div class="section">
        <h2 class="section-head">- 환자 정보</h2>
        <label>판독코드: </label> <span id="reportCode"></span><br>
        <label>환자 ID: </label> <span id="patientId"></span><br>
        <label>환자 이름: </label> <span id="patientName"></span><br>
        <label>환자 생년월일: </label> <span id="patientBirthDate"></span><br>
        <label>환자 나이: </label> <span id="patientAge"></span>
    </div>

    <div class="section">
        <h2 class="section-head">- 검사 정보</h2>
        <label>판독 의사: </label> <span id="doctor"></span><br>
        <label>판독 승인 날짜: </label> <span id="approveDate"></span><br>
        <label>검사 이름: </label> <span id="studyName"></span><br>
        <label>검사 날짜: </label> <span id="studyDate"></span><br>
        <label>검사 장비: </label> <span id="modality"></span><br>
        <label>검사 부위: </label> <span id="bodyPart"></span>
    </div>

    <div class="input-group">
        <label>중증도 레벨: </label>
        <select id="severityLevel">
            <option value="1">1 - Critical (위급)</option>
            <option value="2">2 - Urgent (긴급)</option>
            <option value="3">3 - High (높음)</option>
            <option value="4">4 - Moderate (보통)</option>
            <option value="5">5 - Low (낮음)</option>
        </select>
    </div>

    <div class="input-group">
        <label>보고서 상태: </label>
        <select id="reportStatus">
            <option value="Draft">Draft</option>
            <option value="Finalized">Finalized</option>
            <option value="Needs Revision">Needs Revision</option>
        </select>
    </div>

    <div class="input-group textarea-group">
        <label>판독 내용: </label>
        <textarea id="reportText" rows="4"></textarea>
    </div>

    <button id="update-button">수정</button>
    <button id="delete-button">삭제</button>
</div>
</body>
<c:import url="/footer" />
</html>

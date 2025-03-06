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
    <title>Title</title>
    <link rel="stylesheet" href="/style/reportDetail.css">
    <script src="/static/js/report-detail.js"></script>
</head>
<c:import url="/header" />
<body>
<div id="content-container">
    <h1 id="title-name">판독 상세 정보</h1>

    <div class="input-group">
        <label>환자 ID:</label>
        <input type="text" id="patientId" readonly>
    </div>

    <div class="input-group">
        <label>검사 날짜:</label>
        <input type="text" id="studyDate" readonly>
    </div>

    <div class="input-group">
        <label>검사 이름:</label>
        <input type="text" id="studyName" readonly>
    </div>

    <div class="input-group">
        <label>검사 부위:</label>
        <input type="text" id="bodyPart" readonly>
    </div>

    <div class="input-group">
        <label>중증도 레벨:</label>
        <select id="severityLevel">
            <option value="1">1 - Low</option>
            <option value="2">2 - Mild</option>
            <option value="3">3 - Moderate</option>
            <option value="4">4 - Severe</option>
            <option value="5">5 - Critical</option>
        </select>
    </div>

    <div class="input-group">
        <label>보고서 상태:</label>
        <select id="reportStatus">
            <option value="Draft">Draft</option>
            <option value="Finalized">Finalized</option>
            <option value="Needs Revision">Needs Revision</option>
        </select>
    </div>

    <div class="input-group textarea-group">
        <label>판독 내용:</label>
        <textarea id="reportText" rows="4"></textarea>
    </div>

    <button id="update-button">수정</button>
    <button id="delete-button">삭제</button>
</div>
</body>
<c:import url="/footer" />
</html>

<%--
  Created by IntelliJ IDEA.
  User: gusrb
  Date: 2025-02-25
  Time: 오전 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <link rel="stylesheet" href="/style/imaging-record.css">
    <script type="module" src="/dist/bundle.js"></script>
    <title>영상 판독 페이지</title>
</head>
<c:import url="/header" />
<body>

<div id="container">
    <div id="leftPanel">
        <p class="title">시리즈 목록</p>
        <div id="thumbnailContainer"></div>
        <div id="pagination">
            <button id="prevPageBtn" class="pagination-btn">&lt;</button>
            <div id="pageButtons"></div>
            <button id="nextPageBtn" class="pagination-btn">&gt;</button>
        </div>
    </div>

    <div id="viewerContainer">
        <div id="dicomViewport"></div>
    </div>

    <div id="info">
        <div class="info-container">
            <h3 class="title">환자 정보</h3>
            <p> ID : <span id="patientId">-</span></p>
            <p> 이름 : <span id="patientName">-</span></p>
            <p> 성별 : <span id="patientSex">-</span></p>
            <p> 생년월일 : <span id="patientBirth">-</span></p>
        </div>

        <div class="info-container">
            <h3>Study 정보</h3>
            <p><strong> 검사명:</strong> <span id="studyDesc"> 로딩 중...</span></p>
            <p><strong> 모달리티(검사 장비):</strong> <span id="modality"> 로딩 중...</span></p>
            <p><strong> 검사 부위:</strong> <span id="bodyPart"> 로딩 중...</span></p>
            <p><strong> 접수번호:</strong> <span id="accessNum"> 로딩 중...</span></p>
            <p><strong> 검사 날짜:</strong> <span id="studyDate"> 로딩 중...</span></p>
            <p><strong> 시리즈 개수:</strong> <span id="seriesCnt"> 로딩 중...</span></p>
        </div>

        <div class="info-container">
            <h3>Series 정보</h3>
            <p><strong> 시리즈명:</strong> <span id="seriesDesc"> 로딩 중...</span></p>
            <p><strong> 모달리티:</strong> <span id="seriesModality"> 로딩 중...</span></p>
            <p><strong> 시리즈 날짜:</strong> <span id="seriesDate"> 로딩 중...</span></p>
            <p><strong> 이미지 개수:</strong> <span id="imageCnt"> 로딩 중...</span></p>
            <p><strong> 시리즈 번호:</strong> <span id="seriesNum"> 로딩 중...</span></p>
        </div>

        <div class="info-container">
            <h3>판독 결과</h3>

            <p><strong>중증도 레벨:</strong></p>
            <div class="severity-level">
                <label><input type="radio" name="severityLevel" value="1"> 1</label>
                <label><input type="radio" name="severityLevel" value="2"> 2</label>
                <label><input type="radio" name="severityLevel" value="3"> 3</label>
                <label><input type="radio" name="severityLevel" value="4"> 4</label>
                <label><input type="radio" name="severityLevel" value="5"> 5</label>
            </div>
            <p><strong>보고서 상태:</strong></p>
            <div class="report-status">
                <label><input type="radio" name="reportStatus" value="Draft"> Draft</label>
                <label><input type="radio" name="reportStatus" value="Finalized"> Finalized</label>
                <label><input type="radio" name="reportStatus" value="Needs Revision"> Needs Revision</label>
            </div>

            <p><strong>판독 내용:</strong></p>
            <textarea id="reportText" rows="4"></textarea>

            <div class="button-group">
                <button id="saveReportBtn">저장</button>
                <button id="editReportBtn">수정</button>
            </div>
        </div>

        <p id="autoSaveStatus"></p>
    </div>
</div>

</body>
<c:import url="/footer" />
</html>

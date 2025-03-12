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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="/style/imaging-record.css">
<%--    <script type="module" src ="/script/dicomViewer.js"></script>--%>
<%--    <script type="module" src ="/script/mysqlReport.js"></script>--%>
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
        <div id="toolButtons">
            <button id="zoomBtn"><i class="fa-solid fa-magnifying-glass-plus"></i></button>
            <button id="panBtn"><i class="fa-solid fa-arrows-alt"></i></button>
            <button id="windowLevelBtn"><i class="fa-solid fa-adjust"></i></button>
            <button id="resetBtn"><i class="fa-solid fa-rotate-left"></i></button>
            <button id="lengthMeasureBtn"><i class="fa-solid fa-ruler"></i></button>
            <button id="angleMeasureBtn"><i class="fa-solid fa-drafting-compass"></i></button>
            <button id="multiViewportBtn"><i class="fa-solid fa-th-large"></i></button>
            <button id="histogramAdjustBtn"><i class="fa-solid fa-chart-bar"></i></button>
        </div>

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
            <p><strong> 검사명 : </strong> <span id="studyDesc">-</span></p>
            <p><strong> 모달리티(검사 장비):</strong> <span id="modality">-</span></p>
            <p><strong> 검사 부위:</strong> <span id="bodyPart">-</span></p>
            <p><strong> 접수번호:</strong> <span id="accessNum">-</span></p>
            <p><strong> 검사 날짜:</strong> <span id="studyDate">-</span></p>
            <p><strong> 시리즈 개수:</strong> <span id="seriesCnt">-</span></p>
        </div>

        <div class="info-container">
            <h3>Series 정보</h3>
            <p><strong> 시리즈명:</strong> <span id="seriesDesc">-</span></p>
            <p><strong> 모달리티:</strong> <span id="seriesModality">-</span></p>
            <p><strong> 시리즈 날짜:</strong> <span id="seriesDate">-</span></p>
            <p><strong> 이미지 개수:</strong> <span id="imageCnt">-</span></p>
            <p><strong> 시리즈 번호:</strong> <span id="seriesNum">-</span></p>
        </div>

        <div class="info-container">
            <h3>판독 결과</h3>
            <p>판독 의사 : <span id="userCode">1020</span></p>
            <p>승인 의사 : <span id="approveUserCode">1024</span></p>
            <p>판독 승인 날짜 : <span id="approveStudyDate">20250305</span></p>

            <p><strong>중증도 레벨:</strong></p>
            <select id="severityLevel">
                <option value="1">1 - Low</option>
                <option value="2">2 - Mild</option>
                <option value="3">3 - Moderate</option>
                <option value="4">4 - Severe</option>
                <option value="5">5 - Critical</option>
            </select>

            <p><strong>보고서 상태:</strong></p>
            <select id="reportStatus">
                <option value="Draft">Draft</option>
                <option value="Finalized">Finalized</option>
                <option value="Needs Revision">Needs Revision</option>
            </select>


            <p><strong>판독 내용:</strong></p>
            <textarea id="reportText" rows="4"></textarea>

            <div class="button-group">
                <button id="saveReportBtn">저장</button>
                <button id="editReportBtn">판독 목록</button>
            </div>
        </div>

        <p id="autoSaveStatus"></p>
    </div>
</div>

</body>
<c:import url="/footer" />
</html>

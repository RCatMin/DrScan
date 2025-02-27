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

    <div id="patientInfo">
        <p class="title">환자 정보</p>
        <p>ID : <span id="patientId">-</span></p>
        <p>이름 : <span id="patientName">-</span></p>
        <p>성별 : <span id="patientSex">-</span></p>
        <p>생년월일 : <span id="patientBirth">-</span></p>
    </div>
</div>

</body>
<c:import url="/footer" />
</html>

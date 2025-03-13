<%--
  Created by IntelliJ IDEA.
  User: gusrb
  Date: 2025-03-06
  Time: 오전 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>판독 기록</title>
    <link rel="stylesheet" href="/style/patient.css">
    <script src="/script/radiologist-reports.js" defer></script>
</head>
<c:import url="/header" />
<body>
<div id="content-container">
    <h2 id="title-name">환자 판독 기록</h2>

    <table>
        <thead>
        <tr>
            <th id="sort-reportCode">판독 코드 ⬍</th>
            <th id="sort-severityLevel">중증도 레벨 ⬍</th>
            <th>환자 ID</th>
            <th id="sort-reportStatus">보고서 상태 ⬍</th>
            <th id="sort-studyDate">검사 날짜 ⬍</th>
            <th id="sort-reportText">판독 내용 ⬍</th>
            <th id="sort-regDate">등록일 ⬍</th>
            <th id="sort-modDate">수정일 ⬍</th>
            <th>상세보기</th>
        </tr>
        </thead>
        <tbody id="reportTableBody">
        <!-- 판독 기록이 동적으로 들어감 -->
        </tbody>
    </table>

    <div class="pagination"></div>
</div>
</body>
<c:import url="/footer" />
</html>

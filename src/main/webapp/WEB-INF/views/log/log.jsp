<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>로그 목록</title>
    <link rel="stylesheet" type="text/css" href="/style/viewerList.css">
    <script src="/script/log.js" defer></script>
</head>
<c:import url="/header" />
<body>
<div class="search-bar">
    <input type="number" id="search" placeholder="User Code" value="${param.search}">
    <button id="btn-search" onclick="search()">🔍 Search</button>
</div>

<table class="search-results">
    <thead>
    <tr>
        <th>사용자 코드</th>
        <th>판독코드</th>
        <th>진료코드</th>
        <th>내용</th>
        <th>발생일시</th>
    </tr>
    </thead>
    <tbody id="log-list">
    </tbody>
</table>

<div id="pagination">
    <button id="prev" onclick="goToPage(currentPage - 1)" disabled>◀ Prev</button>
    <button id="next" onclick="goToPage(currentPage + 1)" disabled>Next ▶</button>
</div>
</body>
<c:import url="/footer" />
</html>

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
    <script type="module" src="/dist/bundle.js"></script>
    <title>영상 판독 페이지</title>
</head>
<c:import url="/header" />
<body>
    <div id="dicomContainer">
        <p>이미지 로딩중...</p>
    </div>
</body>
<c:import url="/footer" />
</html>

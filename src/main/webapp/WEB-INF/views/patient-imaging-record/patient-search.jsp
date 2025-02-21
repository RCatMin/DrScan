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
</head>
<c:import url="/header" />
<body>
    <div>
        <h1>환자 정보를 입력하세요</h1>
        <input type="text" id="patientId" placeholder="환자 ID 입력">
        <button>검색</button>
    </div>
</body>
<c:import url="/footer" />
</html>

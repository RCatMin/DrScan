<%--
  Created by IntelliJ IDEA.
  User: iminsu
  Date: 2025. 2. 20.
  Time: 오후 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>환자 판독</title>
</head>
<body>
<h2>환자 검사 리포트</h2>
<c:out value="${patientReport}" />

<c:if test="${not empty patientReport}">
    <h3>환자 정보</h3>
    <p>환자 ID: ${patientReport.patienttab.pid}</p>
    <p>이름: ${patientReport.patienttab.pname}</p>
    <p>생년월일: ${patientReport.patienttab.pbirthDate}</p>
    <p>성별: ${patientReport.patienttab.psex}</p>

</c:if>
</body>
</html>



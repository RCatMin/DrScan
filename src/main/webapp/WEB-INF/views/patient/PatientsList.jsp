<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html lang="ko">
<head>
  <title>진료페이지</title>
  <link rel="stylesheet" href="/style/patient.css">
  <script src="/script/patient.js" defer></script> <!-- pagination.js 추가 -->
</head>
<c:import url="/header" />
<body>
<div id="content-container">
  <h2 id="title-name">환자 목록</h2>
  <table>
    <thead>
    <tr>
      <th>환자 ID</th>
      <th>이름</th>
      <th>성별</th>
      <th>생년월일</th>
      <th>진료기록</th> <!-- 진료보기 버튼 추가 -->
      <th>판독기록</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="patient" items="${patients}">
      <tr>
        <td>${patient.pid}</td>
        <td>${patient.pname}</td>
        <td>${patient.psex}</td>
        <td>${patient.pbirthdate}</td>
        <td>
          <button class="clinic-btn" data-pid="${patient.pid}">진료보기</button> <!-- data-pid 속성 추가 -->
        </td>
        <td>
          <button class="report-btn" data-pid="${patient.pid}">판독보기</button>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="pageNum">
      <button class="pagination-btn ${pageNum == currentPage ? 'active' : ''}" data-page="${pageNum}">
          ${pageNum}
      </button>
    </c:forEach>
  </div>
</div>
</body>
<c:import url="/footer" />
</html>

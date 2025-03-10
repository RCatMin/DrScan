<%--
  Created by IntelliJ IDEA.
  User: iminsu
  Date: 2025. 2. 14.
  Time: 오후 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"  href="/style/reset.css">
    <link rel="stylesheet" href="/style/header.css">
    <link rel="shortcut icon"
          href="https://store-images.s-microsoft.com/image/apps.6418.14364493555984900.9ea956f3-4735-41a8-b265-5c9a29b0fa8c.aa7487ad-5d24-4776-8364-cdfa4576252e">
    <meta property="og:title" content="DrScan">
    <meta property="og:description" content="DICOM활용 의료영상 서비스">
    <meta property="og:image"
          content="https://cmsmedia.defense.gov/2024/Dec/03/2003597411/740/740/0/241203-M-VW876-001.JPG?sv=2024-11-04&ss=b&srt=o&spr=https&se=2025-02-18T03%3A30%3A41Z&sp=r&sig=uPIfIjSAWAqwg0AscqNVxyZoL1nTPY9HBvcnwNVaUVA%3D">
</head>
<body>
<header>
    <div id="logo">
        <h1>
            <a href="/">Dr.Sacn</a>
        </h1>
    </div>

    <c:if test="${empty admin && empty authUser }">
        <div class="btn">
            <input type="button" value="로그인" onclick="location.href='/users/signin'">
            <input type="button" value="회원가입" onclick="location.href='/users/signup'">
        </div>
    </c:if>

    <c:if test="${not empty authUser }">
        <div class="btn">
            <input type="button" value="회원정보" onclick="location.href='/users/me'">
            <input type="button" id="btn-logout" value="로그아웃" onclick="location.href='/users/action/signout'">
        </div>
    </c:if>

    <c:if test="${not empty admin }">
        <div class="btn">
            <input type="button" value="로그아웃" onclick="location.href='/service/admin?command=logout'">
        </div>
    </c:if>
</header>
<nav id="menu">
    <ul>
        <li><a href="/patientScan/list">의료 영상 목록</a></li>
        <li><a href="/patients/">환자 목록</a></li>
    </ul>
</nav>
</body>
</html>

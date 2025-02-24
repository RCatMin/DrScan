<%--
  Created by IntelliJ IDEA.
  User: iminsu
  Date: 2025. 2. 20.
  Time: 오후 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>영상판독 페이지</title>
</head>
<c:import url="header" />
<body>
    <div id = "ImageManipulationBox">

    </div>

    <div id = "printSeriesList">

    </div>

    <div id = "printPatientAndVidInfo">
        <thead>
            <tbody id = "patientInfo"></tbody>
        </thead>
    </div>

    <div id = "printSeriesVideo">

    </div>
</body>
<c:import url="footer" />
</html>

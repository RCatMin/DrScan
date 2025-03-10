<%--
  Created by IntelliJ IDEA.
  User: gusrb
  Date: 2025-03-10
  Time: 오전 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>의료 영상 목록</title>
    <link rel="stylesheet" type="text/css" href="/style/viewerList.css">
    <script src="/script/viewerList.js" defer></script>
</head>
<c:import url="/header" />
<body>
<div class="search-bar">
  <input type="text" id="searchPname" placeholder="Patient Name">
  <input type="text" id="searchPid" placeholder="MRN">

  <div class="date-range">
    <label>Study Date:</label>
    <input type="date" id="searchStartDate" placeholder="Start Date">
    <input type="date" id="searchEndDate" placeholder="End Date">
  </div>

  <input type="text" id="searchDescription" placeholder="Description">

  <select id="searchModality">
    <option value="">All Modalities</option>
    <option value="CR">CR (Computed Radiography)</option>
    <option value="CT">CT (Computed Tomography)</option>
    <option value="DX">DX (Digital Radiography)</option>
    <option value="MG">MG (Mammography)</option>
    <option value="MR">MR (Magnetic Resonance Imaging)</option>
    <option value="NM">NM (Nuclear Medicine)</option>
    <option value="OT">OT (Other)</option>
    <option value="PT">PT (Positron Emission Tomography)</option>
    <option value="US">US (Ultrasound)</option>
    <option value="XA">XA (X-ray Angiography)</option>
    <option value="RF">RF (Radio Fluoroscopy)</option>
  </select>

  <input type="text" id="searchAccession" placeholder="Accession #">

  <button id="btn-search" onclick="searchPatient()">🔍 Search</button>
</div>

<table class="search-results">
  <thead>
  <tr>
    <th>목록</th>
    <th onclick="sortTable('pname')">Patient Name</th>
    <th onclick="sortTable('pid')">MRN</th>
    <th onclick="sortTable('studydate')">Study Date</th>
    <th onclick="sortTable('studydesc')">Description</th>
    <th onclick="sortTable('modality')">Modality</th>
    <th onclick="sortTable('accessnum')">Accession #</th>
    <th onclick="sortTable('imagecnt')">Images</th>
    <th>Viewer</th>
  </tr>
  </thead>
  <tbody id="patientRecords"></tbody>
</table>

<div id="pagination"></div>
</body>
<c:import url="/footer" />
</html>

document.addEventListener("DOMContentLoaded", function () {
    // URL에서 마지막 부분(환자 ID) 가져오기
    const pathSegments = window.location.pathname.split("/");
    const patientId = pathSegments[pathSegments.length - 1]; // 마지막 경로 값이 pid

    console.log("Extracted patientId:", patientId);

    loadReports(patientId);
});

let currentPage = 1;
const itemsPerPage = 5;
let reportsData = [];

async function loadReports(patientId) {
    try {
        let response = await fetch(`/patientScan/action/reports/patient/${patientId}`);
        console.log(response);

        reportsData = await response.json();
        console.log(reportsData);

        displayReports();
    } catch (error) {
        console.error("판독 기록을 불러오는 중 오류 발생:", error);
    }
}

function displayReports() {
    const reportTableBody = document.getElementById("reportTableBody");
    reportTableBody.innerHTML = "";

    const start = (currentPage - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const paginatedReports = reportsData.slice(start, end);

    paginatedReports.forEach(report => {
        let row = `
            <tr>
                <td>${report.reportCode}</td>
                <td>${report.patientId}</td>
                <td>${new Date(report.studyDate).toLocaleString()}</td>
                <td>${report.reportText}</td>
                <td>${new Date(report.regDate).toLocaleString()}</td>
                <td>${new Date(report.modDate).toLocaleString()}</td>
                <td><button onclick="viewReportDetail(${report.reportCode})">상세보기</button></td>
            </tr>
        `;
        reportTableBody.innerHTML += row;
    });

    updatePagination();
}

function updatePagination() {
    document.getElementById("pageInfo").innerText = `${currentPage} / ${Math.ceil(reportsData.length / itemsPerPage)}`;
    document.getElementById("prevPage").disabled = currentPage === 1;
    document.getElementById("nextPage").disabled = currentPage * itemsPerPage >= reportsData.length;
}

document.getElementById("prevPage").addEventListener("click", () => {
    if (currentPage > 1) {
        currentPage--;
        displayReports();
    }
});

document.getElementById("nextPage").addEventListener("click", () => {
    if (currentPage * itemsPerPage < reportsData.length) {
        currentPage++;
        displayReports();
    }
});

function viewReportDetail(reportCode) {
    window.location.href = `/patientScan/report-detail/${reportCode}`;
}

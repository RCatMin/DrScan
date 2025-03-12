document.addEventListener("DOMContentLoaded", function () {
    // URL에서 마지막 부분(환자 ID) 가져오기
    const pathSegments = window.location.pathname.split("/");
    const patientId = pathSegments[pathSegments.length - 1]; // 마지막 경로 값이 pid

    console.log("Extracted patientId:", patientId);

    loadReports(patientId);
});

let currentPage = 1;
const itemsPerPage = 10;
let reportsData = [];

async function loadReports(patientId) {
    try {
        let response = await fetch(`/patientScan/action/reports/patient/${patientId}`);
        console.log(response);

        reportsData = await response.json();

        if (reportsData.length === 0) {
            alert("판독한 내용이 없습니다!");
            return;
        }

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
        let severityClass = getSeverityClass(report.severityLevel);

        let row = `
            <tr class="${severityClass}">
                <td>${report.reportCode}</td>
                <td>${report.patientId}</td>
                <td>${report.reportStatus}</td>
                <td>${new Date(report.studyDate).toLocaleString()}</td>
                <td>${report.reportText}</td>
                <td>${new Date(report.regDate).toLocaleString()}</td>
                <td>${new Date(report.modDate).toLocaleString()}</td>
                <td><button class="report-btn" onclick="viewReportDetail(${report.reportCode})">상세보기</button></td>
            </tr>
        `;
        reportTableBody.innerHTML += row;
    });

    updatePagination();
    // 이벤트 리스너 추가
    document.querySelectorAll(".pagination-btn").forEach(button => {
        button.addEventListener("click", function () {
            currentPage = parseInt(this.getAttribute("data-page"));
            displayReports();
        });
    });
}

// 중증도 레벨에 따른 CSS 클래스 반환
function getSeverityClass(severityLevel) {
    switch (severityLevel) {
        case "1":
            return "severity-critical"; // 위급 - 파란색
        case "2":
            return "severity-urgent"; // 긴급 - 빨간색
        case "3":
            return "severity-high"; // 높음 - 노란색
        case "4":
            return "severity-moderate"; // 보통 - 초록색
        case "5":
            return "severity-low"; // 낮음 - 흰색
        default:
            return "severity-default"; // 기본
    }
}

function updatePagination() {
    const totalPages = Math.ceil(reportsData.length / itemsPerPage);
    const paginationContainer = document.querySelector(".pagination");
    paginationContainer.innerHTML = "";

    // 이전 버튼
    if (currentPage > 1) {
        paginationContainer.innerHTML += `<button class="pagination-btn" data-page="${currentPage - 1}">&lt;</button>`;
    }

    // 페이지 숫자 버튼 (최대 5개 표시)
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, startPage + 4);
    if (endPage - startPage < 4) {
        startPage = Math.max(1, endPage - 4);
    }

    for (let i = startPage; i <= endPage; i++) {
        paginationContainer.innerHTML += `<button class="pagination-btn ${i === currentPage ? 'active' : ''}" data-page="${i}">${i}</button>`;
    }

    // 다음 버튼
    if (currentPage < totalPages) {
        paginationContainer.innerHTML += `<button class="pagination-btn" data-page="${currentPage + 1}">&gt;</button>`;
    }
}

function viewReportDetail(reportCode) {
    window.location.href = `/patientScan/report-detail/${reportCode}`;
}

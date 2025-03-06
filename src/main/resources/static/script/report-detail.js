document.addEventListener("DOMContentLoaded", function() {
    const reportCode = getReportCodeFromURL();
    if (!reportCode) {
        console.error("URL에서 reportCode를 찾을 수 없습니다.");
        return;
    }
    fetchReportDetail(reportCode);

    document.getElementById("update-button").addEventListener("click", function() {
        updateReport(reportCode);
    });

    document.getElementById("delete-button").addEventListener("click", function() {
        deleteReport(reportCode);
    });
});

function getReportCodeFromURL() {
    const pathSegments = window.location.pathname.split("/");
    return pathSegments[pathSegments.length - 1]; // 마지막 경로 값이 reportCode
}

function fetchReportDetail(reportCode) {
    fetch(`/patientScan/action/reports/${reportCode}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("patientId").value = data.patientId;
            document.getElementById("studyDate").value = new Date(data.studyDate).toLocaleString();
            document.getElementById("studyName").value = data.studyName;
            document.getElementById("bodyPart").value = data.bodyPart;
            document.getElementById("severityLevel").value = data.severityLevel;
            document.getElementById("reportStatus").value = data.reportStatus;
            document.getElementById("reportText").value = data.reportText;
        })
        .catch(error => console.error("판독 상세 정보를 가져오는 중 오류 발생:", error));
}

function updateReport(reportCode) {
    const updatedData = {
        severityLevel: document.getElementById("severityLevel").value,
        reportStatus: document.getElementById("reportStatus").value,
        reportText: document.getElementById("reportText").value
    };

    fetch(`/patientScan/action/reports/${reportCode}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedData)
    })
        .then(response => response.json())
        .then(data => {
            alert("판독 정보가 성공적으로 수정되었습니다.");
            location.reload();
        })
        .catch(error => console.error("판독 정보 수정 중 오류 발생:", error));
}

function deleteReport(reportCode) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    fetch(`/patientScan/action/reports/${reportCode}`, { method: "DELETE" })
        .then(response => {
            if (response.ok) {
                alert("판독 정보가 삭제되었습니다.");
                window.location.href = "/patientScan/radiology";
            } else {
                alert("삭제 실패!");
            }
        })
        .catch(error => console.error("판독 정보 삭제 중 오류 발생:", error));
}

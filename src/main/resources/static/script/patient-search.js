var currentPage = 1; // 현재 페이지
var itemsPerPage = 20; // 한 페이지당 표시할 개수
var totalPages = 1; // 총 페이지 수
var allStudies = []; // 전체 데이터를 저장할 배열
var sortOrder = {}; // 정렬 상태를 저장할 객체

document.addEventListener("DOMContentLoaded", function () {
    fetchAllPatientRecords();
});

function fetchAllPatientRecords() {
    console.log("모든 환자 영상 기록을 가져오는 중...");

    fetch("/patientScan/action/records/all")
        .then(response => response.json())
        .then(data => {
            console.log("불러온 데이터:", data);

            if (!Array.isArray(data) || data.length === 0) {
                console.error("불러온 데이터가 비어 있습니다!");
                return;
            }

            allStudies = [];

            data.forEach(patientData => {
                var patient = patientData.patient;
                var studyDetails = patientData.studyDetails || [];

                studyDetails.forEach(study => {
                    var seriesList = study.series || [];
                    seriesList.forEach(series => {
                        allStudies.push({
                            pname: patient.pname,
                            pid: patient.pid,
                            psex: patient.psex,
                            pbirthdate: patient.pbirthdate,
                            modality: series.modality || "N/A",
                            studydesc: study.study.studydesc || "N/A",
                            studydate: study.study.studydate || "N/A",
                            studytime: study.study.studytime || "N/A",
                            studykey: study.study.studykey || study.studykey,
                            serieskey: series.serieskey || series.serieskey
                        });
                    });
                });
            });

            totalPages = Math.ceil(allStudies.length / itemsPerPage);
            console.log("최종 데이터:", allStudies);

            renderPatientTable(allStudies);
            displayPage(1);

            if (allStudies.length > 0) {
                document.getElementById("resultSection").style.display = "block";
            }
        })
        .catch(error => console.error("API 호출 중 오류 발생:", error));
}

function renderPatientTable(data) {
    console.log("테이블에 표시할 데이터:", data);

    var patientRecords = document.getElementById("patientRecords");
    if (!patientRecords) {
        console.error("테이블 요소를 찾을 수 없습니다!");
        return;
    }

    patientRecords.innerHTML = ""; // 기존 데이터 삭제

    data.forEach(study => {
        var row = `
            <tr>
                <td>${study.pname}</td>
                <td>${study.pid}</td>
                <td>${study.psex}</td>
                <td>${study.pbirthdate}</td>
                <td>${study.modality}</td>
                <td>${study.studydesc}</td>
                <td>${study.studydate}</td>
                <td>${study.studytime}</td>
                <td>
                    <button class="btn btn-analysis" 
                        onclick="analyzeImage('${study.pid}', '${study.studykey}', '${study.serieskey}')">
                        영상 판독
                    </button>
                </td>
            </tr>
        `;
        patientRecords.innerHTML += row;
    });

    console.log("테이블 렌더링 완료!");
}

function searchPatient() {
    var pidField = document.getElementById("searchPid");
    var pnameField = document.getElementById("searchPname");
    var psexField = document.getElementById("searchPsex");
    var pbirthdateField = document.getElementById("searchPbirthdate");

    if (!pidField || !pnameField || !psexField || !pbirthdateField) {
        console.error("검색 필드가 올바르게 로드되지 않았습니다.");
        return;
    }

    var pid = pidField.value.trim();
    var pname = pnameField.value.trim();
    var psex = psexField.value;
    var pbirthdate = pbirthdateField.value;

    var url = `/patientScan/action/search?pid=${pid}&pname=${pname}&psex=${psex}&pbirthdate=${pbirthdate}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log("검색 결과:", data);

            allStudies = [];

            data.forEach(patientData => {
                var patient = patientData.patient;
                var studyDetails = patientData.studyDetails || []; // Study 데이터 가져오기!

                studyDetails.forEach(study => {
                    var seriesList = study.series || []; // Series 데이터 가져오기!
                    seriesList.forEach(series => {
                        allStudies.push({
                            pname: patient.pname,
                            pid: patient.pid,
                            psex: patient.psex,
                            pbirthdate: patient.pbirthdate,
                            modality: series.modality || "N/A",
                            studydesc: study.study.studydesc || "N/A",
                            studydate: study.study.studydate || "N/A",
                            studytime: study.study.studytime || "N/A",
                            studykey: study.study.studykey || study.studykey,
                            serieskey: series.serieskey || series.serieskey
                        });
                    });
                });
            });

            totalPages = Math.ceil(allStudies.length / itemsPerPage);

            renderPatientTable(allStudies);
            displayPage(1);

            // 검색 결과가 있으면 resultSection을 보이게 변경
            if (allStudies.length > 0) {
                document.getElementById("resultSection").style.display = "block";
            }
        })
        .catch(error => console.error("검색 중 오류 발생:", error));
}

// 특정 페이지의 데이터 표시 함수
function displayPage(page) {
    currentPage = page; // 현재 페이지 업데이트

    var start = (page - 1) * itemsPerPage;
    var end = start + itemsPerPage;
    var paginatedItems = allStudies.slice(start, end);

    console.log(`페이지 ${page}의 데이터:`, paginatedItems);

    renderPatientTable(paginatedItems);

    updatePaginationControls();
}

// 페이지네이션 버튼 생성
function updatePaginationControls() {
    var pagination = document.getElementById("pagination");
    pagination.innerHTML = ""; // 기존 버튼 초기화

    for (let i = 1; i <= totalPages; i++) {
        let button = document.createElement("button");
        button.innerText = i;
        button.classList.add("pagination-btn");

        if (i === currentPage) {
            button.classList.add("active");
        }

        button.onclick = function () {
            displayPage(i);
        };

        pagination.appendChild(button);
    }
}

function analyzeImage(pid, studykey, serieskey) {
    window.location.href = "/patientScan/imaging-record/" + pid + "/" +studykey + "/" + serieskey;
}

// 정렬
function sortTable(columnKey) {
    if (!sortOrder[columnKey]) {
        sortOrder[columnKey] = 'asc';  // 첫 클릭은 오름차순
    } else {
        sortOrder[columnKey] = sortOrder[columnKey] === 'asc' ? 'desc' : 'asc';
    }

    allStudies.sort((a, b) => {
        let valA = a[columnKey];
        let valB = b[columnKey];

        // null 또는 undefined 처리
        if (valA == null) valA = "";
        if (valB == null) valB = "";

        // 숫자인 경우 숫자로 변환 (예: 생년월일, 촬영날짜, 촬영시간)
        if (!isNaN(valA) && !isNaN(valB) && valA !== "" && valB !== "") {
            valA = Number(valA);
            valB = Number(valB);
        } else {
            // 문자열 정렬 (대소문자 무시)
            valA = String(valA).toLowerCase();
            valB = String(valB).toLowerCase();
        }

        // 정렬 방향 설정
        if (sortOrder[columnKey] === 'asc') {
            return valA > valB ? 1 : valA < valB ? -1 : 0;
        } else {
            return valA < valB ? 1 : valA > valB ? -1 : 0;
        }
    });

    // 첫 번째 페이지부터 다시 표시
    displayPage(1);
}

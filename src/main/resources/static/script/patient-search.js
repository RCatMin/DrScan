var currentPage = 1; // 현재 페이지
var itemsPerPage = 5; // 한 페이지당 표시할 개수
var totalPages = 1; // 총 페이지 수
var allStudies = []; // 전체 데이터를 저장할 배열

function searchPatient() {
    var patientCode = document.getElementById("patientCode").value.trim();
    var regex = /^[A-Z]{2}\d{4}$/; // 대문자 2자리 + 숫자 4자리 검사

    if (patientCode === "") {
        alert("환자 코드를 입력해주세요!");
        return;
    } else if (patientCode.length !== 6) {
        alert("올바른 환자 코드를 입력해주세요!");
        return;
    } else if (!regex.test(patientCode)) {
        alert("환자 코드는 대문자 2자리 + 숫자 4자리 형식이어야 합니다!");
        return;
    }

    fetch(`/patientScan/action/${patientCode}/records`)
        .then(response => {
            if (!response.ok) {
                throw new Error("환자 정보를 불러오는 중 오류가 발생했습니다.");
            }
            return response.json();
        })
        .then(response => {

            console.log("서버 응답 데이터:", response); // 확인용

            document.getElementById("resultSection").style.display = "block";
            var patientRecords = document.getElementById("patientRecords");
            patientRecords.innerHTML = ""; // 기존 데이터 삭제

            var patient = response.patient;
            allStudies = []; // 기존 데이터 초기화

            var studyDetails = response.studyDetails || []; // undefined 방지

            if (!patient || response.studies.length === 0) {
                patientRecords.innerHTML = "<tr><td colspan='10'>해당 환자의 데이터가 없습니다.</td></tr>";
                return;
            }

            studyDetails.forEach(function(study) {
                var seriesList = study.series || []; // undefined 방지
                seriesList.forEach(function(series) {
                    allStudies.push({
                        pname: patient.pname,
                        pid: patient.pid,
                        psex: patient.psex,
                        pbirthdate: patient.pbirthdate,
                        modality: series.modality || "N/A",
                        studydesc: study.study.studydesc || "N/A",
                        studydate: study.study.studydate || "N/A",
                        studytime: study.study.studytime || "N/A",
                        seriesinsuid: series.seriesinsuid
                    });
                });
            });

            totalPages = Math.ceil(allStudies.length / itemsPerPage);
            currentPage = 1; // 페이지 초기화
            displayPage(currentPage);
        })
        .catch(error => {
            alert(error.message);
        });
}

// 특정 페이지의 데이터 표시 함수
function displayPage(page) {
    var patientRecords = document.getElementById("patientRecords");
    patientRecords.innerHTML = ""; // 기존 데이터 삭제
    currentPage = page; // 현재 페이지 업데이트

    var start = (page - 1) * itemsPerPage;
    var end = start + itemsPerPage;
    var paginatedItems = allStudies.slice(start, end);

    paginatedItems.forEach(function(item) {
        var row = `
            <tr>
                <td>${item.pname}</td>
                <td>${item.pid}</td>
                <td>${item.psex}</td>
                <td>${item.pbirthdate}</td>
                <td>${item.modality}</td>
                <td>${item.studydesc}</td>
                <td>${item.studydate}</td>
                <td>${item.studytime}</td>
                <td><button class="btn btn-record" onclick="viewMedicalRecords('${item.pid}')">진료 기록 조회</button></td>
                <td><button class="btn btn-analysis" onclick="analyzeImage('${item.seriesinsuid}')">영상 판독</button></td>
            </tr>
        `;
        patientRecords.innerHTML += row;
    });

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

function viewMedicalRecords(pid) {
    // window.location.href = 진료기록조회 주소넣기;
}

function analyzeImage(seriesId) {
    // window.location.href = 영산판독 주소넣기;
}

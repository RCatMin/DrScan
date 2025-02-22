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


    $.ajax({
        url: "/patient/" + patientCode + "/records",
        method: "GET",
        success: function(response) {
            document.getElementById("resultSection").style.display = "block";
            var patientRecords = document.getElementById("patientRecords");
            patientRecords.innerHTML = ""; // 기존 데이터 삭제

            var patient = response.patient;
            var studies = response.studies;

            if (!patient || studies.length === 0) {
                patientRecords.innerHTML = "<tr><td colspan='10'>해당 환자의 데이터가 없습니다.</td></tr>";
                return;
            }

            studies.forEach(function(study) {
                study.series.forEach(function(series) {
                    var row = `
                        <tr>
                            <td>${patient.pname}</td>
                            <td>${patient.pid}</td>
                            <td>${patient.psex}</td>
                            <td>${patient.pbirthdate}</td>
                            <td>${series.modality}</td>
                            <td>${study.studydesc}</td>
                            <td>${study.studydate}</td>
                            <td>${study.studytime}</td>
                            <td><button class="btn btn-record" onclick="viewMedicalRecords('${patient.pid}')">진료 기록 조회</button></td>
                            <td><button class="btn btn-analysis" onclick="analyzeImage('${series.seriesinsuid}')">영상 판독</button></td>
                        </tr>
                    `;
                    patientRecords.innerHTML += row;
                });
            });
        },
        error: function() {
            alert("환자 정보를 불러오는 중 오류가 발생했습니다.");
        }
    });
}

function viewMedicalRecords(pid) {
    window.location.href = "/patient/" + pid + "/records";
}

function analyzeImage(seriesId) {
    window.location.href = "/image-analysis/" + seriesId;
}

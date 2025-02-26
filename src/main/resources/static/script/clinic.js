document.addEventListener("DOMContentLoaded", function() {
    const patientCode = getPatientCodeFromURL();

    if (patientCode) {
        document.getElementById("patient-code").textContent = patientCode + "님의 진료기록";
        fetchClinicData(patientCode);
    } else {
        console.error("URL에서 환자 코드를 찾을 수 없습니다.");
    }

    // 버튼 클릭 시 addClinic 페이지로 이동
    document.querySelector("#add-button").addEventListener("click", function() {
        window.location.href = "/clinic/add/" + patientCode;
    });
});

// URL에서 환자코드 추출
function getPatientCodeFromURL() {
    const pathSegments = window.location.pathname.split("/");
    return pathSegments.length > 2 ? pathSegments[2] : null;  // /clinic/{patientCode} 구조
}

// 진료 데이터 가져오기
function fetchClinicData(patientCode) {
    fetch(`/clinic/action/${patientCode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("진료 정보를 찾을 수 없습니다.");
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById("clinic-body");

            if (!tbody) {
                throw new Error("tbody 요소를 찾을 수 없습니다.");
            }

            tbody.innerHTML = ""; // 기존 데이터 초기화

            data.forEach(clinic => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${clinic.clinicCode}</td>
                    <td>${clinic.patientCode}</td>
                    <td>${clinic.clinicDate ? new Date(clinic.clinicDate).toLocaleDateString() : "미등록"}</td>
                    <td>${clinic.context}</td>
                    <td>${new Date(clinic.regDate).toLocaleString()}</td>
                    <td>${new Date(clinic.modDate).toLocaleString()}</td>
                    <td><button class="detail-button" data-cliniccode="${clinic.clinicCode}">상세보기</button></td>
                `;

                tbody.appendChild(tr);
            });

            // 상세보기 버튼 이벤트 추가
            document.querySelectorAll(".detail-button").forEach(button => {
                button.addEventListener("click", function() {
                    const clinicCode = this.getAttribute("data-cliniccode");
                    window.location.href = `/clinic/detail/${clinicCode}`;
                });
            });
        })
        .catch(error => {
            console.error("진료 데이터를 가져오는 중 오류 발생:", error);
        });
}

document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로딩 시 진료 데이터를 가져와서 tbody에 추가
    fetchClinicData();

    // 버튼 클릭 시 addClinic 페이지로 이동
    document.getElementById("hiddenPatientCode").value = "MS0006";

});

function fetchClinicData() {
    const patientCode = "MS0006"; // 하드코딩된 예시, 실제로는 JSP에서 받아야 함

    // 서버의 API에서 해당 환자의 진료 데이터를 가져오기
    fetch(`/clinic/action/${patientCode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("진료 정보를 찾을 수 없습니다.");
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById("clinic-body");
            console.log(tbody);  // tbody가 잘 선택되는지 확인

            if (!tbody) {
                throw new Error("tbody 요소를 찾을 수 없습니다.");
            }

            data.forEach(clinic => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${clinic.clinicCode}</td>
                    <td>${clinic.patientCode}</td>
                    <td>${clinic.clinicDate ? new Date(clinic.clinicDate).toLocaleDateString() : "미등록"}</td>
                    <td>${clinic.context}</td>
                    <td>${new Date(clinic.regDate).toLocaleString()}</td>
                    <td>${new Date(clinic.modDate).toLocaleString()}</td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error("진료 데이터를 가져오는 중 오류 발생:", error);
        });
}

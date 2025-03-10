let storedPatientCode=null;
document.addEventListener("DOMContentLoaded", function() {
    const clinicCode = getClinicCodeFromURL();
    const patientCode = document.getElementById("patientCode").value;
    if (!clinicCode) {
        console.error("URL에서 진료 코드를 찾을 수 없습니다.");
        return;
    }
    fetchClinicDetail(clinicCode);

    document.getElementById("update-button").addEventListener("click", function() {
        updateClinic(clinicCode);
    });

    document.getElementById("delete-button").addEventListener("click", function() {
        deleteClinic(clinicCode,patientCode);
    });
});

function getClinicCodeFromURL() {
    const pathSegments = window.location.pathname.split("/");
    return pathSegments.length > 3 ? pathSegments[3] : null;
}

function fetchClinicDetail(clinicCode) {
    fetch(`/clinic/action/detail/${clinicCode}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("clinicCode").value = clinicCode;
            document.getElementById("patientCode").value = data.patientCode;
            document.getElementById("clinicDate").value = data.clinicDate;
            document.getElementById("context").value = data.context;
            storedPatientCode = data.patientCode;
        })
        .catch(error => console.error("진료 상세 정보를 가져오는 중 오류 발생:", error));
}

function updateClinic(clinicCode) {
    const clinicDate = document.getElementById("clinicDate").value;
    const context = document.getElementById("context").value
    const updatedData = {
        clinicDate: clinicDate,
        context: context
    };

    if (clinicDate.trim() === "") {
        alert("진료 날짜를 작성해주세요.");
        return;
    }
    if(context.trim() === ""){
        alert("진료 내용을 작성해주세요.");
        return;
    }

    fetch(`/clinic/action/detail/${clinicCode}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedData)
    })
        .then(response => response.json())
        .then(data => {
            alert("진료 정보가 성공적으로 수정되었습니다.");
            window.location.href = `/clinic/${data.patientCode}`;
        })
        .catch(error => console.error("진료 정보 수정 중 오류 발생:", error));
}

function deleteClinic(clinicCode,patientCode) {
    console.log(patientCode);
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    fetch(`/clinic/action/${clinicCode}`, { method: "DELETE" })
        .then(response => response.json())
        .then(data => {
            alert("진료 정보가 삭제되었습니다.");
            window.location.href = `/clinic/`+storedPatientCode;
        })
        .catch(error => console.error("진료 정보 삭제 중 오류 발생:", error));
}